package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.services.RegisterService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path="register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RegisterController {
    @PostMapping
    public ResponseEntity<Object> doRegister(@RequestBody Map<String, Object> body) {
        return RegisterService.register(body);
    }
}
