package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.CmcCrypto;
import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.domain.CmcCryptoPrice;
import be.tomtruyen.cryptotracker.domain.CryptoResult;
import be.tomtruyen.cryptotracker.repositories.CryptoPriceRepository;
import be.tomtruyen.cryptotracker.repositories.CryptoRepository;
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

        CryptoResult result = validate(header, null);

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
                        "path", "/cryptocurrencies",
                        "success", success,
                        "message", message,
                        "crypto", cryptos,
                        "time", new Date()
                )
        );
    }

    public static ResponseEntity<Object> getPrices(Map<String, String> header) {
        List<CmcCryptoPrice> prices = new ArrayList<>();

        CryptoResult result = validate(header, null);

        boolean success = result.isSuccess();

        if(success) {
            if(CryptoPriceRepository.getInstance().getAll().size() <= 0) {
                result = CryptoResult.ERR_UNKNOWN;
            } else {
                prices = CryptoPriceRepository.getInstance().getAll();
            }
        }

        success = result.isSuccess();
        String message = result.getMessage();
        HttpStatus status = result.getStatus();

        return ResponseEntity.status(status).body(
                Map.of(
                        "path", "/cryptocurrencies/prices",
                        "success", success,
                        "message", message,
                        "prices", prices,
                        "time", new Date()
                )
        );
    }

    public static ResponseEntity<Object> getCryptoList(Map<String, String> header) {
        List<CmcCrypto> cyptos = new ArrayList<>();

        CryptoResult result = validate(header, null);

        boolean success = result.isSuccess();

        if(success) {
            if(CryptoRepository.getInstance().getAll().size() <= 0) {
                result = CryptoResult.ERR_UNKNOWN;
            } else {
                cyptos = CryptoRepository.getInstance().get(150);
            }
        }

        success = result.isSuccess();
        String message = result.getMessage();
        HttpStatus status = result.getStatus();

        return ResponseEntity.status(status).body(
                Map.of(
                        "path", "/cryptocurrencies/list",
                        "success", success,
                        "message", message,
                        "crypto", cyptos,
                        "time", new Date()
                )
        );
    }

    public static ResponseEntity<Object> buy(Map<String, String> header, Map<String, Object> body) {
        CryptoResult result = validate(header, body);

        boolean success = result.isSuccess();

        if(success) {
            try {
                DatabaseService databaseService = new DatabaseService();

                String token = header.getOrDefault("authorization", "");
                Claims claims = JWTService.verifyToken(token);

                assert claims != null;
                int id = (int) claims.getOrDefault("id", -1);

                if(id == -1) throw new SQLException();


                try {
                    String ticker = (String) body.get("ticker");
                    String name = CryptoRepository.getInstance().find(ticker).getName();
                    double buyAmount = Double.parseDouble((String)body.getOrDefault("amount", "0"));
                    double buyPrice = Double.parseDouble((String) body.getOrDefault("price", "0"));

                    databaseService.buyCrypto(id, name, ticker, buyAmount, buyPrice);
                } catch (Exception e) {
                    result = CryptoResult.ERR_UNKNOWN;
                }

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
                        "path", "/cryptocurrencies/buy",
                        "success", success,
                        "message", message,
                        "time", new Date()
                )
        );
    }

    public static ResponseEntity<Object> sell(Map<String, String> header, Map<String, Object> body) {
        CryptoResult result = validate(header, body);

        boolean success = result.isSuccess();

        if(success) {
            try {
                DatabaseService databaseService = new DatabaseService();

                String token = header.getOrDefault("authorization", "");
                Claims claims = JWTService.verifyToken(token);

                assert claims != null;
                int id = (int) claims.getOrDefault("id", -1);

                if(id == -1) throw new SQLException();

                String name = (String) body.get("name");
                String ticker = (String) body.get("ticker");
                double sellAmount = (double) body.get("amount");
                double sellPrice = (double) body.get("price");

                databaseService.sellCrypto(id, name, ticker, sellAmount, sellPrice);

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
                        "path", "/cryptocurrencies/sell",
                        "success", success,
                        "message", message,
                        "time", new Date()
                )
        );
    }

    private static CryptoResult validate(Map<String, String> header, Map<String, Object> body) {
        if (!header.containsKey("authorization")) return CryptoResult.ERR_MISSING_TOKEN;

        String token = header.getOrDefault("authorization", "");

        Claims claims = JWTService.verifyToken(token);

        if(claims == null) return CryptoResult.ERR_INVALID_AUTH;

        Date date = new Date();
        Date expiration = Utils.getDateFromMillis((long) claims.getOrDefault("expiration", System.currentTimeMillis()));

        if (date.after(expiration)) return CryptoResult.ERR_EXPIRED_TOKEN;

        if(body != null) {
            if(!body.containsKey("ticker") || !body.containsKey("amount") || !body.containsKey("price")) return CryptoResult.ERR_MISSING_PARAMETERS;

            String ticker = (String) body.get("ticker");
            double amount = Double.parseDouble((String)body.getOrDefault("amount", "0"));
            double price = Double.parseDouble((String)body.getOrDefault("price", "0"));


            if(ticker.isEmpty()) return CryptoResult.ERR_TICKER_EMPTY;

            if(amount <= 0) return CryptoResult.ERR_AMOUNT_GREATER_THAN_ZERO;

            if(price <= 0) return CryptoResult.ERR_PRICE_GREATER_THAN_ZERO;
        }

        return CryptoResult.SUCCESS;
    }
}
