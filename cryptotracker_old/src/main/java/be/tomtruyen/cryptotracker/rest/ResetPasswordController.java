package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.services.ResetPasswordService;
import be.tomtruyen.cryptotracker.services.VerifyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path="resetpassword", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ResetPasswordController {
    @PostMapping
    public ResponseEntity<Object> doReset(@RequestBody Map<String, Object> body) {
        return ResetPasswordService.reset(body);
    }

    @PostMapping(path = "confirm")
    public ResponseEntity<Object> confirmReset(@RequestBody Map<String, Object> body) {
        return ResetPasswordService.confirm(body);
    }
}
