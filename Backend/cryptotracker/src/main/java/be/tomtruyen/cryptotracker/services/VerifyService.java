package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.VerifyResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class VerifyService {
    public static ResponseEntity<Object> verify(Map<String, Object> body) {
        VerifyResult result = validate(body);

        boolean success = result.isSuccess();

        if(success) {
            String base64Email = ((String) body.getOrDefault("email", "")).trim();

            String email = new String(Base64.getDecoder().decode(base64Email.getBytes()));

            try {
                DatabaseService databaseService = new DatabaseService();

                boolean emailExists = databaseService.verifyUser(email);

                if(!emailExists) {
                    result = VerifyResult.ERR_NOT_FOUND;
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


