package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.domain.CoingeckoCrypto;
import be.tomtruyen.cryptotracker.domain.CryptoResult;
import be.tomtruyen.cryptotracker.repositories.CryptoRepository;
import be.tomtruyen.cryptotracker.utils.Utils;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CryptoService {
    public static ResponseEntity<Object> getPortfolio(Map<String, String> header) {
        List<Crypto> cryptos = new ArrayList<>();

        CryptoResult result = validate(header, null, false);

        boolean success = result.isSuccess();

        if(success) {
            try {
                DatabaseService databaseService = new DatabaseService();

                String token = header.getOrDefault("authorization", "");
                Claims claims = JWTService.verifyToken(token);

                assert claims != null;
                int id = (int) claims.getOrDefault("id", -1);

                if(id == -1) throw new SQLException();

                cryptos = databaseService.getPortfolio(id).stream().sorted(Comparator.comparing(Crypto::getBuyDate)).sorted(Comparator.comparing(Crypto::getName)).collect(Collectors.toList());

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
                        "path", "/cryptocurrencies/portfolio",
                        "success", success,
                        "message", message,
                        "crypto", cryptos,
                        "time", new Date()
                )
        );
    }

    public static ResponseEntity<Object> getPortfolioHistory(Map<String, String> header) {
        List<Crypto> cryptos = new ArrayList<>();

        CryptoResult result = validate(header, null, false);

        boolean success = result.isSuccess();

        if(success) {
            try {
                DatabaseService databaseService = new DatabaseService();

                String token = header.getOrDefault("authorization", "");
                Claims claims = JWTService.verifyToken(token);

                assert claims != null;
                int id = (int) claims.getOrDefault("id", -1);

                if(id == -1) throw new SQLException();

                cryptos = databaseService.getPortfolioHistory(id);

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
                        "path", "/cryptocurrencies/portfolio/history",
                        "success", success,
                        "message", message,
                        "crypto", cryptos,
                        "time", new Date()
                )
        );
    }

    public static ResponseEntity<Object> getCryptoList(Map<String, String> header) {
        List<CoingeckoCrypto> cyptos = new ArrayList<>();

        CryptoResult result = validate(header, null,false);

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
        CryptoResult result = validate(header, body, false);

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
                    double buyAmount = Utils.toDouble(body.get("amount"));
                    double buyPrice = Utils.toDouble(body.get("price"));

                    databaseService.buyCrypto(id, name, ticker, buyAmount, buyPrice);

                    databaseService.closeConnection();
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
        CryptoResult result = validate(header, body, true);

        boolean success = result.isSuccess();

        if(success) {
            try {
                DatabaseService databaseService = new DatabaseService();

                String token = header.getOrDefault("authorization", "");
                Claims claims = JWTService.verifyToken(token);

                assert claims != null;
                int userId = (int) claims.getOrDefault("id", -1);

                if(userId == -1) throw new SQLException();

                int id = (int) body.get("id");
                String ticker = (String) body.get("ticker");
                String name = CryptoRepository.getInstance().find(ticker).getName();
                double sellAmount = Utils.toDouble(body.get("amount"));
                boolean isGas = (boolean) body.get("isGas");
                double sellPrice = Utils.toDouble(body.get("price"));

                Crypto crypto = databaseService.findCryptoById(id);

                if(crypto == null) throw new SQLException();
                if(crypto.getBuyAmount() - crypto.getSellAmount() < sellAmount) {
                    result = CryptoResult.ERR_AMOUNT_TOO_LARGE;
                } else {
                    databaseService.sellCrypto(userId, id, name, ticker, sellAmount, sellPrice, isGas);
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
                        "path", "/cryptocurrencies/sell",
                        "success", success,
                        "message", message,
                        "time", new Date()
                )
        );
    }

    public static ResponseEntity<Object> setPriceAlert(Map<String, String> header, Map<String, Object> body) {
        CryptoResult result = validate(header, null, false);

        boolean success = result.isSuccess();

        if(success) {
            try {
                DatabaseService databaseService = new DatabaseService();

                String token = header.getOrDefault("authorization", "");
                Claims claims = JWTService.verifyToken(token);

                assert claims != null;
                int userId = (int) claims.getOrDefault("id", -1);

                if(userId == -1) throw new SQLException();

                int id = (int) body.getOrDefault("id", 0);
                double alert = Utils.toDouble(body.get("alert"));


                if(id == 0 || alert == 0) {
                    result = CryptoResult.ERR_MISSING_PARAMETERS;
                } else {
                    databaseService.setPriceAlert(userId, id, alert);
                }
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
                        "path", "/cryptocurrencies/alert/set",
                        "success", success,
                        "message", message,
                        "time", new Date()
                )
        );
    }

    public static ResponseEntity<Object> deletePriceAlert(Map<String, String> header, Map<String, Object> body) {
        CryptoResult result = validate(header, null, false);

        boolean success = result.isSuccess();

        if(success) {
            try {
                DatabaseService databaseService = new DatabaseService();

                String token = header.getOrDefault("authorization", "");
                Claims claims = JWTService.verifyToken(token);

                assert claims != null;
                int userId = (int) claims.getOrDefault("id", -1);

                if(userId == -1) throw new SQLException();

                int id = (int) body.get("id");

                if(id == 0) {
                    result = CryptoResult.ERR_MISSING_PARAMETERS;
                } else {
                    databaseService.deletePriceAlert(userId, id);
                }
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
                        "path", "/cryptocurrencies/alert/set",
                        "success", success,
                        "message", message,
                        "time", new Date()
                )
        );
    }

    private static CryptoResult validate(Map<String, String> header, Map<String, Object> body, boolean checkGas) {
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
            double amount = Utils.toDouble(body.get("amount"));
            double price = Utils.toDouble(body.get("price"));

            if(checkGas &&!body.containsKey("isGas")) {
                return CryptoResult.ERR_MISSING_PARAMETERS;
            }

            if(ticker.isEmpty()) return CryptoResult.ERR_TICKER_EMPTY;

            if(amount <= 0) return CryptoResult.ERR_AMOUNT_GREATER_THAN_ZERO;

            if(checkGas) {
                boolean isGas = (boolean) body.get("isGas");

                if (price <= 0 && !isGas) return CryptoResult.ERR_PRICE_GREATER_THAN_ZERO;
            } else {
                if (price <= 0) return CryptoResult.ERR_PRICE_GREATER_THAN_ZERO;
            }
        }

        return CryptoResult.SUCCESS;
    }
}
