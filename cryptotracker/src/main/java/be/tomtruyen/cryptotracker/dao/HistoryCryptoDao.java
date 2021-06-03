package be.tomtruyen.cryptotracker.dao;

import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.domain.HistoryCrypto;
import be.tomtruyen.cryptotracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryCryptoDao extends JpaRepository<HistoryCrypto, Long> {
    List<HistoryCrypto> findHistoryCryptoByUserOrderBySellDate(User user);
}
