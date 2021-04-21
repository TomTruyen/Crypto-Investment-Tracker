package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.utils.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Service
public class JWTService {
    private static final String SECRET_KEY = "TOM_CRYPTO_TRACKER";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int EXPIRATION_IN_MILLIS = 86400000; // == 1 DAY

    public static String generateToken(int id, String email, Claims claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(String.valueOf(id)).setIssuedAt(now).setSubject(email).setIssuer(email).signWith(signatureAlgorithm, signingKey);
        if (claims == null) {
            builder.setClaims(Map.of(
                    "id", id,
                    "email", email,
                    "expiration", new Date(nowMillis + EXPIRATION_IN_MILLIS),
                    "authDate", new Date(nowMillis)
            ));
        } else {
            claims.put("expiration", new Date(nowMillis + EXPIRATION_IN_MILLIS));
            builder.setClaims(claims);
        }

        return builder.compact();
    }

    public static Claims verifyToken(String token) {
        try {
            if (!token.contains(TOKEN_PREFIX))
                return null;

            token = token.replace(TOKEN_PREFIX, "");

            return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            return null;
        }
    }

    public static String refreshToken(String token) {
        Claims claims = verifyToken(token);

        // Checking if the user has to login again or if we can refresh the token
        Date authDate = Utils.getDateFromMillis((long) claims.get("authDate"));
        LocalDateTime dt = authDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        dt = dt.plusDays(7);

        Date dateBeforeLoginIsRequired = Date.from(dt.atZone(ZoneId.systemDefault()).toInstant()); // After 7 days the user has to login again!

        Date now = new Date();

        // User has to login again
        if (now.after(dateBeforeLoginIsRequired))
            return null;

        // Refreshing token
        String email = (String) claims.get("email");
        int id = (int) claims.get("id");

        return generateToken(id, email, claims);
    }
}
