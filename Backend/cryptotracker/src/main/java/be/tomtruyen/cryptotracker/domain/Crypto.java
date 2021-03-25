package be.tomtruyen.cryptotracker.domain;

import java.util.Date;

public class Crypto {
    private int id;
    private int userId;
    private String name;
    private String ticker;
    private int buyAmount;
    private double buyPrice;
    private Date buyDate;
    private int sellAmount;
    private double sellPrice;
    private Date sellDate;

    public Crypto(int id, String name, String ticker, int buyAmount, double buyPrice, Date buyDate, int sellAmount, double sellPrice, Date sellDate) {
        this.id = id;
        this.userId = userId;
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

    public int getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(int buyAmount) {
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

    public int getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(int sellAmount) {
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
