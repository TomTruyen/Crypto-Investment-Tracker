export default class CoingeckoCrypto {
    constructor(id, ticker, name, image, price, marketCap, rank, fullyDilutedValuation, volume_24h, high_24h, low_24h, allTimeHigh, allTimeHighPercentage, allTimeHighDate, allTimeLow, allTimeLowPercentage, allTimeLowDate, circulatingSupply, totalSupply, maxSupply, market_cap_change_24h, market_cap_percent_change_24h, price_change_24h, price_percent_change_24h, price_percent_change_7d, price_percent_change_30d, price_percent_change_1y, lastUpdated) {
        this.id = id;
        this.ticker = ticker;
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

    static fromJSON(json) {
        return new CoingeckoCrypto(
            json['id'],
            json['symbol'],
            json['name'],
            json['image'],
            json['price'],
            json['marketCap'],
            json['rank'],
            json['fullyDilutedValuation'],
            json['volume_24h'],
            json['high_24h'],
            json['low_24h'],
            json['allTimeHigh'],
            json['allTimeHighPercentage'],
            json['allTimeHighDate'],
            json['allTimeLow'],
            json['allTimeLowPercentage'],
            json['allTimeLowDate'],
            json['circulatingSupply'],
            json['totalSupply'],
            json['maxSupply'],
            json['market_cap_change_24h'],
            json['market_cap_percentage_change_24h'],
            json['price_change_24h'],
            json['price_percent_change_24h'],
            json['price_percent_change_7d'],
            json['price_percent_change_30d'],
            json['price_percent_change_1y'],
            json['lastUpdated']
        );
    }

    toOption() {
        return {
            "value": this.ticker,
            "label": `${this.name} (${this.ticker})`,
            "img": this.image
        };
    }
}