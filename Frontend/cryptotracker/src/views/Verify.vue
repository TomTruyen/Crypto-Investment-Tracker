<template>
  <div class="verify">
    <b-form>
      <h3>Verify email</h3>
      <hr />
      <b-alert variant="success" show v-if="verifyResult != null && verifyResult.success">{{ verifyResult.message }} You will be redirected in {{ countDown }}s</b-alert>
      <b-alert variant="danger" show v-if="verifyResult != null && !verifyResult.success && verifyResult.message != ''">{{ verifyResult.message }}</b-alert>
      <p>By clicking the button below you verify that your email is: <b>{{decodeBase64(email)}}</b></p>
      <b-button type="button" variant="primary" v-on:click="verify">Verify</b-button>
    </b-form>
  </div>
</template>

<script>
 export default {
    mounted() {
     this.checkLoggedIn();

     let email = this.$route.query.e;
     if(email != undefined) this.$data.email = email;
    },
    data() {
        return {
            email: '',
            countDown : 5
        }
    },
    watch: {
      verifyResult(newResult) {
        if(newResult.success) {
          this.countDownTimer();
        }
      }
    },
    computed: {
      verifyResult() {
        return this.$store.getters.getVerify;
      }
    },
    methods: {
        verify() {
            this.$store.dispatch('verify', this.$data.email);
        },
        checkLoggedIn() {
            let accessToken = this.$session.get('access_token');
            if (accessToken == null) accessToken = '';

            this.$store.commit('setIsLoggedIn', true);

            if(accessToken != "") this.$router.push('/');
        },
        decodeBase64: function (string) {
            return atob(string);
        },
        countDownTimer() {
            if(this.countDown > 0) {
                setTimeout(() => {
                    this.countDown -= 1
                    this.countDownTimer()
                }, 1000)
            } else {
              this.$router.push('/login');
            }
        }
    }
 }
</script>