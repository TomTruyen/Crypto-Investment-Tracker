package be.tomtruyen.cryptotracker;

import be.tomtruyen.cryptotracker.services.CmcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class CryptotrackerApplication {
	private static Timer timer = new Timer();

	public static void main(String[] args) {
		CmcService.fetchCryptos();
		CmcService.fetchPrices();

		Calendar firstTaskTime = getFirstTime();
		timer.schedule(new FetchPrices(), firstTaskTime.getTime(), 1000 * 60 * 15);
		SpringApplication.run(CryptotrackerApplication.class, args);
	}

	@Configuration
	static class WebMvcConfiguration implements WebMvcConfigurer {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**");
		}
	}

	private static Calendar getFirstTime() {
		Calendar cal = Calendar.getInstance();

		int currentMinute = cal.get(Calendar.MINUTE);

		if (currentMinute < 45) {
			cal.set(Calendar.MINUTE, 45);
		}
		if (currentMinute < 30) {
			cal.set(Calendar.MINUTE, 30);
		}
		if (currentMinute < 15) {
			cal.set(Calendar.MINUTE, 15);
		}
		if (currentMinute >= 45) {
			cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
			cal.set(Calendar.MINUTE, 0);
		}

		cal.set(Calendar.SECOND, 0);

		return cal;
	}
}

class FetchPrices extends TimerTask {
	public void run() {
		CmcService.fetchPrices();
	}
}


