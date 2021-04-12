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
        coingeckoCryptos: [],
        portfolio: [],
        portfolioHistory: [],
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
        getCoingeckoCryptos(state) {
            return state.coingeckoCryptos;
        },
        getCoingeckoCryptosAsOptions(state) {
            let cryptos = [];

            for (let i = 0; i < state.coingeckoCryptos.length; i++) {
                cryptos.push(state.coingeckoCryptos[i].toOption());
            }

            return cryptos;
        },
        getPortfolio(state) {
            return state.portfolio;
        },
        getPortfolioOptions(state) {
            let cryptos = [];

            for (let i = 0; i < state.portfolio.length; i++) {
                const ticker = state.portfolio[i].ticker;

                if (state.coingeckoCryptos.length > 0) {
                    const crypto = state.coingeckoCryptos.find(c => c.ticker == ticker);

                    cryptos.push(state.portfolio[i].toOption(crypto));
                }
            }

            return cryptos;
        },
        getPortfolioHistoryOptions(state) {
            let cryptos = [];

            for (let i = 0; i < state.portfolioHistory.length; i++) {
                const ticker = state.portfolio[i].ticker;

                let crypto = null;
                if (state.coingeckoCryptos.length > 0) {
                    crypto = state.coingeckoCryptos.find(c => c.ticker == ticker);
                }

                cryptos.push(state.portfolioHistory[i].toHistoryOption(crypto));
            }

            return cryptos;
        },
        getPortfolioHistoryProfit(state) {
            let totalProfit = 0;

            for (let i = 0; i < state.portfolioHistory.length; i++) {
                const buyPrice = state.portfolioHistory[i].buyPrice;
                const sellPrice = state.portfolioHistory[i].sellPrice;
                const amount = state.portfolioHistory[i].sellAmount;

                totalProfit += state.portfolioHistory[i].calculateProfitUSD(amount, buyPrice, sellPrice);
            }

            return totalProfit;
        },
        getPortfolioValue(state) {
            let totalValue = 0;

            for (let i = 0; i < state.portfolio.length; i++) {
                const ticker = state.portfolio[i].ticker;

                if (state.coingeckoCryptos.length > 0) {
                    const crypto = state.coingeckoCryptos.find(c => c.ticker == ticker);

                    let price = 0;
                    if (typeof crypto !== 'undefined' && crypto !== null) {
                        price = crypto.price;
                    }

                    totalValue += state.portfolio[i].getValue(price);
                }
            }

            return totalValue.toFixed(2);
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
        setCoingeckoCryptos(state, cryptos) {
            state.coingeckoCryptos = cryptos;
        },
        setPortfolio(state, cryptos) {
            state.portfolio = cryptos;
        },
        setPortfolioHistory(state, cryptos) {
            state.portfolioHistory = cryptos;
        },
        buyCrypto(state, crypto) {
            state.portfolio.push(crypto);
        },
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
        setCoingeckoCryptos(context, token) {
            return API.getCryptoList(token).then(async(cryptos) => {
                context.commit('setCoingeckoCryptos', cryptos);
                return await API.getCryptos(token).then((cryptos) => {
                    context.commit('setPortfolio', cryptos);
                    return true;
                });
            });
        },
        setPortfolioHistory(context, token) {
            API.getPortfolioHistory(token).then((cryptos) => {
                context.commit('setPortfolioHistory', cryptos);
            })
        },
        buyCrypto(context, payload) {
            API.buyCrypto(payload).then((isSuccess) => {
                if (isSuccess) {
                    API.getCryptos(payload.token).then((cryptos) => {
                        context.commit('setPortfolio', cryptos);
                    });
                }
            });
        },
        sellCrypto(context, payload) {
            API.sellCrypto(payload).then((isSuccess) => {
                if (isSuccess) {
                    API.getCryptos(payload.token).then((cryptos) => {
                        context.commit('setPortfolio', cryptos);
                    });
                }
            });
        }
    }
})