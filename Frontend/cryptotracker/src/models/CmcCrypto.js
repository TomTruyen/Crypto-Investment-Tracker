export default class CmcCrypto {
    constructor(id, rank, name, ticker, tokenProvider, imageUri) {
        this.id = id;
        this.rank = rank;
        this.name = name;
        this.ticker = ticker;
        this.tokenProvider = tokenProvider;
        this.imageUri = imageUri;
    }

    static fromJSON(json) {
        return new CmcCrypto(
            json['id'],
            json['rank'],
            json['name'],
            json['ticker'],
            json['tokenProvider'],
            json['imageUri']
        );
    }

    toOption() {
        return {
            "value": this.ticker,
            "label": `${this.name} (${this.ticker})`,
            "img": this.imageUri
        };
    }
}