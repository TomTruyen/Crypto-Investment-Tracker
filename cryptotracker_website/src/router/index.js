import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import History from '../views/History.vue';
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Verify from '../views/Verify.vue'
import ResetPassword from '../views/ResetPassword.vue';
import NotFound from '../views/NotFound.vue';

Vue.use(VueRouter)

const routes = [{
        path: '/',
        alias: '/home',
        name: 'Home',
        meta: {
            requiresAuth: true,
            title: 'Home | CryptoTracker'
        },
        component: Home
    },
    {
        path: '/history',
        name: 'History',
        meta: {
            requiresAuth: true,
            title: 'History | CryptoTracker'
        },
        component: History
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
        path: '/verify/:email',
        name: 'Verify',
        meta: {
            title: 'Verify | CryptoTracker'
        },
        component: Verify
    },
    {
        path: '/resetpassword',
        name: 'Reset Password',
        meta: {
            title: 'Reset Password  | CryptoTracker',
        },
        component: ResetPassword
    },
    {
        path: '/resetpassword/:email',
        name: 'Reset Password Confirm',
        meta: {
            title: 'Reset Password  | CryptoTracker',
        },
        component: ResetPassword
    },
    {
        path: '*',
        name: 'Not Found',
        meta: {
            title: 'Not Found | CryptoTracker'
        },
        component: NotFound
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router