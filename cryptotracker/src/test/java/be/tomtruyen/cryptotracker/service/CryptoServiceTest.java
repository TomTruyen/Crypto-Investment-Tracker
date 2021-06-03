package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.CryptoDao;
import be.tomtruyen.cryptotracker.dao.HistoryCryptoDao;
import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.response.CryptoResponse;
import be.tomtruyen.cryptotracker.rest.resources.UserResource;
import be.tomtruyen.cryptotracker.util.exception.CryptoUserNotFoundException;
import be.tomtruyen.cryptotracker.util.exception.InvalidTokenException;
import be.tomtruyen.cryptotracker.util.exception.UserNotFoundException;
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
import java.util.Map;

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
        Claims claims = new DefaultClaims();
        claims.put("id", -1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.getPortfolio(""));
    }

    @Test
    void getPortfolioThrowsUserNotFoundExceptionWhenUserNotFound() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(null);

        Assertions.assertThrows(CryptoUserNotFoundException.class, () -> cryptoService.getPortfolio(""));
    }

    @Test
    void getPortfolioStatusOkWhenPortfolioFetched() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(new User());

        CryptoResponse cryptoResponse = cryptoService.getPortfolio("");

        Assertions.assertNotNull(cryptoResponse);
        Assertions.assertEquals(200, cryptoResponse.getStatus());
        Assertions.assertEquals("/cryptocurrencies/portfolio", cryptoResponse.getPath());
    }
}
