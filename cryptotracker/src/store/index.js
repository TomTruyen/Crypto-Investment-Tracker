import Vue from 'vue'
import Vuex from 'vuex'

import API from './../api/Api.js';

import Utils from './../utils/Utils.js';

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
        showDetails: {}
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

            let cryptoInstances = {};
            for (let i = 0; i < state.portfolio.length; i++) {
                const ticker = state.portfolio[i].ticker;


                if (cryptoInstances[ticker] == undefined) {
                    // Not found yet, add the crypto with empty array!
                    cryptoInstances[ticker] = [];
                }

                cryptoInstances[ticker].push({
                    'portfolioCrypto': state.portfolio[i],
                });
            }

            for (let ticker in cryptoInstances) {
                let totalAmount = 0;
                let averagePrice = 0;

                const crypto = cryptoInstances[ticker];
                for (let i = 0; i < crypto.length; i++) {
                    const portfolioCrypto = crypto[i].portfolioCrypto;

                    const price = portfolioCrypto.buyPrice;
                    const amount = portfolioCrypto.getBalance();

                    averagePrice += (price * amount);
                    totalAmount += amount;
                }

                averagePrice /= totalAmount;

                if (state.coingeckoCryptos.length > 0) {
                    const coingeckoCrypto = state.coingeckoCryptos.find(c => c.ticker == ticker);

                    if (coingeckoCrypto != undefined) {
                        const name = coingeckoCrypto.name;
                        const ticker = coingeckoCrypto.ticker;

                        if (state.search == '' || name.toLowerCase().includes(state.search) || ticker.toLowerCase().includes(state.search)) {
                            const image = coingeckoCrypto.image;

                            const avgPrice = Utils.numberWithCommas(averagePrice, 2, true);
                            const price = Utils.numberWithCommas(coingeckoCrypto.price, 2, true);
                            const price_percent_change_24h = coingeckoCrypto.price_percent_change_24h;
                            const balance = Utils.numberWithCommas(totalAmount, 6);

                            let value = totalAmount * coingeckoCrypto.price;
                            value = Utils.numberWithCommas(value, 2, true, true);

                            let profit = (coingeckoCrypto.price - averagePrice) / averagePrice * 100;
                            profit = Utils.numberWithCommas(profit, 2, true);

                            let profitUSD = (totalAmount * coingeckoCrypto.price) - (totalAmount * averagePrice);
                            profitUSD = Utils.numberWithCommas(profitUSD, 2, true, true);

                            let foundInstances = state.portfolio.filter(p => p.ticker == ticker);
                            let instances = [];
                            if (foundInstances.length > 0) {
                                for (let i = 0; i < foundInstances.length; i++) {
                                    instances.push(foundInstances[i].toOption(coingeckoCrypto));
                                }
                            }

                            // Show details logic
                            let showDetails = false;
                            if (Object.prototype.hasOwnProperty.call(state.showDetails, ticker) && state.showDetails[ticker]) {
                                showDetails = true;
                            }

                            cryptos.push({
                                'name': name,
                                'ticker': ticker,
                                'image': image,
                                'avg_price': `$${avgPrice}`,
                                'price': `$${price}`,
                                '24h': price_percent_change_24h,
                                'balance': balance,
                                'value': `$${value}`,
                                'profit': `${profit}% ($${profitUSD})`,
                                'profitGreaterThanZero': profit >= 0,
                                'details': coingeckoCrypto,
                                'instances': instances,
                                '_showDetails': showDetails,
                            });
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

                        if (crypto != undefined) {
                            cryptos.push(state.portfolioHistory[i].toHistoryOption(crypto));
                        }
                    }

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

            let uniqueAssets = [];

            for (let i = 0; i < state.portfolio.length; i++) {
                const ticker = state.portfolio[i].ticker;

                if (uniqueAssets.indexOf(ticker) === -1) {
                    uniqueAssets.push(ticker);
                }
            }

            return uniqueAssets.length;
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
        },
        toggleShowDetails(state, ticker) {
            if (Object.prototype.hasOwnProperty.call(state.showDetails, ticker)) {
                state.showDetails[ticker] = !state.showDetails[ticker];
            } else {
                state.showDetails[ticker] = true;
            }

            // Force portfolio to update, otherwise the computed would not update the Portfolio...
            const backupPortfolio = state.portfolio;
            state.portfolio = [];
            state.portfolio = backupPortfolio;
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
            return API.getCryptoList(token).then(async (cryptos) => {
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