import axios from 'axios';
import CmcCrypto from '../models/CmcCrypto';
import LoginResult from '../models/LoginResult';
import RegisterResult from '../models/RegisterResult';
import VerifyResult from '../models/VerifyResult';

export default class API {
    static async login(email, password) {
        let options = {
            method: 'POST',
            baseURL: 'http://localhost:8888/',
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

        console.clear();

        return result;
    }

    static async register(email, password) {
        let options = {
            method: 'POST',
            baseURL: 'http://localhost:8888/',
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

        console.clear();

        return result;
    }

    static async verify(email) {
        let options = {
            method: 'POST',
            baseURL: 'http://localhost:8888/',
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

        console.clear();

        return result;
    }

    static async resendVerification(email) {
        let options = {
            method: 'POST',
            baseURL: 'http://localhost:8888/',
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

        console.clear();

        return result;
    }

    static async getCryptoList(token) {
        const bearer = `Bearer ${token}`;

        let options = {
            method: 'GET',
            baseURL: 'http://localhost:8888/',
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
            console.log(res);

            if (res.data != null && res.data.success) {
                const _cryptos = res.data.crypto;

                console.log(_cryptos);

                for (let i = 0; i < _cryptos.length; i++) {
                    cryptos.push(CmcCrypto.fromJSON(_cryptos[i]));
                }
            }
        }).catch((err) => { console.log(err.response.data) });

        // console.clear();

        console.log(cryptos);

        return cryptos;
    }
}