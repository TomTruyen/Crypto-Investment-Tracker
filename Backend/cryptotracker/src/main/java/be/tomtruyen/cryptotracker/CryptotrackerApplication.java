package be.tomtruyen.cryptotracker;

import be.tomtruyen.cryptotracker.services.CoingeckoApiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class CryptotrackerApplication {
	public static void main(String[] args) {
		System.out.println("DETECTED OS: " + System.getProperty("os.name"));

		CoingeckoApiService.fetchCryptos();

		Timer timer = new Timer();
		timer.schedule(new CryptoTask(), 60000, 60000); // Update every minute

		SpringApplication.run(CryptotrackerApplication.class, args);

	}

	@Configuration
	static class WebMvcConfiguration implements WebMvcConfigurer {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**");
		}
	}
}

class CryptoTask extends TimerTask {
	public void run() {
		CoingeckoApiService.fetchCryptos();
	}
}
