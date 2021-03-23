<template>
  <div class="register">
    <b-form>
      <h3>Register</h3>
      <hr />
      <b-alert variant="success" show v-if="registerResult != null && registerResult.success">{{ registerResult.message }}</b-alert>
      <b-alert variant="danger" show v-if="registerResult != null && !registerResult.success && registerResult.message != ''">{{ registerResult.message }}</b-alert>
      <b-form-group label="Email address:" label-for="email">
        <b-form-input id="email" type="text" placeholder="Email" v-model="email"></b-form-input>
      </b-form-group>
      <b-form-group label="Password:" label-for="password">
        <b-form-input id="password" type="password" placeholder="Password" v-model="password"></b-form-input>
      </b-form-group>
      <b-form-group label="Repeat password:" label-for="repeat-password">
        <b-form-input id="repeat-password" type="password" placeholder="Repeat password" v-model="repeatPassword"></b-form-input>
      </b-form-group>
      <b-button type="button" variant="primary" v-on:click="register">Register</b-button>
    </b-form>
  </div>
</template>

<script>

export default {
  mounted() {
    this.checkLoggedIn();
  },
  data: function () {
    return{
      email: '',
      password: '',
      repeatPassword: '',
    };
  },
  computed: {
    registerResult() {
      return this.$store.getters.getRegister;
    }
  },
  methods: {
    register() {
      this.$store.dispatch('register', {
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
