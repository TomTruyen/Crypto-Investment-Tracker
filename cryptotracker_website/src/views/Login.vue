<template>
  <div class="login">
    <b-form v-on:submit.prevent="login">
      <h3>Login</h3>
      <br />
      <b-alert
        variant="danger"
        show
        v-if="loginResult != null && loginResult.message != ''"
        >{{ loginResult.message }}</b-alert
      >
      <b-alert
        variant="danger"
        show
        v-if="
          verifyResult != null &&
          !verifyResult.success &&
          verifyResult.message != ''
        "
        >{{ verifyResult.message }}</b-alert
      >
      <b-alert
        variant="success"
        show
        v-if="
          verifyResult != null &&
          verifyResult.success &&
          verifyResult.message != ''
        "
        >{{ verifyResult.message }}</b-alert
      >
      <b-link
        v-if="
          verifyResult == null &&
          loginResult != null &&
          loginResult.message.toLowerCase().includes('mail not verified')
        "
        v-on:click="resendVerification"
      >
        {{ getVerificationMessage }}
        <br /><br />
      </b-link>
      <b-alert
        variant="danger"
        show
        v-if="loginResult == null && getErrorMessage != null"
        >{{ getErrorMessage }}</b-alert
      >
      <b-form-group label="Email address:" class="info-value" label-for="email">
        <b-form-input
          id="email"
          class="info-title"
          type="text"
          placeholder="Email"
          v-model="email"
        ></b-form-input>
      </b-form-group>
      <b-form-group label="Password:" class="info-value" label-for="password">
        <b-form-input
          id="password"
          class="info-title"
          type="password"
          placeholder="Password"
          v-model="password"
        ></b-form-input>
      </b-form-group>
      <b-form-group>
        <router-link to="/resetpassword" class="forgotpassword-link"
          >Forgot your password?</router-link
        >
      </b-form-group>
      <b-button type="submit" class="btn-custom primary width-150px"
        >Login</b-button
      >
    </b-form>
  </div>
</template>

<script>
import { eventBus } from "@/events/event.js";
import Utils from "@/utils/Utils.js";

export default {
  mounted() {
    this.resetFields();
    this.checkLoggedIn();
  },
  data: function () {
    return {
      email: "",
      password: "",
      errorMessage: null,
      verificationMessage: "Resend verification email",
    };
  },
  watch: {
    loginResult(newResult) {
      if (newResult.success && newResult.token != "") {
        this.$session.set("access_token", newResult.token);

        eventBus.$emit("accessTokenSet", newResult.token);


        this.$router.push("/").catch(() => {});
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
    },
  },
  methods: {
    login() {
      let isValidEmail = Utils.isValidEmail(this.$data.email);
      let isNotEmptyPassword = this.$data.password.length > 0;

      if (!isValidEmail) {
        this.errorMessage = "Invalid email";
      } else if (!isNotEmptyPassword) {
        this.errorMessage = "Password can't be empty";
      }

      if (isValidEmail && isNotEmptyPassword) {
        this.$store.dispatch("login", {
          email: this.$data.email,
          password: this.$data.password,
        });
      }
    },
    resendVerification() {
      if (!this.$data.sendingVerification) {
        this.$data.verificationMessage = "Sending email...";

        this.$store.dispatch("resendVerification", this.$data.email);
      }
    },
    checkLoggedIn() {
      let accessToken = this.$session.get("access_token");
      if (accessToken == null) accessToken = "";

      this.$store.commit("setIsLoggedIn", true);

      if (accessToken != "") this.$router.push("/");
    },
    resetFields() {
      this.$data.email = "";
      this.$data.password = "";
      this.$data.errorMessage = null;
      this.$data.verificationMessage = "Resend verification email";
    },
  },
};
</script>