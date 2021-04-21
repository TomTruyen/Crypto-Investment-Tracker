package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.LoginResult;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.VerifyResult;
import be.tomtruyen.cryptotracker.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

@Service
public class VerifyService {
    public static ResponseEntity<Object> verify(Map<String, Object> body) {
        VerifyResult result = validate(body);

        boolean success = result.isSuccess();

        String token = "";
        User user;
        if(success) {
            String email = ((String) body.getOrDefault("email", "")).toLowerCase().trim();

            try {
                DatabaseService databaseService = new DatabaseService();

                if(databaseService.connection == null) throw new SQLException();

                user = databaseService.findUserByEmail(email);

                if(user == null){
                    result = VerifyResult.ERR_NOT_FOUND;
                } else if(user.isVerified()){
                    result = VerifyResult.ERR_ALREADY_VERIFIED;
                } else {
                    databaseService.verifyUser(email);
                }

                databaseService.closeConnection();
            } catch (SQLException se) {
                se.printStackTrace();

                result = VerifyResult.ERR_UNKNOWN;
            }
        }

        success = result.isSuccess();
        String message = result.getMessage();
        HttpStatus status = result.getStatus();

        return ResponseEntity.status(status).body(
                Map.of(
                        "path", "/verify",
                        "success", success,
                        "message", message,
                        "token", token,
                        "time", new Date()
                )
        );
    }

    private static VerifyResult validate(Map<String, Object> body) {
        String email = (String) body.getOrDefault("email", null);

        if(email == null) return VerifyResult.ERR_MISSING_PARAMETERS;

        return VerifyResult.SUCCESS;
    }
}