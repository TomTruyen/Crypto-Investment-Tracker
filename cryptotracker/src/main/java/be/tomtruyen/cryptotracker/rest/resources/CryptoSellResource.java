package be.tomtruyen.cryptotracker.rest.resources;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CryptoSellResource {
    @Min(value = 1, message = "Id invalid")
    private Long id;
    @NotEmpty(message = "Ticker can't be empty")
    private String ticker;
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private double amount;
    private double price;
    private boolean isGas = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isGas() {
        return isGas;
    }

    public void setGas(boolean gas) {
        isGas = gas;
    }
}
