package be.tomtruyen.cryptotracker.model;

import be.tomtruyen.cryptotracker.rest.resources.CryptoDeleteAlertResource;

public class CryptoDeleteAlertResourceBuilder {
    private static final Long ID = 1L;

    private Long id = ID;

    private CryptoDeleteAlertResourceBuilder() {}

    public static CryptoDeleteAlertResourceBuilder anCryptoSetAlertResource() { return new CryptoDeleteAlertResourceBuilder(); }

    public CryptoDeleteAlertResourceBuilder withId(Long id) {
        this.id = id;

        return this;
    }

    public CryptoDeleteAlertResource build() {
        CryptoDeleteAlertResource cryptoDeleteAlertResource = new CryptoDeleteAlertResource();
        cryptoDeleteAlertResource.setId(id);
        return cryptoDeleteAlertResource;
    }
}
