export default class CoingeckoCrypto {
    constructor(id, ticker, name, image, price, marketCap, rank, fullyDilutedValuation, volume_24h, high_24h, low_24h, allTimeHigh, allTimeHighPercentage, allTimeHighDate, allTimeLow, allTimeLowPercentage, allTimeLowDate, circulatingSupply, totalSupply, maxSupply, market_cap_change_24h, market_cap_percent_change_24h, price_change_24h, price_percent_change_24h, price_percent_change_7d, price_percent_change_30d, price_percent_change_1y, lastUpdated, color) {
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
        this.color = color;
    }

    static fromJSON(json) {
        return new CoingeckoCrypto(
            parseInt(json['id']),
            json['symbol']?.toString()?.toUpperCase(),
            json['name']?.toString(),
            json['image']?.toString(),
            parseFloat(json['price']),
            parseFloat(json['marketCap']),
            parseInt(json['rank']),
            parseFloat(json['fullyDilutedValuation']),
            parseFloat(json['volume24h']),
            parseFloat(json['high24h']),
            parseFloat(json['low24h']),
            parseFloat(json['allTimeHigh']),
            parseFloat(json['allTimeHighPercentage']),
            new Date(json['allTimeHighDate']),
            parseFloat(json['allTimeLow']),
            parseFloat(json['allTimeLowPercentage']),
            new Date(json['allTimeLowDate']),
            parseInt(json['circulatingSupply']),
            parseInt(json['totalSupply']),
            parseInt(json['maxSupply']),
            parseFloat(json['marketCapChange24h']),
            parseFloat(json['marketCapPercentChange24h']),
            parseFloat(json['priceChange24h']),
            parseFloat(json['pricePercentChange24h']),
            parseFloat(json['pricePercentChange7d']),
            parseFloat(json['pricePercentChange30d']),
            parseFloat(json['pricePercentChange1y']),
            new Date(json['lastUpdated']),
            new Object(json['color'])
        );
    }

    toPercent(number, decimals = 2) {
        const numberDecimals = parseFloat(number).toFixed(decimals);
        const numberFractionDigits = parseFloat(numberDecimals).toLocaleString('en-US', { minimumFractionDigits: 2 });

        return `${numberFractionDigits}%`;
    }

    toDollar(number, decimals = 2) {
        let numberDecimals = parseFloat(number);

        if (numberDecimals < 1) return `$${numberDecimals.toFixed(6)}`;

        numberDecimals = numberDecimals.toFixed(decimals);
        const numberFractionDigits = parseFloat(numberDecimals).toLocaleString('en-US', { minimumFractionDigits: 2 });

        return `$${numberFractionDigits}`;
    }

    toFormat(number) {
        number = parseInt(number);

        if (number == 0) return '-';

        return number.toLocaleString('en-US');
    }

    toDate(date) {
        function daysDiff(first, second) {
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

    getRgbColor(alpha = 1) {
        const r = this.color.red;
        const g = this.color.green;
        const b = this.color.blue;

        return `rgba(${r},${g},${b},${alpha})`;
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