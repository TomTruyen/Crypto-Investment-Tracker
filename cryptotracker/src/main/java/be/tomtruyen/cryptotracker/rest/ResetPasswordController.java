package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.rest.resources.EmailResource;
import be.tomtruyen.cryptotracker.rest.resources.UserResource;
import be.tomtruyen.cryptotracker.service.ResetPasswordService;
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
@RequestMapping(path = "resetpassword", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ResetPasswordController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private final ResetPasswordService resetPasswordService;

    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> reset(@RequestBody @Valid EmailResource emailResource) {
        UserResponse response = resetPasswordService.reset(emailResource);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping(path = "confirm")
    public ResponseEntity<UserResponse> confirm(@RequestBody @Valid UserResource userResource) {
        UserResponse response = resetPasswordService.confirm(userResource);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
