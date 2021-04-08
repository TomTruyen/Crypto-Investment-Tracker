package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.services.CryptoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="cryptocurrencies", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class CryptoController {
    @GetMapping(path = "portfolio")
    public ResponseEntity<Object> getPortfolio(@RequestHeader Map<String, String> header) {
        return CryptoService.getPortfolio(header);
    }

    @GetMapping(path="portfolio/history")
    public ResponseEntity<Object> getPortfolioHistory(@RequestHeader Map<String, String> header) {
        return CryptoService.getPortfolioHistory(header);
    }

    @GetMapping(path = "list")
    public ResponseEntity<Object> getCryptoList(@RequestHeader Map<String, String> header) {
        return CryptoService.getCryptoList(header);
    }

    @PostMapping(path = "buy")
    public ResponseEntity<Object> buyCrypto(@RequestHeader Map<String, String> header, @RequestBody Map<String, Object> body) {
        return CryptoService.buy(header, body);
    }

    @PostMapping(path = "sell")
    public ResponseEntity<Object> sellCrypto(@RequestHeader Map<String, String> header, @RequestBody Map<String, Object> body) {
        return CryptoService.sell(header, body);
    }
}
