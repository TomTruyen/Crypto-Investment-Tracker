package be.tomtruyen.cryptotracker.domain;

import java.util.Map;

public class CmcCrypto {
    private int id;
    private int rank;
    private final String name;
    private final String ticker;
    private final Map<String, Object> tokenProvider; // Ethereum, Cardano, Tron,...
    private String imageUri;

    public CmcCrypto(int id, int rank, String name, String ticker, Map<String, Object> tokenProvider) {
        if(id != -1) this.id = id;
        this.rank = rank;
        this.name = name;
        this.ticker = ticker;
        this.tokenProvider = tokenProvider;
        if (id != -1) this.imageUri = "https://s2.coinmarketcap.com/static/img/coins/64x64/" + id + ".png";
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

    public Map<String, Object> getTokenProvider() {
        return tokenProvider;
    }

    public String getImageUri() {
        return imageUri;
    }

    public static CmcCrypto fromJSON(Map<String, Object> json, int rank) {
        int idInteger = -1;
        var id = json.getOrDefault("id", null);
        if(id != null) idInteger = ((Double) id).intValue();

        String name = (String) json.getOrDefault("name", null);
        String ticker = (String) json.getOrDefault("symbol", null);

        Map<String, Object> tokenProvider = (Map<String, Object>) json.getOrDefault("platform", null);


        try {
            return new CmcCrypto(idInteger, rank, name, ticker, tokenProvider);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
