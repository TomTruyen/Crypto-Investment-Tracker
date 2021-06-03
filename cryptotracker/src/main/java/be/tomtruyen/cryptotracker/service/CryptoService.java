package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.CryptoDao;
import be.tomtruyen.cryptotracker.dao.HistoryCryptoDao;
import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.builder.CryptoResponseBuilder;
import be.tomtruyen.cryptotracker.domain.response.CryptoResponse;
import be.tomtruyen.cryptotracker.util.exception.CryptoUserNotFoundException;
import be.tomtruyen.cryptotracker.util.exception.InvalidTokenException;
import be.tomtruyen.cryptotracker.util.jwt.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class CryptoService {
    private final UserDao userDao;
    private final CryptoDao cryptoDao;
    private final HistoryCryptoDao historyCryptoDao;
    private final JwtService jwtService;

    public CryptoService(UserDao userDao, CryptoDao cryptoDao, HistoryCryptoDao historyCryptoDao, JwtService jwtService) {
        this.userDao = userDao;
        this.cryptoDao = cryptoDao;
        this.historyCryptoDao = historyCryptoDao;
        this.jwtService = jwtService;
    }

    public CryptoResponse getPortfolio(String token) {
        Claims claims = jwtService.verifyToken(token);

        if(claims == null) throw new InvalidTokenException("Token is invalid", "/cryptocurrencies/portfolio");

        long id = (long) (int) claims.getOrDefault("id", -1);

        if(id == -1) throw new InvalidTokenException("Token is invalid", "/cryptocurrencies/portfolio");

        User user = userDao.findUserById(id);

        if(user == null) throw new CryptoUserNotFoundException("User not found", "/cryptocurrencies/portfolio");

        List<Crypto> crypto = cryptoDao.findCryptosByUserOrderByBuyDateAscNameAsc(user);

        return new CryptoResponseBuilder().withPath("/cryptocurrencies/portfolio").withCrypto(crypto).withOk().build();
    }
}
