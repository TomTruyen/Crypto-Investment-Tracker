import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import VueCookie from 'vue-cookie';
import VueSession from 'vue-session';
import VueSelect from 'vue-select';
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';

import 'vue-select/dist/vue-select.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

import './style/style.css';

Vue.config.productionTip = false

Vue.use(VueCookie);
Vue.use(VueSession);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.component('v-select', VueSelect);

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!store.getters.isLoggedIn) {
            document.title = 'Login | CryptoTracker';
            next({ name: 'Login' })
        } else {
            document.title = to.meta.title;
            next();
        }
    } else {
        document.title = to.meta.title;
        next() // does not require auth, make sure to always call next()!
    }
})

new Vue({
    router,
    store,
    render: h => h(App),
    created() {
        this.$router.push('/')
    }
}).$mount('#app')