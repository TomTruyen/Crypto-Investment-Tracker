export default class Crypto {
    constructor(id, name, ticker, buyAmount, buyPrice, buyDate, sellAmount, sellPrice, sellDate) {
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

    static fromJSON(json) {
        return new Crypto(
            json['id'],
            json['name'],
            json['ticker'],
            json['buyAmount'],
            json['buyPrice'],
            json['buyDate'],
            json['sellAmount'],
            json['sellPrice'],
            json['sellDate'],
        );
    }

    calculateProfit(currentPrice) {
        let increase = currentPrice - this.buyPrice;
        let profit = (increase / this.buyPrice * 100);

        return Number(profit.toFixed(2));
    }

    calculateProfitUSD(amount, buyPrice, sellPrice) {
        return Number(((amount * buyPrice) - (amount * sellPrice)).toFixed(2));
    }

    toOption(currentPrice, change_24h) {
        let value = (this.buyAmount * currentPrice);
        let profit = this.calculateProfit(currentPrice);
        let price = currentPrice > 1 ? currentPrice.toFixed(2) : currentPrice.toFixed(6);


        return {
            "id": this.id,
            "name": `${this.name} (${this.ticker})`,
            "price": `$${price}`,
            "change_24h": `${change_24h.toFixed(2)}%`,
            "balance": this.buyAmount - this.sellAmount,
            "value": `$${value.toFixed(2)}`,
            "profit": `${profit.toFixed(2)}%`,
            "ticker": this.ticker,
        };
    }

    toHistoryOption() {
        let profit = this.calculateProfit(this.sellPrice);
        let profitUSD = this.calculateProfitUSD(this.sellAmount, this.buyPrice, this.sellPrice);

        return {
            'date': this.sellDate,
            'name': `${this.name} (${this.ticker})`,
            'buy_price': `$${this.buyPrice.toFixed(2)}`,
            'sell_amount': this.sellAmount,
            'sell_price': `$${this.sellPrice.toFixed(2)}`,
            'profit': `${profit.toFixed(2)}%`,
            'profit_usd': `$${profitUSD.toFixed(2)}`,
        };
    }
}