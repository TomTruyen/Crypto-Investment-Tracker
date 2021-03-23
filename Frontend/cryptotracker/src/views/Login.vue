<template>
  <div class="login">
    <b-form>
      <h3>Login</h3>
      <hr />
      <b-alert variant="danger" show v-if="loginResult != null && loginResult.message != ''">{{ loginResult.message }}</b-alert>
      <b-form-group label="Email address:" label-for="email">
        <b-form-input id="email" type="text" placeholder="Email" v-model="email"></b-form-input>
      </b-form-group>
      <b-form-group label="Password:" label-for="password">
        <b-form-input id="password" type="password" placeholder="Password" v-model="password"></b-form-input>
      </b-form-group>
      <b-button type="button" variant="primary" v-on:click="login">Login</b-button>
    </b-form>
  </div>
</template>

<script>
  import {eventBus} from '@/events/event.js';

  export default {
    mounted() {
     this.checkLoggedIn();
    },
    data: function () {
      return{
        email: '',
        password: '',
      };
    },
    watch: {
      loginResult(newResult) {
        if(newResult.success && newResult.token != "") {
          this.$cookie.set('access_token', newResult.token, {expires: 7});

          eventBus.$emit('accessTokenSet', newResult.token);

          this.$router.push('/');
        }
      }
    },
    computed: {
      loginResult() {
        return this.$store.getters.getLogin;
      }
    },
    methods: {
      login() {
        this.$store.dispatch('login', {
          'email': this.$data.email,
          'password': this.$data.password
        });
      },
      checkLoggedIn() {
        let cookie = this.$cookie.get('access_token');
        if (cookie == null) cookie = '';

        this.$store.commit('setIsLoggedIn', true);

        if(cookie != "") this.$router.push('/');
      },
    }
  }
</script>