import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import VueCookie from 'vue-cookie';
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

Vue.config.productionTip = false

Vue.use(VueCookie);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        if (!store.getters.isLoggedIn) {
            next({ name: 'Login' })
        } else {
            next();
        }
    } else {
        next() // does not require auth, make sure to always call next()!
    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')