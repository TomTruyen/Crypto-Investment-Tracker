package be.tomtruyen.cryptotracker.model;

import be.tomtruyen.cryptotracker.rest.resources.CryptoSetAlertResource;

public class CryptoSetAlertResourceBuilder {
    private static final Long ID = 1L;
    private static final double ALERT = 1;

    private Long id = ID;
    private double alert = ALERT;

    private CryptoSetAlertResourceBuilder() {}

    public static CryptoSetAlertResourceBuilder anCryptoSetAlertResource() { return new CryptoSetAlertResourceBuilder(); }

    public CryptoSetAlertResourceBuilder withId(Long id) {
        this.id = id;

        return this;
    }

    public CryptoSetAlertResourceBuilder withAlert(double alert) {
        this.alert = alert;

        return this;
    }

    public CryptoSetAlertResource build() {
        CryptoSetAlertResource cryptoSetAlertResource = new CryptoSetAlertResource();
        cryptoSetAlertResource.setId(id);
        cryptoSetAlertResource.setAlert(alert);
        return cryptoSetAlertResource;
    }
}
