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
        let currentPrice = crypto.price;
        let change_24h = crypto.price_percent_change_24h;

        const balance = this.getBalance();
        let value = this.getValue(currentPrice);
        let profit = this.calculateProfit(currentPrice);
        const profitUSD = this.calculateProfitUSD(balance, this.buyPrice, currentPrice);

        return {
            "id": this.id,
            "name": this.name,
            "price": `$${Utils.numberWithCommas(currentPrice, currentPrice > 1 ? 2 : 6, true)}`,
            "change_24h": `${Utils.numberWithCommas(change_24h, 2, true)}%`,
            "balance": Utils.numberWithCommas(balance, 6),
            "value": `$${Utils.numberWithCommas(value, 2, true)}`,
            "profit": `${Utils.numberWithCommas(profit, 2, true)}% ($${Utils.numberWithCommas(profitUSD, 2, true)})`,
            "profitGreaterThanZero": profitUSD >= 0,
            "ticker": this.ticker,
            "details": crypto
        };
    }

    toHistoryOption(crypto) {
        let profit = this.calculateProfit(this.sellPrice);
        let profitUSD = this.calculateProfitUSD(this.sellAmount, this.buyPrice, this.sellPrice);

        return {
            'date': this.sellDate,
            'name': `${this.name} (${this.ticker})`,
            'buy_price': `$${this.buyPrice.toFixed(2)}`,
            'sell_amount': this.sellAmount,
            'sell_price': `$${this.sellPrice.toFixed(2)}`,
            'profit': `${profit.toFixed(2)}%`,
            'profit_usd': `$${Number(profitUSD.toFixed(2)).toLocaleString('en-US', {minimumFractionDigits: 2})}`,
            'profitGreaterThanZero': profitUSD >= 0,
            'details': crypto
        };
    }
}