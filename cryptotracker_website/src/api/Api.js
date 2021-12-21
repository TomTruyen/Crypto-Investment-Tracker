import axios from 'axios';
import CoingeckoCrypto from '../models/CoingeckoCrypto';
import Crypto from '../models/Crypto';
import LoginResult from '../models/LoginResult';
import RegisterResult from '../models/RegisterResult';
import VerifyResult from '../models/VerifyResult';

const baseURL = process.env.VUE_APP_BASE_URL;

export default class API {
    static async login(email, password) {
        let options = {
            method: 'POST',
            baseURL: baseURL,
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
            if (res.data) {
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
            baseURL: baseURL,
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
            if (res.data) {
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
            baseURL: baseURL,
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
            if (res.data) {
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
            baseURL: baseURL,
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
            if (res.data) {
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
            baseURL: baseURL,
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
            console.log(res);

            if (res.data && res.data.success) {
                const _cryptos = res.data.crypto;

                for (let i = 0; i < _cryptos.length; i++) {
                    cryptos.push(Crypto.fromJSON(_cryptos[i]));
                }
            }
        }).catch((err) => { console.log(err); console.log(err.response.data) });

        // console.clear();

        return cryptos;
    }

    static async getPortfolioHistory(token) {
        const bearer = `Bearer ${token}`;

        let options = {
            method: 'GET',
            baseURL: baseURL,
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
            if (res.data && res.data.success) {
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
            baseURL: baseURL,
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
            if (res.data && res.data.success) {
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

        let options = {
            method: 'POST',
            baseURL: baseURL,
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

        let result = null;

        await axios.request(options).then((res) => {
            result = res.data;
        }).catch((err) => { result = err.response.data; });

        // console.clear();

        return result;
    }

    static async sellCrypto(payload) {
        const bearer = `Bearer ${payload.token}`;

        let options = {
            method: 'POST',
            baseURL: baseURL,
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
                'isGas': payload.isGas,
            },
        };

        let result = null;

        await axios.request(options).then((res) => {
            result = res.data;
        }).catch((err) => { result = err.response.data; });

        // console.clear();

        return result;
    }

    static async resetPassword(email) {
        email = email.toLowerCase().trim();

        let result = null;

        let options = {
            method: 'POST',
            baseURL: baseURL,
            url: '/resetpassword/',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
            },
            data: {
                'email': email,
            },
        };

        await axios.request(options).then((res) => {
            result = res.data;
        }).catch((err) => { result = err.response.data; });

        return result;
    }

    static async resetPasswordConfirm(email, password) {
        email = email.toLowerCase().trim();

        let result = null;

        let options = {
            method: 'POST',
            baseURL: baseURL,
            url: '/resetpassword/confirm',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
            },
            data: {
                'email': email,
                'password': password,
            },
        };

        await axios.request(options).then((res) => {
            result = res.data;
        }).catch((err) => { result = err.response.data; });

        return result;
    }

    static async setPriceAlert(token, id, alert) {
        const bearer = `Bearer ${token}`;

        let options = {
            method: 'POST',
            baseURL: baseURL,
            url: '/cryptocurrencies/alert/set',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': bearer
            },
            data: {
                'id': id,
                'alert': alert,
            },
        };

        let result = null;

        await axios.request(options).then((res) => { result = res.data; }).catch((err) => { result = err.response.data; });

        return result;
    }

    static async deletePriceAlert(token, id) {
        const bearer = `Bearer ${token}`;

        let options = {
            method: 'POST',
            baseURL: baseURL,
            url: '/cryptocurrencies/alert/delete',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': bearer
            },
            data: {
                'id': id,
            },
        };

        let result = null;

        await axios.request(options).then((res) => { result = res.data; }).catch((err) => { result = err.response.data; });

        return result;
    }
}