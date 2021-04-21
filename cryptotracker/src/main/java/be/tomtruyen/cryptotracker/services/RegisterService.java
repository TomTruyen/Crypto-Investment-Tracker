package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.RegisterResult;
import be.tomtruyen.cryptotracker.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.Map;

@Service
public class RegisterService {
    public static ResponseEntity<Object> register(Map<String, Object> body){
        RegisterResult result = validate(body);

        boolean success = result.isSuccess();

        if(success) {
            String email = ((String) body.getOrDefault("email", "")).toLowerCase().trim();
            String password = ((String) body.getOrDefault("password", "")).trim();
            password = Utils.hashPassword(password);

            try {
                DatabaseService databaseService = new DatabaseService();

                if(databaseService.connection == null) throw new SQLException();

                databaseService.addUser(email, password);

                EmailService.sendVerificationEmail(email);

                databaseService.closeConnection();
            } catch (SQLException se) {
                se.printStackTrace();

                if(se instanceof SQLIntegrityConstraintViolationException) {
                    result = RegisterResult.ERR_ALREADY_EXISTS;
                } else {
                    result = RegisterResult.ERR_UNKNOWN;
                }
            }
        }

        success = result.isSuccess();
        String message = result.getMessage();
        HttpStatus status = result.getStatus();

        return ResponseEntity.status(status).body(
                Map.of(
                        "path", "/register",
                        "success", success,
                        "message", message,
                        "time", new Date()
                )
        );
    }

    private static RegisterResult validate(Map<String, Object> body) {
        String email = (String) body.getOrDefault("email", null);
        String password = (String) body.getOrDefault("password", null);

        if(email == null || password == null) return RegisterResult.ERR_MISSING_PARAMETERS;

        if(!Utils.isValidEmail(email)) return RegisterResult.ERR_INVALID_EMAIL;

        if(!Utils.isValidPassword(password)) return RegisterResult.ERR_INVALID_PASSWORD;

        return RegisterResult.SUCCESS;
    }
}
