package be.tomtruyen.cryptotracker.model;

import be.tomtruyen.cryptotracker.rest.resources.CryptoBuyResource;
import be.tomtruyen.cryptotracker.rest.resources.CryptoSellResource;

public class CryptoSellResourceBuilder {
    private static final Long ID = 1L;
    private static final String TICKER = "BTC";
    private static final double AMOUNT = 1;
    private static final double PRICE = 1;
    private static final boolean IS_GAS = false;

    private Long id = ID;
    private String ticker = TICKER;
    private double amount = AMOUNT;
    private double price = PRICE;
    private boolean isGas = IS_GAS;

    private CryptoSellResourceBuilder() {}

    public static CryptoSellResourceBuilder anCryptoBuyResource() { return new CryptoSellResourceBuilder(); }

    public CryptoSellResourceBuilder withId(Long id) {
        this.id = id;

        return this;
    }

    public CryptoSellResourceBuilder withTicker(String ticker) {
        this.ticker = ticker;

        return this;
    }

    public CryptoSellResourceBuilder withAmount(double amount) {
        this.amount = amount;

        return this;
    }

    public CryptoSellResourceBuilder withPrice(double price) {
        this.price = price;

        return this;
    }

    public CryptoSellResourceBuilder withGas(boolean isGas) {
        this.isGas = isGas;

        return this;
    }

    public CryptoSellResource build() {
        CryptoSellResource cryptoSellResource = new CryptoSellResource();
        cryptoSellResource.setId(id);
        cryptoSellResource.setTicker(ticker);
        cryptoSellResource.setAmount(amount);
        cryptoSellResource.setPrice(price);
        cryptoSellResource.setGas(isGas);
        return cryptoSellResource;
    }
}
