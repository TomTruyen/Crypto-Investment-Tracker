package be.tomtruyen.cryptotracker.repositories;

import be.tomtruyen.cryptotracker.domain.CmcCrypto;
import be.tomtruyen.cryptotracker.domain.CmcCryptoPrice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CryptoRepository {
    private static CryptoRepository instance;
    private static Object monitor = new Object();
    private List<CmcCrypto> cryptos = Collections.synchronizedList(new ArrayList<>());

    private CryptoRepository() {}

    public void set(List<CmcCrypto> cryptos) {
        clear();
        this.cryptos.addAll(cryptos);
    }

    public void add(CmcCrypto crypto) {
        this.cryptos.add(crypto);
    }

    public List<CmcCrypto> getAll() {
        return cryptos;
    }

    public void clear() {
        cryptos.clear();
    }

    public static CryptoRepository getInstance() {
        if(instance == null) {
            synchronized (monitor) {
                if(instance == null) {
                    instance = new CryptoRepository();
                }
            }
        }

        return instance;
    }
}
