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
import be.tomtruyen.cryptotracker.rest.resources.CryptoBuyResource;
import be.tomtruyen.cryptotracker.rest.resources.CryptoDeleteAlertResource;
import be.tomtruyen.cryptotracker.rest.resources.CryptoSellResource;
import be.tomtruyen.cryptotracker.rest.resources.CryptoSetAlertResource;
import be.tomtruyen.cryptotracker.util.exception.CryptoUserNotFoundException;
import be.tomtruyen.cryptotracker.util.exception.InvalidTokenException;
import be.tomtruyen.cryptotracker.util.jwt.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public CryptoResponse buy(String token, CryptoBuyResource cryptoResource) {
        User user = validateTokenAndGetUser(token, "/cryptocurrencies/buy");

        CoingeckoCrypto coingeckoCrypto = CryptoRepository.getInstance().find(cryptoResource.getTicker());

        Crypto crypto = new Crypto();
        crypto.setName(coingeckoCrypto.getName());
        crypto.setTicker(crypto.getTicker());
        crypto.setBuyAmount(cryptoResource.getAmount());
        crypto.setBuyPrice(cryptoResource.getPrice());
        crypto.setBuyDate(new Date());
        crypto.setUser(user);

        cryptoDao.save(crypto);

        return new CryptoResponseBuilder().withPath("/cryptocurrencies/buy").withOk().build();
    }

    public CryptoResponse sell(String token, CryptoSellResource cryptoResource) {
        validateTokenAndGetUser(token,"/cryptocurrencies/sell");

        Crypto crypto = cryptoDao.findFirstById(cryptoResource.getId());

        // Insert history
        HistoryCrypto sellCrypto = new HistoryCrypto();
        sellCrypto.setName(crypto.getName());
        sellCrypto.setTicker(crypto.getTicker());
        sellCrypto.setBuyAmount(crypto.getBuyAmount());
        sellCrypto.setBuyPrice(crypto.getBuyPrice());
        sellCrypto.setBuyDate(crypto.getBuyDate());
        sellCrypto.setSellAmount(cryptoResource.getAmount());
        sellCrypto.setSellPrice(cryptoResource.getPrice());
        sellCrypto.setSellDate(new Date());
        sellCrypto.setGas(cryptoResource.isGas());
        sellCrypto.setUser(crypto.getUser());

        historyCryptoDao.save(sellCrypto);

        // Update portfolio
        double newSellAmount = crypto.getSellAmount() + cryptoResource.getAmount();

        if(newSellAmount >= crypto.getBuyAmount() || crypto.getBuyAmount() - newSellAmount < 0.000001) {
            // Delete crypto
            cryptoDao.delete(crypto);
        } else {
            // Update crypto
            crypto.setSellAmount(newSellAmount);
            cryptoDao.save(crypto);
        }

        return new CryptoResponseBuilder().withPath("/cryptocurrencies/sell").withOk().build();
    }

    public CryptoResponse setAlert(String token, CryptoSetAlertResource alertResource) {
        validateTokenAndGetUser(token, "/cryptocurrencies/alert/set");

        Crypto crypto = cryptoDao.findFirstById(alertResource.getId());
        crypto.setPriceAlert(alertResource.getAlert());

        cryptoDao.save(crypto);

        return new CryptoResponseBuilder().withPath("/cryptocurrencies/alert/set").withOk().build();
    }

    public CryptoResponse deleteAlert(String token, CryptoDeleteAlertResource alertResource) {
        validateTokenAndGetUser(token, "/cryptocurrencies/alert/delete");

        Crypto crypto = cryptoDao.findFirstById(alertResource.getId());
        crypto.setPriceAlert(0);

        cryptoDao.save(crypto);

        return new CryptoResponseBuilder().withPath("/cryptocurrencies/alert/delete").withOk().build();
    }
}
