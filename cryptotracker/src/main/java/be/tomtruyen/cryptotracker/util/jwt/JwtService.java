package be.tomtruyen.cryptotracker.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private static final String SECRET_KEY = "TOM_CRYPTO_TRACKER";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int EXPIRATION_IN_MILLIS = 86400000; // == 1 DAY

    public String generateToken(Long id, String email, Claims claims) {
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

    public Claims verifyToken(String token) {
        try {
            if (!token.contains(TOKEN_PREFIX))
                return null;

            token = token.replace(TOKEN_PREFIX, "");

            return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            return null;
        }
    }
}