package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.RegisterResult;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.ResetPasswordResult;
import be.tomtruyen.cryptotracker.domain.VerifyResult;
import be.tomtruyen.cryptotracker.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class ResetPasswordService {
    public static ResponseEntity<Object> reset(Map<String, Object> body) {
        ResetPasswordResult result = validate(body, false);

        boolean success = result.isSuccess();

        String token = "";
        User user;
        if(success) {
            String email = ((String) body.getOrDefault("email", "")).toLowerCase().trim();

            try {
                DatabaseService databaseService = new DatabaseService();

                if(databaseService.connection == null) throw new SQLException();

                user = databaseService.findUserByEmail(email);

                if(user != null) {
                    EmailService.sendResetPasswordEmail(email);
                }

                databaseService.closeConnection();
            } catch (SQLException se) {
                se.printStackTrace();

                result = ResetPasswordResult.ERR_UNKNOWN;
            }
        }

        success = result.isSuccess();
        String message = result.getMessage();
        HttpStatus status = result.getStatus();

        return ResponseEntity.status(status).body(
                Map.of(
                        "path", "/resetpassword",
                        "success", success,
                        "message", message,
                        "token", token,
                        "time", new Date()
                )
        );
    }

    public static ResponseEntity<Object> confirm(Map<String, Object> body) {
        ResetPasswordResult result = validate(body, true);

        String password = ((String) body.getOrDefault("password", "")).trim();
        if(!Utils.isValidPassword(password)) result = ResetPasswordResult.ERR_INVALID_PASSWORD;

        boolean success = result.isSuccess();

        String token = "";
        User user;
        if(success) {
            result = ResetPasswordResult.SUCCESS_CONFIRM;

            String email = ((String) body.getOrDefault("email", "")).toLowerCase().trim();
            password = Utils.hashPassword(password);

            try {
                DatabaseService databaseService = new DatabaseService();

                if(databaseService.connection == null) throw new SQLException();

                user = databaseService.findUserByEmail(email);

                if(user == null) {
                    result = ResetPasswordResult.ERR_NOT_FOUND;
                } else {
                    boolean isResetFailed = databaseService.resetPassword(email, password);
                    if(isResetFailed) result = ResetPasswordResult.ERR_UNKNOWN;
                }

                databaseService.closeConnection();
            } catch (SQLException se) {
                se.printStackTrace();

                result = ResetPasswordResult.ERR_UNKNOWN;
            }
        }

        success = result.isSuccess();
        String message = result.getMessage();
        HttpStatus status = result.getStatus();

        return ResponseEntity.status(status).body(
                Map.of(
                        "path", "/resetpassword/confirm",
                        "success", success,
                        "message", message,
                        "token", token,
                        "time", new Date()
                )
        );
    }

    private static ResetPasswordResult validate(Map<String, Object> body, boolean checkPassword) {
        String email = (String) body.getOrDefault("email", null);

        if(email == null) return ResetPasswordResult.ERR_MISSING_PARAMETERS;

        if(checkPassword) {
            String password = (String) body.getOrDefault("password", null);
            if(password == null) return ResetPasswordResult.ERR_MISSING_PARAMETERS;
        }

        return ResetPasswordResult.SUCCESS;
    }
}
