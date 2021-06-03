package be.tomtruyen.cryptotracker.domain;

import com.google.gson.annotations.SerializedName;

import java.awt.*;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CoingeckoCrypto implements Serializable {
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    @SerializedName("id")
    private String id;
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("current_price")
    private double price;
    @SerializedName("market_cap")
    private double marketCap;
    @SerializedName("market_cap_rank")
    private int rank;
    @SerializedName("fully_diluted_valuation")
    private double fullyDilutedValuation;
    @SerializedName("total_volume")
    private double volume24h;
    @SerializedName("high_24h")
    private double high24h;
    @SerializedName("low_24h")
    private double low24h;
    @SerializedName("ath")
    private double allTimeHigh;
    @SerializedName("ath_change_percentage")
    private double allTimeHighPercentage;
    @SerializedName("ath_date")
    private String allTimeHighDate;
    @SerializedName("atl")
    private double allTimeLow;
    @SerializedName("atl_change_percentage")
    private double allTimeLowPercentage;
    @SerializedName("atl_date")
    private String allTimeLowDate;
    @SerializedName("circulating_supply")
    private double circulatingSupply;
    @SerializedName("total_supply")
    private double totalSupply;
    @SerializedName("max_supply")
    private double maxSupply;
    @SerializedName("market_cap_change_24h")
    private double marketCapChange24h;
    @SerializedName("market_cap_change_percentage_24h")
    private double marketCapPercentChange24h;
    @SerializedName("price_change_24h")
    private double priceChange24h;
    @SerializedName("price_change_percentage_24h")
    private double pricePercentChange24h;
    @SerializedName("price_change_percentage_7d_in_currency")
    private double pricePercentChange7d;
    @SerializedName("price_change_percentage_30d_in_currency")
    private double pricePercentChange30d;
    @SerializedName("price_change_percentage_1y_in_currency")
    private double pricePercentChange1y;
    @SerializedName("last_updated")
    private String lastUpdated;
    private transient Color color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getFullyDilutedValuation() {
        return fullyDilutedValuation;
    }

    public void setFullyDilutedValuation(double fullyDilutedValuation) {
        this.fullyDilutedValuation = fullyDilutedValuation;
    }

    public double getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(double volume24h) {
        this.volume24h = volume24h;
    }

    public double getHigh24h() {
        return high24h;
    }

    public void setHigh24h(double high24h) {
        this.high24h = high24h;
    }

    public double getLow24h() {
        return low24h;
    }

    public void setLow24h(double low24h) {
        this.low24h = low24h;
    }

    public double getAllTimeHigh() {
        return allTimeHigh;
    }

    public void setAllTimeHigh(double allTimeHigh) {
        this.allTimeHigh = allTimeHigh;
    }

    public double getAllTimeHighPercentage() {
        return allTimeHighPercentage;
    }

    public void setAllTimeHighPercentage(double allTimeHighPercentage) {
        this.allTimeHighPercentage = allTimeHighPercentage;
    }

    public String getAllTimeHighDate() {
        return allTimeHighDate;
    }

    public void setAllTimeHighDate(String allTimeHighDate) {
        this.allTimeHighDate = allTimeHighDate;
    }

    public double getAllTimeLow() {
        return allTimeLow;
    }

    public void setAllTimeLow(double allTimeLow) {
        this.allTimeLow = allTimeLow;
    }

    public double getAllTimeLowPercentage() {
        return allTimeLowPercentage;
    }

    public void setAllTimeLowPercentage(double allTimeLowPercentage) {
        this.allTimeLowPercentage = allTimeLowPercentage;
    }

    public String getAllTimeLowDate() {
        return allTimeLowDate;
    }

    public void setAllTimeLowDate(String allTimeLowDate) {
        this.allTimeLowDate = allTimeLowDate;
    }

    public long getCirculatingSupply() {
        return (long) circulatingSupply;
    }

    public void setCirculatingSupply(double circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public long getTotalSupply() {
        return (long) totalSupply;
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public long getMaxSupply() {
        return (long) maxSupply;
    }

    public void setMaxSupply(double maxSupply) {
        this.maxSupply = maxSupply;
    }

    public double getMarketCapChange24h() {
        return marketCapChange24h;
    }

    public void setMarketCapChange24h(double marketCapChange24h) {
        this.marketCapChange24h = marketCapChange24h;
    }

    public double getMarketCapPercentChange24h() {
        return marketCapPercentChange24h;
    }

    public void setMarketCapPercentChange24h(double marketCapPercentChange24h) {
        this.marketCapPercentChange24h = marketCapPercentChange24h;
    }

    public double getPriceChange24h() {
        return priceChange24h;
    }

    public void setPriceChange24h(double priceChange24h) {
        this.priceChange24h = priceChange24h;
    }

    public double getPricePercentChange24h() {
        return pricePercentChange24h;
    }

    public void setPricePercentChange24h(double pricePercentChange24h) {
        this.pricePercentChange24h = pricePercentChange24h;
    }

    public double getPricePercentChange7d() {
        return pricePercentChange7d;
    }

    public void setPricePercentChange7d(double pricePercentChange7d) {
        this.pricePercentChange7d = pricePercentChange7d;
    }

    public double getPricePercentChange30d() {
        return pricePercentChange30d;
    }

    public void setPricePercentChange30d(double pricePercentChange30d) {
        this.pricePercentChange30d = pricePercentChange30d;
    }

    public double getPricePercentChange1y() {
        return pricePercentChange1y;
    }

    public void setPricePercentChange1y(double pricePercentChange1y) {
        this.pricePercentChange1y = pricePercentChange1y;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
