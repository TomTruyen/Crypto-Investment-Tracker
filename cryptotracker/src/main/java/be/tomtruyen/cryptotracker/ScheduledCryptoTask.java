package be.tomtruyen.cryptotracker;

import be.tomtruyen.cryptotracker.util.coingecko.CoingeckoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledCryptoTask {
    CoingeckoService coingeckoService;

    public ScheduledCryptoTask(CoingeckoService coingeckoService) {
        this.coingeckoService = coingeckoService;
    }

    @Scheduled(fixedRate = 60000)
    public void fetchCryptos() {
        coingeckoService.fetchCryptos();
    }
}
