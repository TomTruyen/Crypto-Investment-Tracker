export default class Crypto {
    constructor(id, name, ticker, buyAmount, buyPrice, buyDate, sellAmount) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.buyAmount = buyAmount;
        this.buyPrice = buyPrice;
        this.buyDate = buyDate;
        this.sellAmount = sellAmount;
    }

    static fromJSON(json) {
        return new Crypto(
            json['id'],
            json['name'],
            json['ticker'],
            json['buyAmount'],
            json['buyPrice'],
            json['buyDate'],
            json['sellAmount']
        );
    }

    toOption(currentPrice, change_24h) {
        let increase = currentPrice - this.buyPrice;
        let profit = (increase / this.buyPrice * 100).toFixed(2);

        let value = (this.buyAmount * currentPrice).toFixed(2);

        let price = currentPrice > 1 ? currentPrice.toFixed(2) : currentPrice.toFixed(6);


        return {
            "id": this.id,
            "name": `${this.name} (${this.ticker})`,
            "price": `$${price}`,
            "change_24h": `${change_24h.toFixed(2)}%`,
            "balance": this.buyAmount - this.sellAmount,
            "value": `$${value}`,
            "profit": `${profit}%`,
            "ticker": this.ticker,
        };
    }
}