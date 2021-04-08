<style>
@import './style/style.css';
</style>

<template>
  <div>
  <!-- Logged out view -->
    <div v-if="!['Verify', 'Not Found'].includes($route.name)">
      <b-nav align="right" v-if="cookie == ''">
        <b-nav-item to="/login">Login</b-nav-item>
        <b-nav-item to="/register">Register</b-nav-item>
      </b-nav>
    <!-- Logged in view -->
      <b-nav align="right" v-if="cookie != ''">
        <b-nav-item to="/">Home</b-nav-item>
        <b-nav-item to="/history">History</b-nav-item>
        <b-nav-item v-on:click="logout">Logout</b-nav-item>
      </b-nav>
    </div>
    <router-view />
  </div>
</template>

<script>
import {eventBus} from '@/events/event.js';

export default {
  mounted() {
    this.cookie = this.getAccessToken();
  },
  created() {
    eventBus.$on('accessTokenSet', (token) => this.cookie = token);
  },
  data() {
    return {
      cookie: ''
    };
  },
  methods: {
    getAccessToken() {
        let cookie = this.$cookie.get('access_token');
        if (cookie == null) cookie = '';

        return cookie;
    },
    logout() {
      this.$cookie.delete('access_token');
      this.cookie = '';

      this.$router.push('/login');
    }
  }
}
</script>