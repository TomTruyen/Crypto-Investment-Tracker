import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Verify from '../views/Verify.vue'

Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'Home',
        meta: {
            requiresAuth: true,
            title: 'Home | CryptoTracker'
        },
        component: Home
    },
    {
        path: '/login',
        name: 'Login',
        meta: {
            title: 'Login | CryptoTracker'
        },
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        meta: {
            title: 'Register | CryptoTracker'
        },
        component: Register
    },
    {
        path: '/verify',
        name: 'Verify',
        meta: {
            title: 'Verify | CryptoTracker'
        },
        component: Verify
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router