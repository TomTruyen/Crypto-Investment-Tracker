import axios from 'axios';
import CoingeckoCrypto from '../models/CoingeckoCrypto';
import Crypto from '../models/Crypto';
import LoginResult from '../models/LoginResult';
import RegisterResult from '../models/RegisterResult';
import VerifyResult from '../models/VerifyResult';

export default class API {
    static async login(email, password) {
        let options = {
            method: 'POST',
            baseURL: 'http://192.168.0.150:8888/',
            url: '/login',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*"
            },
            data: {
                'email': email,
                'password': password
            }
        };

        let result = new LoginResult();
        await axios.request(options).then((res) => {
            if (res.data != null) {
                result = LoginResult.fromJSON(res.data);
            }
        }).catch((err) => {
            result = LoginResult.fromJSON(err.response.data);
        });

        // console.clear();

        return result;
    }

    static async register(email, password) {
        let options = {
            method: 'POST',
            baseURL: 'http://192.168.0.150:8888/',
            url: '/register',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*"
            },
            data: {
                'email': email,
                'password': password
            }
        };

        let result = new RegisterResult();
        await axios.request(options).then((res) => {
            if (res.data != null) {
                result = RegisterResult.fromJSON(res.data);
            }
        }).catch((err) => {
            result = RegisterResult.fromJSON(err.response.data);
        });

        // console.clear();

        return result;
    }

    static async verify(email) {
        let options = {
            method: 'POST',
            baseURL: 'http://192.168.0.150:8888/',
            url: '/verify',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*"
            },
            data: {
                'email': email,
            }
        };

        let result = new VerifyResult();
        await axios.request(options).then((res) => {
            if (res.data != null) {
                result = VerifyResult.fromJSON(res.data);
            }
        }).catch((err) => {
            result = VerifyResult.fromJSON(err.response.data);
        });

        // console.clear();

        return result;
    }

    static async resendVerification(email) {
        let options = {
            method: 'POST',
            baseURL: 'http://192.168.0.150:8888/',
            url: '/verify/resend',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*"
            },
            data: {
                'email': email,
            }
        };

        let result = new VerifyResult();
        await axios.request(options).then((res) => {
            if (res.data != null) {
                result = VerifyResult.fromJSON(res.data);
            }
        }).catch((err) => {
            result = VerifyResult.fromJSON(err.response.data);
        });

        // console.clear();

        return result;
    }

    static async getCryptos(token) {
        const bearer = `Bearer ${token}`;

        let options = {
            method: 'GET',
            baseURL: 'http://192.168.0.150:8888/',
            url: '/cryptocurrencies/portfolio',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': bearer
            },
            data: {},
        };

        let cryptos = [];
        await axios.request(options).then((res) => {
            if (res.data != null && res.data.success) {
                const _cryptos = res.data.crypto;

                for (let i = 0; i < _cryptos.length; i++) {
                    cryptos.push(Crypto.fromJSON(_cryptos[i]));
                }
            }
        }).catch((err) => { console.log(err.response.data) });

        // console.clear();

        return cryptos;
    }

    static async getPortfolioHistory(token) {
        const bearer = `Bearer ${token}`;

        let options = {
            method: 'GET',
            baseURL: 'http://192.168.0.150:8888/',
            url: '/cryptocurrencies/portfolio/history',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': bearer
            },
            data: {},
        };

        let cryptos = [];
        await axios.request(options).then((res) => {
            if (res.data != null && res.data.success) {
                const _cryptos = res.data.crypto;

                for (let i = 0; i < _cryptos.length; i++) {
                    cryptos.push(Crypto.fromJSON(_cryptos[i]));
                }
            }
        }).catch((err) => { console.log(err.response.data) });

        // console.clear();

        return cryptos;
    }

    static async getCryptoList(token) {
        const bearer = `Bearer ${token}`;

        let options = {
            method: 'GET',
            baseURL: 'http://192.168.0.150:8888/',
            url: '/cryptocurrencies/list/',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': bearer
            },
            data: {},
        };

        let cryptos = [];
        await axios.request(options).then((res) => {
            if (res.data != null && res.data.success) {
                const _cryptos = res.data.crypto;

                for (let i = 0; i < _cryptos.length; i++) {
                    cryptos.push(CoingeckoCrypto.fromJSON(_cryptos[i]));
                }
            }
        }).catch((err) => { console.log(err.response.data) });

        // console.clear();

        return cryptos;
    }

    static async buyCrypto(payload) {
        const bearer = `Bearer ${payload.token}`;

        let success = false;

        let options = {
            method: 'POST',
            baseURL: 'http://192.168.0.150:8888/',
            url: '/cryptocurrencies/buy/',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': bearer
            },
            data: {
                'ticker': payload.crypto,
                'amount': payload.amount,
                'price': payload.price,
            },
        };

        await axios.request(options).then((res) => {
            if (res.data != null && res.data.success) success = true;
        }).catch((err) => { console.log(err.response.data) });

        // console.clear();

        return success;
    }

    static async sellCrypto(payload) {
        const bearer = `Bearer ${payload.token}`;

        let success = false;

        let options = {
            method: 'POST',
            baseURL: 'http://192.168.0.150:8888/',
            url: '/cryptocurrencies/sell/',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': bearer
            },
            data: {
                'id': payload.id,
                'ticker': payload.crypto,
                'amount': payload.amount,
                'price': payload.price,
            },
        };

        await axios.request(options).then((res) => {
            if (res.data != null && res.data.success) success = true;
        }).catch((err) => { console.log(err.response.data) });

        // console.clear();

        return success;
    }
}