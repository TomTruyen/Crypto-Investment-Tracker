package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.LoginResult;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Service
public class LoginService {
    public static ResponseEntity<Object> login(Map<String, Object> body) {
        LoginResult result = validate(body);

        boolean success = result.isSuccess();

        String token = "";
        User user;
        if(success) {
            String email = ((String) body.getOrDefault("email", "")).toLowerCase().trim();
            String password = ((String) body.getOrDefault("password", "")).trim();
            password = Utils.hashPassword(password);

            try {
                DatabaseService databaseService = new DatabaseService();

                if(databaseService.connection == null) throw new SQLException();

                user = databaseService.findUser(email, password);

                if(user == null){
                    result = LoginResult.ERR_NOT_FOUND;
                } else if(!user.isVerified()){
                    result = LoginResult.ERR_NOT_VERIFIED;
                } else {
                    token = JWTService.generateToken(user.getId(), user.getEmail(), null);
                }

                databaseService.closeConnection();
            } catch (SQLException se) {
                se.printStackTrace();

                result = LoginResult.ERR_UNKNOWN;
            }
        }

        success = result.isSuccess();
        String message = result.getMessage();
        HttpStatus status = result.getStatus();

        return ResponseEntity.status(status).body(
                Map.of(
                    "path", "/login",
                    "success", success,
                    "message", message,
                    "token", token,
                    "time", new Date()
                )
        );
    }

    private static LoginResult validate(Map<String, Object> body) {
        String email = (String) body.getOrDefault("email", null);
        String password = (String) body.getOrDefault("password", null);

        if(email == null || password == null) return LoginResult.ERR_MISSING_PARAMETERS;

        return LoginResult.SUCCESS;
    }
}
