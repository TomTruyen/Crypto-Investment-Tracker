<template>
  <div>
  <!-- Logged out view -->
    <div v-if="!['Verify', 'Not Found'].includes($route.name)">
      <b-nav align="right" v-if="token == ''">
        <b-nav-item to="/login">Login</b-nav-item>
        <b-nav-item to="/register">Register</b-nav-item>
      </b-nav>
    <!-- Logged in view -->
      <b-nav align="right" v-if="token != ''">
        <b-nav-item to="/">Home</b-nav-item>
        <b-nav-item to="/history">History</b-nav-item>
        <b-nav-item v-on:click="logout">Logout</b-nav-item>
      </b-nav>
    </div>
    <router-view />
    <div class="footer">
      <span class="credits">Real-time data powered by <a href="https://www.coingecko.com/" target="_blank">Coingecko</a></span>
    </div>
  </div>
</template>

<script>
import {eventBus} from '@/events/event.js';

export default {
  mounted() {
    this.token = this.getAccessToken();
  },
  created() {
    eventBus.$on('accessTokenSet', (token) => this.token = token);
  },
  data() {
    return {
      token: ''
    };
  },
  methods: {
    getAccessToken() {
        let accessToken = this.$session.get('access_token');
        if (accessToken == null) accessToken = '';

        return accessToken;
    },
    logout() {
      this.$session.remove('access_token');
      this.token = '';

      this.$router.push('/login');
    }
  }
}
</script>