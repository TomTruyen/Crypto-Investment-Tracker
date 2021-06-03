package be.tomtruyen.cryptotracker.rest.resources;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

public class CryptoBuyResource {
    @NotEmpty(message = "Ticker can't be empty")
    private String ticker;
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private double amount;
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private double price;

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
}
