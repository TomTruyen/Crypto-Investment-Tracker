package be.tomtruyen.cryptotracker.domain;

import java.util.Map;

public class CryptoPrice {
    private int id;
    private int rank;
    private final String name;
    private final String ticker;
    private int maxSupply;
    private final double circulatingSupply;
    private final double totalSupply;
    private final Map<String, Object> tokenProvider; // Ethereum, Cardano, Tron,...
    private final double price;
    private final double volume_24h;
    private final double percent_change_1h;
    private final double percent_change_24h;
    private final double percent_change_7d;
    private final double percent_change_30d;
    private final double percent_change_60d;
    private final double percent_change_90d;
    private final double marketCap;
    private final String lastUpdated;

    public CryptoPrice(int id, int rank, String name, String ticker, int maxSupply, double circulatingSupply, double totalSupply, Map<String, Object> tokenProvider, double price, double volume_24h, double percent_change_1h, double percent_change_24h, double percent_change_7d, double percent_change_30d, double percent_change_60d, double percent_change_90d, double marketCap, String lastUpdated) {
        if (id != -1) this.id = id;
        if (rank != -1) this.rank = rank;
        this.name = name;
        this.ticker = ticker;
        if (maxSupply != -1) this.maxSupply = maxSupply;
        this.circulatingSupply = circulatingSupply;
        this.totalSupply = totalSupply;
        this.tokenProvider = tokenProvider;
        this.price = price;
        this.volume_24h = volume_24h;
        this.percent_change_1h = percent_change_1h;
        this.percent_change_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.percent_change_30d = percent_change_30d;
        this.percent_change_60d = percent_change_60d;
        this.percent_change_90d = percent_change_90d;
        this.marketCap = marketCap;
        this.lastUpdated = lastUpdated;
    }

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public int getMaxSupply() {
        return maxSupply;
    }

    public double getCirculatingSupply() {
        return circulatingSupply;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public Map<String, Object> getTokenProvider() {
        return tokenProvider;
    }

    public double getPrice() {
        return price;
    }

    public double getVolume_24h() {
        return volume_24h;
    }

    public double getPercent_change_1h() {
        return percent_change_1h;
    }

    public double getPercent_change_24h() {
        return percent_change_24h;
    }

    public double getPercent_change_7d() {
        return percent_change_7d;
    }

    public double getPercent_change_30d() {
        return percent_change_30d;
    }

    public double getPercent_change_60d() {
        return percent_change_60d;
    }

    public double getPercent_change_90d() {
        return percent_change_90d;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public static CryptoPrice fromJSON(Map<String, Object> json) {
        int idInteger = -1;
        var id = json.getOrDefault("id", null);
        if(id != null) idInteger = ((Double) id).intValue();

        int rankInteger = -1;
        var rank = json.getOrDefault("cmc_rank", null);
        if(rank != null) rankInteger = ((Double) rank).intValue();

        String name = (String) json.getOrDefault("name", null);
        String ticker = (String) json.getOrDefault("symbol", null);

        int maxSupplyInteger = -1;
        var maxSupply = json.getOrDefault("max_supply", null);
        if(maxSupply != null) maxSupplyInteger = ((Double) maxSupply).intValue();

        double circulatingSupply = (double) json.getOrDefault("circulating_supply", null);
        double totalSupply = (double) json.getOrDefault("total_supply", null);

        Map<String, Object> tokenProvider = (Map<String, Object>) json.getOrDefault("platform", null);

        Map<String, Object> prices = (Map<String, Object>)((Map<String, Object>)json.get("quote")).get("USD");
        double price = (double) prices.getOrDefault("price", null);
        double volume_24h = (double) prices.getOrDefault("volume_24h", null);
        double percent_change_1h = (double) prices.getOrDefault("percent_change_1h", null);
        double percent_change_24h = (double) prices.getOrDefault("percent_change_24h", null);
        double percent_change_7d = (double) prices.getOrDefault("percent_change_7d", null);
        double percent_change_30d = (double) prices.getOrDefault("percent_change_30d", null);
        double percent_change_60d = (double) prices.getOrDefault("percent_change_60d", null);
        double percent_change_90d = (double) prices.getOrDefault("percent_change_90d", null);
        double market_cap = (double) prices.getOrDefault("market_cap", null);

        String lastUpdated = (String) json.getOrDefault("last_updated", null);

        try {
            return new CryptoPrice(idInteger, rankInteger, name, ticker, maxSupplyInteger, circulatingSupply, totalSupply, tokenProvider, price, volume_24h, percent_change_1h, percent_change_24h, percent_change_7d, percent_change_30d, percent_change_60d, percent_change_90d, market_cap, lastUpdated);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
