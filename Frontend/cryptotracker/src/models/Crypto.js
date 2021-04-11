import Utils from '@/utils/Utils.js';

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
        return Number(((amount * sellPrice) - (amount * buyPrice)).toFixed(2));
    }

    getBalance() {
        if (isNaN(this.sellAmount)) return this.buyAmount;

        return this.buyAmount - this.sellAmount;
    }

    getValue(currentPrice) {
        return Number(this.getBalance() * currentPrice);
    }




    toOption(crypto) {
        let rank = crypto.rank;
        let marketCap = crypto.marketCap;
        let currentPrice = crypto.price;
        let change_24h = crypto.price_percent_change_24h;
        let change_7d = crypto.price_percent_change_7d;
        let change_30d = crypto.price_percent_change_30d;
        let change_1y = crypto.price_percent_change_1y;
        let ath = crypto.allTimeHigh;
        let atl = crypto.allTimeLow;


        const balance = this.getBalance();
        let value = this.getValue(currentPrice);
        let profit = this.calculateProfit(currentPrice);
        const profitUSD = this.calculateProfitUSD(balance, this.buyPrice, currentPrice);

        return {
            "id": this.id,
            "name": `${this.name} (${this.ticker})`,
            "price": `$${Utils.numberWithCommas(currentPrice, currentPrice > 1 ? 2 : 6)}`,
            "change_24h": `${Utils.numberWithCommas(change_24h)}%`,
            "balance": Utils.numberWithCommas(balance),
            "value": `$${Utils.numberWithCommas(value)}`,
            "profit": `${Utils.numberWithCommas(profit)}% ($${Utils.numberWithCommas(profitUSD)})`,
            "ticker": this.ticker,
            "details": {
                "rank": `#${rank}`,
                "marketCap": `$${Utils.numberWithCommas(marketCap)}`,
                "change_24h": `${Utils.numberWithCommas(change_24h)}%`,
                "change_7d": `${Utils.numberWithCommas(change_7d)}%`,
                "change_30d": `${Utils.numberWithCommas(change_30d)}%`,
                "change_1y": `${Utils.numberWithCommas(change_1y)}%`,
                "ATH": `$${Utils.numberWithCommas(ath)}`,
                "ATL": `$${Utils.numberWithCommas(atl)}`,
            },
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