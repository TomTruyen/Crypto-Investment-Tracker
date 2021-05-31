package be.tomtruyen.cryptotracker.repositories;

import be.tomtruyen.cryptotracker.domain.CoingeckoCrypto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CryptoRepository {
    private static CryptoRepository instance;
    private static Object monitor = new Object();
    private List<CoingeckoCrypto> cryptos = Collections.synchronizedList(new ArrayList<>());
    private int count = 1;

    private CryptoRepository() {}

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        this.count++;
    }

    public void set(List<CoingeckoCrypto> cryptos) {
        clear();
        this.cryptos.addAll(cryptos);
    }

    public void add(CoingeckoCrypto crypto) {
        int index = -1;

        for(int i = 0; i < cryptos.size(); i++) {
            if(cryptos.get(i).getSymbol().equalsIgnoreCase(crypto.getSymbol())) {
                index = i;
                break;
            }
        }

        if(index > -1) {
            crypto.setColor(this.cryptos.get(index).getColor());

            this.cryptos.set(index, crypto);
        } else {
            this.cryptos.add(crypto);
        }
    }

    public CoingeckoCrypto find(String ticker) {
        return cryptos.stream().filter(c -> c.getSymbol().equalsIgnoreCase(ticker)).findFirst().orElse(null);
    }

    public List<CoingeckoCrypto> getAll() {
        return cryptos;
    }

    public List<CoingeckoCrypto> get(int limit) {
        return cryptos.stream().limit(limit).collect(Collectors.toList());
    }

    public void sortByRank() {
        this.set(cryptos.stream().sorted(Comparator.comparing(CoingeckoCrypto::getRank)).collect(Collectors.toList()));
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
