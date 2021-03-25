package be.tomtruyen.cryptotracker;

import be.tomtruyen.cryptotracker.services.CmcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CryptotrackerApplication {
	public static void main(String[] args) {
		CmcService.fetchPrices();
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
