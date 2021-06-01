package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.rest.resources.VerifyResource;
import be.tomtruyen.cryptotracker.service.VerifyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "verify", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class VerifyController {
    private final VerifyService verifyService;

    public VerifyController(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> verify(@RequestBody @Valid VerifyResource verifyResource) {
        UserResponse response = verifyService.verify(verifyResource);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
