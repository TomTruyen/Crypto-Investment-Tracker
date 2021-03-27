<template>
  <div class="home">
      <ul class="mb-0 pl-3">
        <li v-for="name in submitted">{{ name }}</li>
      </ul>

      <b-button variant="primary" v-b-modal.buy>Buy</b-button>
      <b-modal id="buy" ref="modal" title="Buy crypto" @show="resetModal" @hidden="resetModal" @ok="handleOk">
        <form ref="form" @submit.stop.prevent="handleSubmit">
          <b-form-group label="Crypto's" label-for="crypto-select" :state="cryptoState">
            <b-form-select id="crypto-select" :options="getCmcCryptoOptions" v-model="crypto" :state="cryptoState"></b-form-select>
          </b-form-group>
        </form>
      </b-modal>

    <h1>Home page</h1>
    <p>Main Tracker comes here</p>

    <b-table striped hover :items="getPortfolioOptions"></b-table>
  </div>
</template>

<script>
  export default {
    mounted() {
      this.setCmcPrices();
      this.setCmcCryptos();
      this.setPortfolio();
    },
    data() {
      return {
        crypto: '',
        cryptoState: null,
        submitted: [],
      }
    },
    computed: {
      getCmcCryptoOptions() {
        return this.$store.getters.getCmcCryptosAsOptions;
      },
      getPortfolioOptions() {
        return this.$store.getters.getPortfolioOptions;
      }
    },
    methods: {
      resetModal() {
        this.crypto = "";
        this.cryptoState = null;
      },
      handleOk(e) {
        e.preventDefault();
        this.handleSubmit();
      },
      handleSubmit() {
        this.submitted.push(this.crypto);

        this.$nextTick(() => {
          this.$bvModal.hide('buy')
        });
      },
      setCmcPrices() {
         const token = this.$cookie.get('access_token');
          this.$store.dispatch('setCmcPrices', token);
      },
      setCmcCryptos() {
        const token = this.$cookie.get('access_token');
        this.$store.dispatch('setCmcCryptos', token);
      },
      setPortfolio() {
        const token = this.$cookie.get('access_token');
        this.$store.dispatch('setPortfolio', token);
      }
    }
  }
</script>