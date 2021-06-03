package be.tomtruyen.cryptotracker.dao;

import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CryptoDao extends JpaRepository<Crypto, Long> {
    List<Crypto> findCryptosByUserOrderByBuyDateAscNameAsc(User user);
    List<Crypto> findCryptosByPriceAlertGreaterThan(double priceAlert);
}
