package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.services.LoginService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path="login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {
    @PostMapping
    public ResponseEntity<Object> doLogin(@RequestBody Map<String, Object> body) {
        return LoginService.login(body);
    }
}
