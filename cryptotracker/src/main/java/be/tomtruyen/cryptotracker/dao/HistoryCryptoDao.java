package be.tomtruyen.cryptotracker.dao;

import be.tomtruyen.cryptotracker.domain.HistoryCrypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryCryptoDao extends JpaRepository<HistoryCrypto, Long> {
}
