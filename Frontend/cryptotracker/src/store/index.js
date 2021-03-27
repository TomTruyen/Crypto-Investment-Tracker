import Vue from 'vue'
import Vuex from 'vuex'

import API from './../api/Api.js';

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        isLoggedIn: false,
        loginResult: null,
        registerResult: null,
        verifyResult: null,
        resendVerificationResult: null,
        cmcPrices: [],
        cmcCryptos: [],
        portfolio: [],
    },
    getters: {
        isLoggedIn(state) {
            return state.isLoggedIn;
        },
        getLogin(state) {
            return state.loginResult;
        },
        getRegister(state) {
            return state.registerResult;
        },
        getVerify(state) {
            return state.verifyResult;
        },
        getResendVerificationResult(state) {
            return state.resendVerificationResult;
        },
        getCmcCryptos(state) {
            return state.cmcCryptos;
        },
        getCmcCryptosAsOptions(state) {
            let cryptos = [];

            for (let i = 0; i < state.cmcCryptos.length; i++) {
                cryptos.push(state.cmcCryptos[i].toOption());
            }

            return cryptos;
        },
        getPortfolio(state) {
            return state.portfolio;
        },
        getPortfolioOptions(state) {
            let cryptos = [];

            for (let i = 0; i < state.portfolio.length; i++) {
                let ticker = state.portfolio[i].ticker;
                const crypto = state.cmcPrices.find(c => c.ticker == ticker);

                cryptos.push(state.portfolio[i].toOption(crypto.price, crypto.percent_change_24h));
            }

            console.log("RETURNING OPTIONS");
            console.log(cryptos);

            return cryptos;
        }
    },
    mutations: {
        setIsLoggedIn(state, value) {
            state.isLoggedIn = value;
        },
        login(state, loginResult) {
            state.loginResult = loginResult;
            if (loginResult.success && loginResult.token != "") state.isLoggedIn = true;
        },
        register(state, registerResult) {
            state.registerResult = registerResult;
        },
        verify(state, verifyResult) {
            state.verifyResult = verifyResult;
        },
        resendVerification(state, verifyResult) {
            state.resendVerificationResult = verifyResult;
        },
        setCmcPrices(state, prices) {
            state.cmcPrices = prices;
        },
        setCmcCryptos(state, cryptos) {
            state.cmcCryptos = cryptos;
        },
        setPortfolio(state, cryptos) {
            state.portfolio = cryptos;
        }
    },
    actions: {
        login(context, data) {
            API.login(data['email'], data['password']).then((loginResult) => {
                context.commit('login', loginResult);
            });
        },
        register(context, data) {
            API.register(data['email'], data['password']).then((registerResult) => {
                context.commit('register', registerResult);
            });
        },
        verify(context, email) {
            API.verify(email).then((verifyResult) => {
                context.commit('verify', verifyResult);
            });
        },
        resendVerification(context, email) {
            API.resendVerification(email).then((verifyResult) => {
                context.commit('resendVerification', verifyResult);
            });
        },
        setCmcPrices(context, token) {
            API.getCryptoPrices(token).then((prices) => {
                context.commit('setCmcPrices', prices);
            });
        },
        setCmcCryptos(context, token) {
            API.getCryptoList(token).then((cryptos) => {
                context.commit('setCmcCryptos', cryptos);
            });
        },
        setPortfolio(context, token) {
            API.getCryptos(token).then((cryptos) => {
                console.log("SETTING PORTFOLIO");
                console.log(cryptos);

                context.commit('setPortfolio', cryptos);
            });
        }
    }
})