package be.tomtruyen.cryptotracker.repositories;

import be.tomtruyen.cryptotracker.domain.CryptoPrice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CryptoPriceRepository {
    private static CryptoPriceRepository instance;
    private static Object monitor = new Object();
    private List<CryptoPrice> prices = Collections.synchronizedList(new ArrayList<>());

    private CryptoPriceRepository() {}

    public void set(List<CryptoPrice> prices) {
        clear();
        this.prices.addAll(prices);
    }

    public void add(CryptoPrice price) {
        this.prices.add(price);
    }

    public void remove(CryptoPrice price) {
        this.prices.remove(price);
    }

    public List<CryptoPrice> getAll() {
        return prices;
    }

    public void clear() {
        prices.clear();
    }

    public static CryptoPriceRepository getInstance() {
        if(instance == null) {
            synchronized (monitor) {
                if(instance == null) {
                    instance = new CryptoPriceRepository();
                }
            }
        }

        return instance;
    }
}
