package be.tomtruyen.cryptotracker;

import be.tomtruyen.cryptotracker.rest.handler.JpaExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptotrackerApplication {
	private static final Logger LOGGER = LogManager.getLogger(CryptotrackerApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting Application.");

		SpringApplication.run(CryptotrackerApplication.class, args);
	}

}
