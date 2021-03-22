package be.tomtruyen.cryptotracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@SpringBootApplication
public class CryptotrackerApplication {
	public static void main(String[] args) {
		SpringApplication.run(CryptotrackerApplication.class, args);
	}
}
