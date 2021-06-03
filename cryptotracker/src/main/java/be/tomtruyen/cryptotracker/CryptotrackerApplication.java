package be.tomtruyen.cryptotracker;

import be.tomtruyen.cryptotracker.rest.handler.JpaExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableScheduling
@CrossOrigin
public class CryptotrackerApplication {
	public static void main(String[] args) {
		SpringApplication.run(CryptotrackerApplication.class, args);
	}
}
