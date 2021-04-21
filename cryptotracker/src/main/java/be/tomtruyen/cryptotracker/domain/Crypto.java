package be.tomtruyen.cryptotracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


public class Crypto {
    private int id;
    @JsonIgnore
    private int userId;
    private String name;
    private String ticker;
    private double buyAmount;
    private double buyPrice;
    private Date buyDate;
    private double sellAmount;
    private double sellPrice;
    private Date sellDate;

    public Crypto(int id, String name, String ticker, double buyAmount, double buyPrice, Date buyDate, double sellAmount, double sellPrice, Date sellDate) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.buyAmount = buyAmount;
        this.buyPrice = buyPrice;
        this.buyDate = buyDate;
        this.sellAmount = sellAmount;
        this.sellPrice = sellPrice;
        this.sellDate = sellDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(double buyAmount) {
        this.buyAmount = buyAmount;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public double getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(double sellAmount) {
        this.sellAmount = sellAmount;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }
}
