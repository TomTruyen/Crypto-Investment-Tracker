package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.CryptoDao;
import be.tomtruyen.cryptotracker.dao.HistoryCryptoDao;
import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.CoingeckoCrypto;
import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.domain.HistoryCrypto;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.builder.CryptoResponseBuilder;
import be.tomtruyen.cryptotracker.domain.response.CryptoResponse;
import be.tomtruyen.cryptotracker.repository.CryptoRepository;
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

    private Claims validateTokenAndGetClaims(String token, String path) {
        Claims claims = jwtService.verifyToken(token);

        if(claims == null) throw new InvalidTokenException("Token is invalid", path);

        return claims;
    }

    private User validateTokenAndGetUser(String token, String path) {
        Claims claims = validateTokenAndGetClaims(token, path);

        long id = (long) (int) claims.getOrDefault("id", -1);

        if(id == -1) throw new InvalidTokenException("Token is invalid", path);

        User user = userDao.findUserById(id);

        if(user == null) throw new CryptoUserNotFoundException("User not found", path);

        return user;
    }

    public CryptoResponse getPortfolio(String token) {
        User user = validateTokenAndGetUser(token, "/cryptocurrencies/portfolio");

        List<Crypto> crypto = cryptoDao.findCryptosByUserOrderByBuyDateAscNameAsc(user);

        return new CryptoResponseBuilder().withPath("/cryptocurrencies/portfolio").withCrypto(crypto).withOk().build();
    }

    public CryptoResponse getPortfolioHistory(String token) {
        User user = validateTokenAndGetUser(token, "/cryptocurrencies/portfolio/history");

        List<HistoryCrypto> crypto = historyCryptoDao.findHistoryCryptoByUserOrderBySellDate(user);

        return new CryptoResponseBuilder().withPath("/cryptocurrencies/portfolio/history").withCrypto(crypto).withOk().build();
    }

    public CryptoResponse getCryptoList(String token) {
        validateTokenAndGetClaims(token, "/cryptocurrencies/list");

        List<CoingeckoCrypto> crypto = CryptoRepository.getInstance().get(150);

        return new CryptoResponseBuilder().withPath("/cryptocurrencies/list").withCrypto(crypto).withOk().build();
    }
}
