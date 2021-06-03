package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.domain.response.CryptoResponse;
import be.tomtruyen.cryptotracker.rest.resources.CryptoBuyResource;
import be.tomtruyen.cryptotracker.rest.resources.CryptoDeleteAlertResource;
import be.tomtruyen.cryptotracker.rest.resources.CryptoSellResource;
import be.tomtruyen.cryptotracker.rest.resources.CryptoSetAlertResource;
import be.tomtruyen.cryptotracker.service.CryptoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "cryptocurrencies", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class CryptoController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping(path = "portfolio")
    public ResponseEntity<CryptoResponse> getPortfolio(@RequestHeader(name = "authorization") String token) {
        CryptoResponse response = cryptoService.getPortfolio(token);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @GetMapping(path = "portfolio/history")
    public ResponseEntity<CryptoResponse> getPortfolioHistory(@RequestHeader(name = "authorization") String token) {
        CryptoResponse response = cryptoService.getPortfolioHistory(token);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @GetMapping(path = "list")
    public ResponseEntity<CryptoResponse> getCryptoList(@RequestHeader(name = "authorization") String token) {
        CryptoResponse response = cryptoService.getCryptoList(token);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping(path = "buy")
    public ResponseEntity<CryptoResponse> buyCrypto(@RequestHeader(name = "authorization") String token, @RequestBody @Valid CryptoBuyResource cryptoResource) {
        CryptoResponse response = cryptoService.buy(token, cryptoResource);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping(path = "sell")
    public ResponseEntity<CryptoResponse> sellCrypto(@RequestHeader(name = "authorization") String token, @RequestBody @Valid CryptoSellResource cryptoResource) {
        CryptoResponse response = cryptoService.sell(token, cryptoResource);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping(path = "alert/set")
    public ResponseEntity<CryptoResponse> setPriceAlert(@RequestHeader(name = "authorization") String token, @RequestBody @Valid CryptoSetAlertResource alertResource) {
        CryptoResponse response = cryptoService.setAlert(token, alertResource);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping(path = "alert/delete")
    public ResponseEntity<CryptoResponse> deletePriceAlert(@RequestHeader(name = "authorization") String token, @RequestBody @Valid CryptoDeleteAlertResource alertResource) {
        CryptoResponse response = cryptoService.deleteAlert(token, alertResource);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
