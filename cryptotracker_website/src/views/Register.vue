<template>
  <div class="register">
    <b-form v-on:submit.prevent="register">
      <h3>Register</h3>
      <br />
      <b-alert variant="success" show v-if="registerResult != null && registerResult.success && registerResult.message != ''">{{registerResult.message}}</b-alert>
      <b-alert variant="danger" show v-if="registerResult != null && !registerResult.success && registerResult.message != ''">{{ registerResult.message }}</b-alert>
      <b-alert variant="danger" show v-if="registerResult == null && getErrorMessage != null">{{ getErrorMessage }}</b-alert>
      <b-form-group label="Email address:" class="info-value" label-for="email">
        <b-form-input id="email" class="info-title" type="text" placeholder="Email" v-model="email"></b-form-input>
      </b-form-group>
      <b-form-group label="Password:" class="info-value" label-for="password">
        <b-form-input id="password" class="info-title" type="password" placeholder="Password" v-model="password"></b-form-input>
      </b-form-group>
      <b-form-group label="Repeat password:" class="info-value" label-for="repeat-password">
        <b-form-input id="repeat-password" class="info-title" type="password" placeholder="Repeat password" v-model="repeatPassword"></b-form-input>
      </b-form-group>
      <b-button type="submit" class="btn-custom primary width-150px">Register</b-button>
    </b-form>
  </div>
</template>

<script>
import Utils from '@/utils/Utils.js';

export default {
  mounted() {
    this.resetFields();
    this.checkLoggedIn();
  },
  data: function () {
    return{
      email: '',
      password: '',
      repeatPassword: '',
      errorMessage: null,
    };
  },
  computed: {
    registerResult() {
      return this.$store.getters.getRegister;
    },
    getErrorMessage() {
      return this.$data.errorMessage;
    }
  },
  methods: {
    register() {
      let isValidEmail = Utils.isValidEmail(this.$data.email);
      let isValidPassword = Utils.isValidPassword(this.$data.password);
      let passwordsMatch = this.$data.password == this.$data.repeatPassword;

      if(!isValidEmail) {
        this.errorMessage = "Invalid email";
      } else if (!isValidPassword) {
        if(this.$data.password.length < 8) {
          this.errorMessage =  "Password must be at least 8 characters long";
        } else {
          this.errorMessage = "Password must contain uppercase and lowercase characters and at least 1 number";
        }
      } else if (!passwordsMatch) {
        this.errorMessage = "Password don't match";
      }

      if(isValidEmail && isValidPassword && passwordsMatch) {
        this.$store.dispatch('register', {
          'email': this.$data.email,
          'password': this.$data.password
        });
      }
    },
    checkLoggedIn() {
      let accessToken = this.$session.get('access_token');
      if (accessToken == null) accessToken = '';

      this.$store.commit('setIsLoggedIn', true);

      if(accessToken != "") this.$router.push('/');
    },
    resetFields() {
      this.$data.email = "";
      this.$data.password = "";
      this.$data.errorMessage = null;
    }
  }
}

</script>
