package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.CryptoDao;
import be.tomtruyen.cryptotracker.dao.HistoryCryptoDao;
import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.rest.resources.UserResource;
import be.tomtruyen.cryptotracker.util.exception.CryptoUserNotFoundException;
import be.tomtruyen.cryptotracker.util.exception.InvalidTokenException;
import be.tomtruyen.cryptotracker.util.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoServiceTest
{
    @Mock
    private UserDao userDao;

    @Mock
    private CryptoDao cryptoDao;

    @Mock
    private HistoryCryptoDao historyCryptoDao;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private CryptoService cryptoService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void getPortfolioThrowsInvalidTokenExceptionWhenInvalidToken() {
        when(jwtService.verifyToken(anyString())).thenReturn(null);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.getPortfolio(""));
    }

    @Test
    void getPortfolioThrowsInvalidTokenExceptionWhenIdNotValid() {
        String token = "Bearer " + jwtService.generateToken(null, "test@test.com", null);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.getPortfolio(token));
    }
}
