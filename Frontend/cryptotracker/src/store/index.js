import Vue from 'vue'
import Vuex from 'vuex'

import API from './../api/Api.js';

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        isLoggedIn: false,
        loginResult: null,
    },
    getters: {
        getLogin(state) {
            return state.loginResult;
        },
        isLoggedIn(state) {
            return state.isLoggedIn;
        },
    },
    mutations: {
        login(state, loginResult) {
            state.loginResult = loginResult;
            if (loginResult.success && loginResult.token != "") state.isLoggedIn = true;
        },
        setIsLoggedIn(state, value) {
            state.isLoggedIn = value;
        }
    },
    actions: {
        login(context, data) {
            API.login(data['email'], data['password']).then((loginResult) => {
                context.commit('login', loginResult);
            });
        }
    }
})