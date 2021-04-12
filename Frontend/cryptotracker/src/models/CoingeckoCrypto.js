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
            json['market_cap_percent_change_24h'],
            json['price_change_24h'],
            json['price_percent_change_24h'],
            json['price_percent_change_7d'],
            json['price_percent_change_30d'],
            json['price_percent_change_1y'],
            json['lastUpdated']
        );
    }

    toPercent(number, decimals = 2) {
        return `${Number.parseFloat(number).toFixed(decimals)}%`;
    }

    toDollar(number, decimals = 2) {
        if (number < 1) return `$${number.toFixed(6)}`;

        return `$${Number(Number.parseFloat(number).toFixed(decimals)).toLocaleString('en-US', {minimumFractionDigits: 2})}`;
    }

    toFormat(number) {
        if (number == 0) return '-';

        return number.toLocaleString('en-US');
    }

    toDate(date) {
        function daysDiff(first, second) {
            // Take the difference between the dates and divide by milliseconds per day.
            // Round to nearest whole number to deal with DST.
            return Math.round((second - first) / (1000 * 60 * 60 * 24));
        }

        function monthDiff(d1, d2) {
            var months;
            months = (d2.getFullYear() - d1.getFullYear()) * 12;
            months -= d1.getMonth();
            months += d2.getMonth();
            return months <= 0 ? 0 : months;
        }

        if (typeof date != 'object') {
            date = new Date(date);
        }

        const now = new Date();

        const formatter = new Intl.DateTimeFormat('en-US', { year: 'numeric', month: 'short', day: 'numeric' });

        const dateFormatted = formatter.format(date);

        let timeAgo = `(${daysDiff(date, now)} days ago)`;

        if (daysDiff(date, now) == 1) {
            timeAgo = `(1 day ago)`;
        }

        if (daysDiff(date, now) <= 0) {
            timeAgo = `less than 1 day ago)`;
        }

        if (monthDiff(date, now) > 0) {
            if (monthDiff(date, now) > 1) {
                timeAgo = `(${monthDiff(date, now)} months ago)`;
            } else {
                timeAgo = `(a month ago)`;
            }
        }

        if (now.getFullYear() - date.getFullYear() > 0) {
            if (now.getFullYear() - date.getFullYear() > 1) {
                timeAgo = `(${now.getFullYear() - date.getFullYear()} years ago)`;
            } else {
                timeAgo = `(a year ago)`;
            }
        }

        return `${dateFormatted} ${timeAgo}`;
    }

    getPriceDollar() {
        return this.toDollar(this.price);
    }

    getPriceChangeDollar() {
        return this.toDollar(this.price_change_24h);
    }

    getPriceChangePercentage() {
        return this.toPercent(this.price_percent_change_24h);
    }

    getLow24hDollar() {
        return this.toDollar(this.low_24h);
    }

    getHigh24hDollar() {
        return this.toDollar(this.high_24h);
    }

    getVolume24hDollar() {
        return this.toDollar(this.volume_24h);
    }

    getMarketCapDollar() {
        return this.toDollar(this.marketCap);
    }

    getMarketCapPercentage() {
        return this.toPercent(this.market_cap_percent_change_24h);
    }

    getPriceChange7dPercentage() {
        return this.toPercent(this.price_percent_change_7d);
    }

    getPriceChange30dPercentage() {
        return this.toPercent(this.price_percent_change_30d);
    }

    getPriceChange1yPercentage() {
        return this.toPercent(this.price_percent_change_1y);
    }

    getFullyDilutedMarketCapDollar() {
        if (this.fullyDilutedValuation == 0 || isNaN(this.fullyDilutedValuation)) {
            if (isNaN(this.totalSupply) || this.totalSupply == 0) {
                return '-'
            } else {
                return this.toDollar(this.totalSupply * this.price);
            }
        }

        return this.toDollar(this.fullyDilutedValuation);
    }

    getAllTimeHighDollar() {
        return this.toDollar(this.allTimeHigh);
    }

    getAllTimeHighPercentage() {
        return this.toPercent(this.allTimeHighPercentage);
    }

    getAllTimeHighDate() {
        return this.toDate(this.allTimeHighDate);
    }

    getAllTimeLowDollar() {
        return this.toDollar(this.allTimeLow);
    }

    getAllTimeLowPercentage() {
        return this.toPercent(this.allTimeLowPercentage);
    }

    getAllTimeLowDate() {
        return this.toDate(this.allTimeLowDate);
    }

    getCirculatingSupply() {
        return this.toFormat(this.circulatingSupply);
    }

    getTotalSupply() {
        return this.toFormat(this.totalSupply);
    }

    getMaxSupply() {
        if (isNaN(this.maxSupply) || this.maxSupply == 0) return this.getTotalSupply();

        return this.toFormat(this.maxSupply);
    }

    toOption() {
        return {
            "value": this.ticker,
            "label": `${this.name} (${this.ticker})`,
            "img": this.image
        };
    }
}