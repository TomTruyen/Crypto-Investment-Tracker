package be.tomtruyen.cryptotracker.model;

import be.tomtruyen.cryptotracker.rest.resources.CryptoBuyResource;

public class CryptoBuyResourceBuilder {
    private static final String TICKER = "BTC";
    private static final double AMOUNT = 1;
    private static final double PRICE = 1;

    private String ticker = TICKER;
    private double amount = AMOUNT;
    private double price = PRICE;

    private CryptoBuyResourceBuilder() {}

    public static CryptoBuyResourceBuilder anCryptoBuyResource() { return new CryptoBuyResourceBuilder(); }

    public CryptoBuyResourceBuilder withTicker(String ticker) {
        this.ticker = ticker;

        return this;
    }

    public CryptoBuyResourceBuilder withAmount(double amount) {
        this.amount = amount;

        return this;
    }

    public CryptoBuyResourceBuilder withPrice(double price) {
        this.price = price;

        return this;
    }

    public CryptoBuyResource build() {
        CryptoBuyResource cryptoBuyResource = new CryptoBuyResource();
        cryptoBuyResource.setTicker(ticker);
        cryptoBuyResource.setAmount(amount);
        cryptoBuyResource.setPrice(price);
        return cryptoBuyResource;
    }
}
