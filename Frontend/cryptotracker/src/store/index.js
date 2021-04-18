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
        search: '',
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
                const name = state.portfolio[i].name;

                if (state.search == '' || name.toLowerCase().includes(state.search) || ticker.toLowerCase().includes(state.search)) {
                    if (state.coingeckoCryptos.length > 0) {
                        const crypto = state.coingeckoCryptos.find(c => c.ticker == ticker);

                        if (crypto != undefined) {
                            cryptos.push(state.portfolio[i].toOption(crypto));
                        }
                    }
                }
            }

            return cryptos;
        },
        getPortfolioHistoryOptions(state) {
            let cryptos = [];

            for (let i = 0; i < state.portfolioHistory.length; i++) {
                const ticker = state.portfolioHistory[i].ticker;
                const name = state.portfolioHistory[i].name;

                if (state.search == '' || name.toLowerCase().includes(state.search) || ticker.toLowerCase().includes(state.search)) {
                    let crypto = null;
                    if (state.coingeckoCryptos.length > 0) {
                        crypto = state.coingeckoCryptos.find(c => c.ticker == ticker);
                    }

                    cryptos.push(state.portfolioHistory[i].toHistoryOption(crypto));
                }
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

            return Number(totalProfit.toFixed(2)).toLocaleString('en-US', { minimumFractionDigits: 2 });
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

            return Number(totalValue.toFixed(2)).toLocaleString('en-US', { minimumFractionDigits: 2 });
        },
        getPortfolioAssets(state) {
            if (state.portfolio == null) return 0;

            return state.portfolio.length;
        },
        getPortfolioChartData(state) {
            let labels = [];
            const borderWidth = 1;
            let borderColor = [];
            let backgroundColor = [];
            let data = [];

            for (let i = 0; i < state.portfolio.length; i++) {
                const ticker = state.portfolio[i].ticker;

                if (state.coingeckoCryptos.length > 0) {
                    const crypto = state.coingeckoCryptos.find(c => c.ticker == ticker);

                    if (crypto != undefined) {
                        const value = Number(state.portfolio[i].getValue(crypto.price).toFixed(2));

                        const label = `${crypto.name} (${crypto.ticker})`;

                        if (labels.indexOf(label) > -1) {
                            const index = labels.indexOf(label);

                            data[index] += value;
                        } else {
                            labels.push(label);

                            borderColor.push(crypto.getRgbColor(1));
                            backgroundColor.push(crypto.getRgbColor(0.5));

                            data.push(value);
                        }
                    }
                }
            }

            let chartData = {
                labels: labels,
                datasets: [{
                    borderWidth: borderWidth,
                    borderColor: borderColor,
                    backgroundColor: backgroundColor,
                    data: data
                }]
            };

            if (data.length <= 0) {
                chartData = {
                    labels: [''],
                    datasets: [{
                        borderWidth: 1,
                        borderColor: 'rgba(50, 50, 50, 1)',
                        backgroundColor: 'rgba(50, 50, 50, 0.5)',
                        data: [-1]
                    }]
                }
            }

            return chartData;
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
            // Sort by Value;
            cryptos = cryptos.sort((a, b) => {
                let aPrice = state.coingeckoCryptos.find(c => c.ticker == a.ticker);
                let bPrice = state.coingeckoCryptos.find(c => c.ticker == b.ticker);

                if (aPrice != undefined && bPrice != undefined) {
                    aPrice = aPrice.price;
                    bPrice = bPrice.price;

                    const aValue = a.getValue(aPrice);
                    const bValue = b.getValue(bPrice);

                    if (aValue < bValue) return -1;
                    if (aValue > bValue) return 1;
                }

                return 0;
            }).reverse();

            state.portfolio = cryptos;
        },
        setPortfolioHistory(state, cryptos) {
            state.portfolioHistory = cryptos;
        },
        buyCrypto(state, crypto) {
            state.portfolio.push(crypto);
        },
        updateSearch(state, search) {
            state.search = search.toLowerCase();
        },
        clearSearch(state) {
            state.search = '';
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
        },
        resetPassword(context, email) {
            return API.resetPassword(email);
        },
        resetPasswordConfirm(context, payload) {
            const email = payload['email'];
            const password = payload['password'];

            return API.resetPasswordConfirm(email, password);
        }
    }
})