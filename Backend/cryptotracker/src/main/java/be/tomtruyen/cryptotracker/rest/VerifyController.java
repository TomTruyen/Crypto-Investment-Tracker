package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.services.RegisterService;
import be.tomtruyen.cryptotracker.services.VerifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("verify")
public class VerifyController {
    @PostMapping
    public ResponseEntity<Object> doVerify(@RequestBody Map<String, Object> body) {
        return VerifyService.verify(body);
    }
}
