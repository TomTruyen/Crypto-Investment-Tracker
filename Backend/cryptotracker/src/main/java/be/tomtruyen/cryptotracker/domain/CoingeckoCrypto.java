package be.tomtruyen.cryptotracker.domain;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class CoingeckoCrypto implements Serializable {
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    private final String id;
    private final String symbol;
    private final String name;
    private final String image;
    private final double price;
    private final double marketCap;
    private final  int rank;
    private final double fullyDilutedValuation;
    private final double volume_24h;
    private final double high_24h;
    private final double low_24h;
    private final double allTimeHigh;
    private final double allTimeHighPercentage; //Percentage between ATH & NOW
    private final LocalDate allTimeHighDate;
    private final double allTimeLow;
    private final double allTimeLowPercentage;
    private final LocalDate allTimeLowDate;
    private final long circulatingSupply;
    private final long totalSupply;
    private final long maxSupply;
    private final double market_cap_change_24h;
    private final double market_cap_percent_change_24h;
    private final double price_change_24h;
    private final double price_percent_change_24h;
    private final double price_percent_change_7d;
    private final double price_percent_change_30d;
    private final double price_percent_change_1y;
    private final LocalDate lastUpdated;
    private Color color;

    public CoingeckoCrypto(String id, String symbol, String name, String image, double price, double marketCap, int rank, double fullyDilutedValuation, double volume_24h, double high_24h, double low_24h, double allTimeHigh, double allTimeHighPercentage, LocalDate allTimeHighDate, double allTimeLow, double allTimeLowPercentage, LocalDate allTimeLowDate, long circulatingSupply, long totalSupply, long maxSupply, double market_cap_change_24h, double market_cap_percent_change_24h, double price_change_24h, double price_percent_change_24h, double price_percent_change_7d, double price_percent_change_30d, double price_percent_change_1y, LocalDate lastUpdated) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.image = image;
        this.price = price;
        this.marketCap = marketCap;
        this.rank = rank;
        this.fullyDilutedValuation = fullyDilutedValuation;
        this.volume_24h = volume_24h;
        this.high_24h = high_24h;
        this.low_24h = low_24h;
        this.allTimeHigh = allTimeHigh;
        this.allTimeHighPercentage = allTimeHighPercentage;
        this.allTimeHighDate = allTimeHighDate;
        this.allTimeLow = allTimeLow;
        this.allTimeLowPercentage = allTimeLowPercentage;
        this.allTimeLowDate = allTimeLowDate;
        this.circulatingSupply = circulatingSupply;
        this.totalSupply = totalSupply;
        this.maxSupply = maxSupply;
        this.market_cap_change_24h = market_cap_change_24h;
        this.market_cap_percent_change_24h = market_cap_percent_change_24h;
        this.price_change_24h = price_change_24h;
        this.price_percent_change_24h = price_percent_change_24h;
        this.price_percent_change_7d = price_percent_change_7d;
        this.price_percent_change_30d = price_percent_change_30d;
        this.price_percent_change_1y = price_percent_change_1y;
        this.lastUpdated = lastUpdated;
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public int getRank() {
        return rank;
    }

    public double getFullyDilutedValuation() {
        return fullyDilutedValuation;
    }

    public double getVolume_24h() {
        return volume_24h;
    }

    public double getHigh_24h() {
        return high_24h;
    }

    public double getLow_24h() {
        return low_24h;
    }

    public double getAllTimeHigh() {
        return allTimeHigh;
    }

    public double getAllTimeHighPercentage() {
        return allTimeHighPercentage;
    }

    public LocalDate getAllTimeHighDate() {
        return allTimeHighDate;
    }

    public double getAllTimeLow() {
        return allTimeLow;
    }

    public double getAllTimeLowPercentage() {
        return allTimeLowPercentage;
    }

    public LocalDate getAllTimeLowDate() {
        return allTimeLowDate;
    }

    public long getCirculatingSupply() {
        return circulatingSupply;
    }

    public long getTotalSupply() {
        return totalSupply;
    }

    public long getMaxSupply() {
        return maxSupply;
    }

    public double getMarket_cap_change_24h() {
        return market_cap_change_24h;
    }

    public double getMarket_cap_percent_change_24h() {
        return market_cap_percent_change_24h;
    }

    public double getPrice_change_24h() {
        return price_change_24h;
    }

    public double getPrice_percent_change_24h() {
        return price_percent_change_24h;
    }

    public double getPrice_percent_change_7d() {
        return price_percent_change_7d;
    }

    public double getPrice_percent_change_30d() {
        return price_percent_change_30d;
    }

    public double getPrice_percent_change_1y() {
        return price_percent_change_1y;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static CoingeckoCrypto fromJSON(Map<String, Object> json) {


        String id = (String) json.getOrDefault("id", "");
        String symbol = (String) json.getOrDefault("symbol", "");
        symbol = symbol.toUpperCase();
        String name = (String) json.getOrDefault("name", "");
        String image = (String) json.getOrDefault("image", "");
        double price = (json.getOrDefault("current_price", 0) == null ? 0 : (double) json.getOrDefault("current_price", 0));
        double marketCap = (json.getOrDefault("market_cap", 0) == null ? 0 : (double) json.getOrDefault("market_cap", 0));
        int rank = (int) Math.round((json.getOrDefault("market_cap_rank", 0) == null ? 0 : (double) json.getOrDefault("market_cap_rank", 0)));
        double fullyDilutedValuation = (json.getOrDefault("fully_diluted_valuation", 0) == null ? 0 : (double) json.getOrDefault("fully_diluted_valuation", 0));
        double volume_24h = (json.getOrDefault("total_volume", 0) == null ? 0 : (double) json.getOrDefault("total_volume", 0));
        double high_24h = (json.getOrDefault("high_24h", 0) == null ? 0 : (double) json.getOrDefault("high_24h", 0));
        double low_24h = (json.getOrDefault("low_24h", 0) == null ? 0 : (double) json.getOrDefault("low_24h", 0));
        double allTimeHigh = (json.getOrDefault("ath", 0) == null ? 0 : (double) json.getOrDefault("ath", 0));
        double allTimeHighPercentage = (json.getOrDefault("ath_change_percentage", 0) == null ? 0 : (double) json.getOrDefault("ath_change_percentage", 0));
        LocalDate allTimeHighDate = null;
        if(json.get("ath_date") != null) allTimeHighDate = LocalDate.parse((String)json.getOrDefault("ath_date", "1970-01-01T00:00:00.000Z"), dateFormatter);
        double allTimeLow = (json.getOrDefault("atl", 0) == null ? 0 : (double) json.getOrDefault("atl", 0));
        double allTimeLowPercentage = (json.getOrDefault("atl_change_percentage", 0) == null ? 0 : (double) json.getOrDefault("atl_change_percentage", 0));
        LocalDate allTimeLowDate = null;
        if(json.get("atl_date") != null) allTimeLowDate = LocalDate.parse((String)json.getOrDefault("atl_date", "1970-01-01T00:00:00.000Z"), dateFormatter);
        long circulatingSupply = (long) (json.getOrDefault("circulating_supply", 0) == null ? 0 : (double) json.getOrDefault("circulating_supply", 0));
        long totalSupply = (long) (json.getOrDefault("total_supply", 0) == null ? 0 : (double) json.getOrDefault("total_supply", 0));
        long maxSupply = (long) (json.getOrDefault("max_supply", 0) == null ? 0 : (double) json.getOrDefault("max_supply", 0));
        double market_cap_change_24h = (json.getOrDefault("market_cap_change_24h", (double) 0) == null ? 0 : (double) json.getOrDefault("market_cap_change_24h", 0));
        double market_cap_percent_change_24h = (json.getOrDefault("market_cap_change_percentage_24h", (double) 0) == null ? 0 : (double) json.getOrDefault("market_cap_change_percentage_24h", 0));
        double price_change_24h = (json.getOrDefault("price_change_24h", (double) 0) == null ? 0 : (double) json.getOrDefault("price_change_24h", 0));
        double price_percent_change_24h = (json.getOrDefault("price_change_percentage_24h_in_currency", (double) 0) == null ? 0 : (double) json.getOrDefault("price_change_percentage_24h_in_currency", 0));
        double price_percent_change_7d = (json.getOrDefault("price_change_percentage_7d_in_currency", (double) 0) == null ? 0 : (double) json.getOrDefault("price_change_percentage_7d_in_currency", 0));
        double price_percent_change_30d = (json.getOrDefault("price_change_percentage_30d_in_currency", (double) 0) == null ? 0 : (double) json.getOrDefault("price_change_percentage_30d_in_currency", 0));
        double price_percent_change_1y = (json.getOrDefault("price_change_percentage_1y_in_currency", (double) 0) == null ? 0 : (double) json.getOrDefault("price_change_percentage_1y_in_currency", 0));
        LocalDate lastUpdated = LocalDate.now();
        if(json.get("last_updated") != null) lastUpdated = LocalDate.parse((String) json.get("last_updated"), dateFormatter);

        return new CoingeckoCrypto(id, symbol, name, image, price, marketCap, rank, fullyDilutedValuation, volume_24h, high_24h, low_24h, allTimeHigh, allTimeHighPercentage, allTimeHighDate, allTimeLow, allTimeLowPercentage, allTimeLowDate, circulatingSupply, totalSupply, maxSupply, market_cap_change_24h, market_cap_percent_change_24h, price_change_24h, price_percent_change_24h, price_percent_change_7d, price_percent_change_30d, price_percent_change_1y, lastUpdated);
    }
}
