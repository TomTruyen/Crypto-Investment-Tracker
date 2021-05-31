package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.rest.resources.UserResource;
import be.tomtruyen.cryptotracker.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserResource user) {
        UserResponse response = loginService.login(user);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
