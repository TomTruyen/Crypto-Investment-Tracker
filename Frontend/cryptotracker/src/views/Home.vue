<template>
  <div class="home">
      <ul class="mb-0 pl-3">
        <li v-for="name in submitted">{{ name }}</li>
      </ul>

      

    <h1>Home page</h1>
    <p>Main Tracker comes here</p>

    <b-button variant="primary" v-b-modal.buy>Buy</b-button>
      <b-modal id="buy" ref="modal" title="Buy crypto" @show="resetModal" @hidden="resetModal" @ok="handleOk">
        <form ref="form" @submit.stop.prevent="handleSubmit">
          <b-form-group label="Asset" label-for="crypto-select" :state="cryptoState">
            <b-form-select id="crypto-select" :options="getCmcCryptoOptions" v-model="crypto" :state="cryptoState"></b-form-select>
          </b-form-group>
          <b-form-group label="Amount" label-for="crypto-amount" :state="cryptoState">
            <b-form-input id="crypto-amount" type="number" v-model="amount" placeholder="0"></b-form-input>
          </b-form-group>
          <b-form-group label="Price" label-for="crypto-price" :state="cryptoState">
            <b-form-input id="crypto-price" type="number" v-model="price" placeholder="0"></b-form-input>
          </b-form-group>
        </form>
      </b-modal>

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
        amount: 0,
        price: 0,
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
        this.amount = 0;
        this.price = 0;
        this.cryptoState = null;
      },
      handleOk(e) {
        e.preventDefault();
        this.handleSubmit();
      },
      handleSubmit() {
        if(this.crypto != "" && this.amount != 0 && this.price != 0) {
          this.$store.dispatch('buyCrypto', {
            'token': this.$cookie.get('access_token'),
            'crypto': this.crypto,
            'amount': this.amount,
            'price': this.price,
          });

          this.$nextTick(() => {
            this.$bvModal.hide('buy')
          });
        }
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