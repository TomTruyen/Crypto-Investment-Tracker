<style>
@import './style/style.css';
</style>

<template>
  <div>
  <!-- Logged out view -->
    <b-nav align="right" v-if="cookie == ''">
      <b-nav-item to="/login">Login</b-nav-item>
      <b-nav-item to="/register">Register</b-nav-item>
    </b-nav>
  <!-- Logged in view -->
    <b-nav align="right" v-if="cookie != ''">
      <b-nav-item to="/">Home</b-nav-item>
      <b-nav-item to="/" v-on:click="logout">Logout</b-nav-item>
    </b-nav>
    <router-view />
  </div>
</template>

<script>
import {eventBus} from '@/events/event.js';

export default {
  mounted: function () {
    this.cookie = this.getAccessToken();
  },
  created() {
    eventBus.$on('accessTokenSet', (token) => this.cookie = token);
  },
  data: function() {
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
    }
  }
}
</script>