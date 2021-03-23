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
            requiresAuth: true
        },
        component: Home
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/verify',
        name: 'Verify',
        component: Verify
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router