package be.tomtruyen.cryptotracker.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtServiceTest {
    private JwtService jwtService;
    private final Long id = 999L;
    private final String email = "test@bearer.token";

    @BeforeEach
    void init() {
        jwtService = new JwtService();
    }

    @Test
    void generateTokenShouldReturnBearerToken() {
        String token = jwtService.generateToken(id, email, null);

        Assertions.assertNotNull(token);
        Assertions.assertTrue(token.length() > 0);
    }

    @Test
    void generateTokenShouldReturnBearerTokenWithNewExpirationIfClaimsNotNull() {
        Claims claims = new DefaultClaims();

        String token = jwtService.generateToken(id, email, claims);

        Assertions.assertNotNull(token);
        Assertions.assertTrue(token.length() > 0);
    }

    @Test
    void verifyTokenShouldReturnNullIfPrefixInvalid() {
        String token = "bad_token";

        Claims claims = jwtService.verifyToken(token);

        Assertions.assertNull(claims);
    }

    @Test
    void verifyTokenShouldReturnNullIfException() {
        String token = "Bearer bad_token";

        Claims claims = jwtService.verifyToken(token);

        Assertions.assertNull(claims);
    }

    @Test
    void verifyTokenShouldReturnClaimsIfTokenIsValid() {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRvbS50cnV5ZW5AZ21haWwuY29tIiwiaWQiOjEsImV4cGlyYXRpb24iOjE2MjI3MjAzNTAxNTIsImF1dGhEYXRlIjoxNjIyNjMzOTUwMTUyfQ.gREAqVQUYj8EnGqeL7e5j7OjQ7UD-3J3w3qXijUNl3w";

        Claims claims = jwtService.verifyToken(token);

        Assertions.assertNotNull(claims);
    }
}
