package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.domain.response.CryptoResponse;
import be.tomtruyen.cryptotracker.service.CryptoService;
import be.tomtruyen.cryptotracker.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
