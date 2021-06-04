package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.CryptoDao;
import be.tomtruyen.cryptotracker.dao.HistoryCryptoDao;
import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.response.CryptoResponse;
import be.tomtruyen.cryptotracker.model.CryptoBuyResourceBuilder;
import be.tomtruyen.cryptotracker.model.CryptoDeleteAlertResourceBuilder;
import be.tomtruyen.cryptotracker.model.CryptoSellResourceBuilder;
import be.tomtruyen.cryptotracker.model.CryptoSetAlertResourceBuilder;
import be.tomtruyen.cryptotracker.repository.CryptoRepository;
import be.tomtruyen.cryptotracker.rest.resources.*;
import be.tomtruyen.cryptotracker.util.exception.CryptoNotFoundException;
import be.tomtruyen.cryptotracker.util.exception.CryptoUserNotFoundException;
import be.tomtruyen.cryptotracker.util.exception.InvalidTokenException;
import be.tomtruyen.cryptotracker.util.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private CryptoBuyResource cryptoBuyResource;
    private CryptoSellResource cryptoSellResource;
    private CryptoSetAlertResource cryptoSetAlertResource;
    private CryptoDeleteAlertResource cryptoDeleteAlertResource;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void init() {
        cryptoBuyResource = CryptoBuyResourceBuilder.anCryptoBuyResource().build();
        cryptoSellResource = CryptoSellResourceBuilder.anCryptoBuyResource().build();
        cryptoSetAlertResource = CryptoSetAlertResourceBuilder.anCryptoSetAlertResource().build();
        cryptoDeleteAlertResource = CryptoDeleteAlertResourceBuilder.anCryptoSetAlertResource().build();
    }

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

    @Test
    void getPortfolioHistoryThrowsInvalidTokenExceptionWhenInvalidToken() {
        when(jwtService.verifyToken(anyString())).thenReturn(null);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.getPortfolioHistory(""));
    }

    @Test
    void getPortfolioHistoryThrowsInvalidTokenExceptionWhenIdNotValid() {
        Claims claims = new DefaultClaims();
        claims.put("id", -1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.getPortfolioHistory(""));
    }

    @Test
    void getPortfolioHistoryThrowsUserNotFoundExceptionWhenUserNotFound() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(null);

        Assertions.assertThrows(CryptoUserNotFoundException.class, () -> cryptoService.getPortfolioHistory(""));
    }

    @Test
    void getPortfolioHistoryStatusOkWhenPortfolioHistoryFetched() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(new User());

        CryptoResponse cryptoResponse = cryptoService.getPortfolioHistory("");

        Assertions.assertNotNull(cryptoResponse);
        Assertions.assertEquals(200, cryptoResponse.getStatus());
        Assertions.assertEquals("/cryptocurrencies/portfolio/history", cryptoResponse.getPath());
    }

    @Test
    void getCryptoListThrowsInvalidTokenExceptionWhenInvalidToken() {
        when(jwtService.verifyToken(anyString())).thenReturn(null);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.getCryptoList(""));
    }

    @Test
    void  getCryptoListStatusOkWhenCryptosFetched() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);

        CryptoResponse cryptoResponse = cryptoService.getCryptoList("");

        Assertions.assertNotNull(cryptoResponse);
        Assertions.assertEquals(200, cryptoResponse.getStatus());
        Assertions.assertEquals("/cryptocurrencies/list", cryptoResponse.getPath());
    }

    @Test
    void buyThrowsInvalidTokenExceptionWhenInvalidToken() {
        when(jwtService.verifyToken(anyString())).thenReturn(null);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.buy("", cryptoBuyResource));
    }

    @Test
    void buyThrowsInvalidTokenExceptionWhenIdNotValid() {
        Claims claims = new DefaultClaims();
        claims.put("id", -1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.buy("", cryptoBuyResource));
    }

    @Test
    void buyThrowsUserNotFoundExceptionWhenUserNotFound() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(null);

        Assertions.assertThrows(CryptoUserNotFoundException.class, () -> cryptoService.buy("", cryptoBuyResource));
    }

    @Test
    void buyThrowsCryptoNotFoundExceptionWhenCryptoNotFound() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        cryptoBuyResource.setTicker("DOES NOT EXIST");

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(new User());

        Assertions.assertThrows(CryptoNotFoundException.class, () -> cryptoService.buy("", cryptoBuyResource));
    }

    @Test
    void sellThrowsInvalidTokenExceptionWhenInvalidToken() {
        when(jwtService.verifyToken(anyString())).thenReturn(null);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.sell("", cryptoSellResource));
    }

    @Test
    void sellThrowsInvalidTokenExceptionWhenIdNotValid() {
        Claims claims = new DefaultClaims();
        claims.put("id", -1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.sell("", cryptoSellResource));
    }

    @Test
    void sellThrowsUserNotFoundExceptionWhenUserNotFound() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(null);

        Assertions.assertThrows(CryptoUserNotFoundException.class, () -> cryptoService.sell("", cryptoSellResource));
    }

    @Test
    void sellThrowsCryptoNotFoundExceptionWhenCryptoNotFound() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(new User());

        Assertions.assertThrows(CryptoNotFoundException.class, () -> cryptoService.sell("", cryptoSellResource));
    }

    @Test
    void setAlertThrowsInvalidTokenExceptionWhenInvalidToken() {
        when(jwtService.verifyToken(anyString())).thenReturn(null);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.setAlert("", cryptoSetAlertResource));
    }

    @Test
    void setAlertThrowsInvalidTokenExceptionWhenIdNotValid() {
        Claims claims = new DefaultClaims();
        claims.put("id", -1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.setAlert("", cryptoSetAlertResource));
    }

    @Test
    void setAlertThrowsUserNotFoundExceptionWhenUserNotFound() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(null);

        Assertions.assertThrows(CryptoUserNotFoundException.class, () -> cryptoService.setAlert("", cryptoSetAlertResource));
    }

    @Test
    void setAlertThrowsCryptoNotFoundExceptionWhenCryptoNotFound() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(new User());

        Assertions.assertThrows(CryptoNotFoundException.class, () -> cryptoService.setAlert("", cryptoSetAlertResource));
    }

    @Test
    void deleteAlertThrowsInvalidTokenExceptionWhenInvalidToken() {
        when(jwtService.verifyToken(anyString())).thenReturn(null);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.deleteAlert("", cryptoDeleteAlertResource));
    }

    @Test
    void deleteAlertThrowsInvalidTokenExceptionWhenIdNotValid() {
        Claims claims = new DefaultClaims();
        claims.put("id", -1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);

        Assertions.assertThrows(InvalidTokenException.class, () -> cryptoService.deleteAlert("", cryptoDeleteAlertResource));
    }

    @Test
    void deleteAlertThrowsUserNotFoundExceptionWhenUserNotFound() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(null);

        Assertions.assertThrows(CryptoUserNotFoundException.class, () -> cryptoService.deleteAlert("", cryptoDeleteAlertResource));
    }

    @Test
    void deleteAlertThrowsCryptoNotFoundExceptionWhenCryptoNotFound() {
        Claims claims = new DefaultClaims();
        claims.put("id", 1);

        when(jwtService.verifyToken(anyString())).thenReturn(claims);
        when(userDao.findUserById(anyLong())).thenReturn(new User());

        Assertions.assertThrows(CryptoNotFoundException.class, () -> cryptoService.deleteAlert("", cryptoDeleteAlertResource));
    }
}
