package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.services.CryptoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="crypto", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class CryptoController {
    @GetMapping
    public ResponseEntity<Object> getCryptos(@RequestHeader Map<String, String> header) {
        return CryptoService.get(header);
    }

    @PostMapping(path = "buy")
    public ResponseEntity<Object> buyCrypto(@RequestHeader Map<String, String> header, @RequestBody Map<String, Object> body) {
        return CryptoService.buy(header, body);
    }
}
