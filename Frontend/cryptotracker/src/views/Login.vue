<template>
  <div class="login">
    <b-form v-on:submit.prevent="login">
      <h3>Login</h3>
      <hr />
      <b-alert variant="danger" show v-if="loginResult != null && loginResult.message != ''">{{ loginResult.message }}</b-alert>
      <b-alert variant="danger" show v-if="verifyResult != null && !verifyResult.success && verifyResult.message != ''">{{ verifyResult.message }}</b-alert>
      <b-alert variant="success" show v-if="verifyResult != null && verifyResult.success && verifyResult.message != ''">{{ verifyResult.message }}</b-alert>
      <b-link v-if="verifyResult == null && loginResult != null && loginResult.message.toLowerCase().includes('mail not verified')" v-on:click="resendVerification">
        {{getVerificationMessage}}
      <br/><br />
      </b-link>
      <b-alert variant="danger" show v-if="loginResult == null && getErrorMessage != null">{{ getErrorMessage }}</b-alert>
      <b-form-group label="Email address:" label-for="email">
        <b-form-input id="email" type="text" placeholder="Email" v-model="email"></b-form-input>
      </b-form-group>
      <b-form-group label="Password:" label-for="password">
        <b-form-input id="password" type="password" placeholder="Password" v-model="password"></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Login</b-button>
    </b-form>
  </div>
</template>

<script>
  import {eventBus} from '@/events/event.js';
  import Utils from '@/utils/Utils.js';
  import API from './../api/Api.js';

  export default {
    mounted() {
     this.resetFields();
     this.checkLoggedIn();
    },
    data: function () {
      return{
        email: '',
        password: '',
        errorMessage: null,
        verificationMessage: 'Resend verification email',
      };
    },
    watch: {
      loginResult(newResult) {
        if(newResult.success && newResult.token != "") {
          this.$cookie.set('access_token', newResult.token);

          eventBus.$emit('accessTokenSet', newResult.token);

          this.$router.push('/');
        }
      },
    },
    computed: {
      loginResult() {
        return this.$store.getters.getLogin;
      },
      verifyResult() {
        return this.$store.getters.getResendVerificationResult;
      },
      getErrorMessage() {
        return this.$data.errorMessage;
      },
      getVerificationMessage() {
        return this.$data.verificationMessage;
      }
    },
    methods: {
      login() {
        let isValidEmail = Utils.isValidEmail(this.$data.email);
        let isNotEmptyPassword = this.$data.password.length > 0;

        if(!isValidEmail) {
          this.errorMessage = "Invalid email";
        } else if (!isNotEmptyPassword) {
          this.errorMessage = "Password can't be empty";
        }

        if(isValidEmail && isNotEmptyPassword) {
          this.$store.dispatch('login', {
            'email': this.$data.email,
            'password': this.$data.password
          });
        }
      },
      resendVerification() {
        if(!this.$data.sendingVerification) {
          this.$data.verificationMessage = "Sending email...";

          this.$store.dispatch('resendVerification', this.$data.email);
        }
      },
      checkLoggedIn() {
        let cookie = this.$cookie.get('access_token');
        if (cookie == null) cookie = '';

        this.$store.commit('setIsLoggedIn', true);

        if(cookie != "") this.$router.push('/');
      },
      resetFields() {
        this.$data.email = "";
        this.$data.password = "";
        this.$data.errorMessage = null;
        this.$data.verificationMessage = 'Resend verification email';
      }
    }
  }
</script>