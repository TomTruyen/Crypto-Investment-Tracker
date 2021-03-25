package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.domain.CryptoResult;
import be.tomtruyen.cryptotracker.domain.VerifyResult;
import be.tomtruyen.cryptotracker.utils.Utils;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CryptoService {
    public static ResponseEntity<Object> get(Map<String, String> header) {
        List<Crypto> cryptos = new ArrayList<>();

        CryptoResult result = validate(header);

        boolean success = result.isSuccess();

        if(success) {
            try {
                DatabaseService databaseService = new DatabaseService();

                String token = header.getOrDefault("authorization", "");
                Claims claims = JWTService.verifyToken(token);

                assert claims != null;
                int id = (int) claims.getOrDefault("id", -1);

                if(id == -1) throw new SQLException();

                cryptos = databaseService.getCryptos(id);

                databaseService.closeConnection();
            } catch (SQLException se) {
                result = CryptoResult.ERR_UNKNOWN;
                se.printStackTrace();
            }
        }

        success = result.isSuccess();
        String message = result.getMessage();
        HttpStatus status = result.getStatus();

        return ResponseEntity.status(status).body(
                Map.of(
                        "path", "/crypto",
                        "success", success,
                        "message", message,
                        "crypto", cryptos,
                        "time", new Date()
                )
        );
    }

    private static CryptoResult validate(Map<String, String> header) {
        if (!header.containsKey("authorization")) return CryptoResult.ERR_MISSING_PARAMETERS;

        String token = header.getOrDefault("authorization", "");

        Claims claims = JWTService.verifyToken(token);

        if(claims == null) return CryptoResult.ERR_INVALID_AUTH;

        Date date = new Date();
        Date expiration = Utils.getDateFromMillis((long) claims.getOrDefault("expiration", System.currentTimeMillis()));

        if (date.after(expiration)) return CryptoResult.ERR_EXPIRED_TOKEN;

        return CryptoResult.SUCCESS;
    }
}
