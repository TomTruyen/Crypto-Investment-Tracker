import Utils from '@/utils/Utils.js';

export default class Crypto {
    constructor(id, name, ticker, buyAmount, buyPrice, buyDate, sellAmount, sellPrice, sellDate, priceAlert, isGas) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.buyAmount = buyAmount;
        this.buyPrice = buyPrice;
        this.buyDate = buyDate;
        this.sellAmount = sellAmount;
        this.sellPrice = sellPrice;
        this.sellDate = sellDate;
        if (priceAlert == undefined) priceAlert = 0;
        this.priceAlert = priceAlert;
        this.isGas = isGas;
    }

    static fromJSON(json) {
        return new Crypto(
            parseInt(json['id']),
            json['name']?.toString(),
            json['ticker']?.toString()?.toUpperCase(),
            parseFloat(json['buyAmount']),
            parseFloat(json['buyPrice']),
            Utils.toFormatDate(new Date(json['buyDate'])),
            parseFloat(json['sellAmount']),
            parseFloat(json['sellPrice']),
            Utils.toFormatDate(new Date(json['sellDate'])),
            parseFloat(json['priceAlert']),
            json['gas']
        );
    }

    calculateProfit(price) {
        price = parseFloat(price);

        let increase = price - this.buyPrice;
        let profit = (increase / this.buyPrice * 100);

        return parseFloat(profit.toFixed(2));
    }

    calculateProfitUSD(amount, buyPrice, sellPrice) {
        amount = parseFloat(amount);
        buyPrice = parseFloat(buyPrice);
        sellPrice = parseFloat(sellPrice);

        let profit = (amount * sellPrice) - (amount * buyPrice);
        profit = parseFloat(profit.toFixed(2));

        return profit;
    }

    getBalance() {
        if (isNaN(this.sellAmount)) return this.buyAmount;

        return this.buyAmount - this.sellAmount;
    }

    getValue(price) {
        price = parseFloat(price);

        const value = parseFloat(this.getBalance() * price);

        return value;
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
            "ticker": this.ticker,
            "image": crypto.image,
            "date": this.buyDate,
            "price": `$${Utils.numberWithCommas(this.buyPrice, this.buyPrice > 1 ? 2 : 6, true)}`,
            "change_24h": `${Utils.numberWithCommas(change_24h, 2, true)}%`,
            "balance": Utils.numberWithCommas(balance, 6),
            "balanceValue": balance,
            "value": `$${Utils.numberWithCommas(value, 2, true)}`,
            "profit": `${Utils.numberWithCommas(profit, 2, true)}% ($${Utils.numberWithCommas(profitUSD, 2, true)})`,
            "profitGreaterThanZero": profitUSD >= 0,
        };
    }

    toHistoryOption(crypto) {
        let profit = this.calculateProfit(this.sellPrice);
        let profitUSD = this.calculateProfitUSD(this.sellAmount, this.buyPrice, this.sellPrice);

        return {
            'date': this.sellDate,
            'name': `${this.name} (${this.ticker})`,
            'buy_price': `$${Utils.numberWithCommas(this.buyPrice, this.buyPrice > 1 ? 2 : 6, true)}`,
            'sell_amount': Utils.numberWithCommas(this.sellAmount, 6),
            'sell_price': this.isGas ? 'GAS FEE' : `$${Utils.numberWithCommas(this.sellPrice, this.sellPrice > 1 ? 2 : 6, true)}`,
            'profit': `${Utils.numberWithCommas(profit, 2, true)}%`,
            'profit_usd': `$${Utils.numberWithCommas(profitUSD, 2, true)}`,
            'profitGreaterThanZero': profitUSD >= 0,
            'details': crypto
        };
    }
}