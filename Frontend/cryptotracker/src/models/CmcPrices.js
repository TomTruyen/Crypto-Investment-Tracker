export default class CmcPrices {
    constructor(id, rank, name, ticker, maxSupply, circulatingSupply, totalSupply, tokenProvider, price, volume_24h, percent_change_1h, percent_change_24h, percent_change_7d, percent_change_30d, percent_change_60d, percent_change_90d, marketCap, lastUpdated) {
        this.id = id;
        this.rank = rank;
        this.name = name;
        this.ticker = ticker;
        this.maxSupply = maxSupply;
        this.circulatingSupply = circulatingSupply;
        this.totalSupply = totalSupply;
        this.tokenProvider = tokenProvider;
        this.price = price;
        this.volume_24h = volume_24h;
        this.percent_change_1h = percent_change_1h;
        this.percent_change_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d
        this.percent_change_30d = percent_change_30d;
        this.percent_change_60d = percent_change_60d;
        this.percent_change_90d = percent_change_90d;
        this.marketCap = marketCap;
        this.lastUpdated = lastUpdated;
    }

    static fromJSON(json) {
        return new CmcPrices(
            json['id'],
            json['rank'],
            json['name'],
            json['ticker'],
            json['maxSupply'],
            json['circulatingSupply'],
            json['totalSupply'],
            json['tokenProvider'],
            json['price'],
            json['volume_24h'],
            json['percent_change_1h'],
            json['percent_change_24h'],
            json['percent_change_7d'],
            json['percent_change_30d'],
            json['percent_change_60d'],
            json['percent_change_90d'],
            json['marketCap'],
            json['lastUpdated']
        );
    }
}