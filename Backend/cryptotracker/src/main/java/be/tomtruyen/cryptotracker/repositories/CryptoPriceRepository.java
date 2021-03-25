package be.tomtruyen.cryptotracker.repositories;

import be.tomtruyen.cryptotracker.domain.CmcCryptoPrice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CryptoPriceRepository {
    private static CryptoPriceRepository instance;
    private static Object monitor = new Object();
    private List<CmcCryptoPrice> prices = Collections.synchronizedList(new ArrayList<>());

    private CryptoPriceRepository() {}

    public void set(List<CmcCryptoPrice> prices) {
        clear();
        this.prices.addAll(prices);
    }

    public void add(CmcCryptoPrice price) {
        this.prices.add(price);
    }

    public void remove(CmcCryptoPrice price) {
        this.prices.remove(price);
    }

    public List<CmcCryptoPrice> getAll() {
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
