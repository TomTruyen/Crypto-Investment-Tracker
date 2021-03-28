<template>
  <div class="home">
      <ul class="mb-0 pl-3">
        <li v-for="name in submitted">{{ name }}</li>
      </ul>

      

    <h1>Home page</h1>
    <p>Refresh in: {{formattedTime}}</p>

    <b-button variant="primary" v-b-modal.buy>Buy</b-button>
      <b-modal id="buy" ref="modal" title="Buy crypto" @show="resetModal" @hidden="resetModal" @ok="handleOk">
        <form ref="form" @submit.stop.prevent="handleSubmit">
          <b-form-group label="Asset" label-for="crypto-select" :state="cryptoState">
            <v-select id="crypto-select" :options="getCmcCryptoOptions" :reduce="option => option.value" v-model="crypto">
                <template slot="option" slot-scope="option">
                    <img width="32" height="32" :src="option.img" />
                    <div class="spacer"></div>
                    {{ option.label }}
                </template>
            </v-select>
          </b-form-group>
          <b-form-group label="Amount" label-for="crypto-amount" :state="cryptoState">
            <b-form-input id="crypto-amount" type="number" v-model="amount" placeholder="0"></b-form-input>
          </b-form-group>
          <b-form-group label="Price" label-for="crypto-price" :state="cryptoState">
            <b-form-input id="crypto-price" type="number" v-model="price" placeholder="0"></b-form-input>
          </b-form-group>
        </form>
      </b-modal>

    <b-table striped hover :items="getPortfolioOptions" :fields="fields">
      <template #cell(actions)="row">
        <b-button size="sm" @click="sell(row.item, row.index, $event.target)" class="mr-1" variant="danger">SELL</b-button>
      </template>
    </b-table>
  </div>
</template>

<script>
  export default {
    mounted() {
      this.setInitialTimer();
      this.setTimer();

      this.setCmcPrices();
      this.setCmcCryptos();
      this.setPortfolio();
    },
    data() {
      return {
        timer: 0,
        formattedTime: '15:00',
        crypto: '',
        amount: 0,
        price: 0,
        cryptoState: null,
        submitted: [],
        // used for table displaying
        fields: [
          {key: 'name', label: 'Asset Name', sortable: true},
          {key: 'price', label: 'Price', sortable: true},
          {key: 'change_24h', label: '24H Change', sortable: true},
          {key: 'balance', label: 'Balance', sortable: true},
          {key: 'value', label: 'Value', sortable: true},
          {key: 'profit', label: 'Profit/Loss', sortable: true},
          {key: 'actions', label: 'Sell Crypto'},
        ]
      }
    }, 
    computed: {
      getCmcCryptoOptions() {
        return this.$store.getters.getCmcCryptosAsOptions;
      },
      getPortfolioOptions() {
        return this.$store.getters.getPortfolioOptions;
      },
    },
    methods: {
      refresh() {
        this.setCmcPrices();
        this.setCmcCryptos();
        this.setPortfolio();
      },
      setTimer() {
        if(this.timer <= 0) {
          this.refresh();
          this.timer = 900000;
          this.setFormattedTime();
        }

        setTimeout(() => {
            this.timer -= 1000;
            this.setFormattedTime();
            this.setTimer()
        }, 1000)
      },
      setFormattedTime() {
        let minutes = Math.floor(this.timer / 60000);
        let seconds = ((this.timer % 60000) / 1000).toFixed(0);

        if(seconds == 60) {
          minutes++;
          seconds = 0;
        }

        if(minutes < 10) {
          minutes = "0" + minutes;
        }

        if(seconds < 10) {
          seconds = "0" + seconds;
        }

        this.formattedTime = `${minutes}:${seconds}`;
      },
      setInitialTimer() {
        let date = new Date();
        let minutes = date.getMinutes();

        let time = 15;

        if(minutes >= 45) {
          time = 60 - minutes;
        }

        if (minutes < 45) {
          time = minutes - 30;
        }

        if(minutes < 30) {
          time = minutes - 15;
        }

        if(minutes < 15) {
          time = 15 - minutes;
        }

        this.timer = time * 60 * 1000;
        this.setFormattedTime();
      },
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
      },
      sell(item, index, button) {
        const id = item.id; //id with which it's stored in the Database

        // Should show popup (modal)
        const name = item.name;
        const maxAmount = item.balance;

        // There the user should have a textfield (not editable) with the 'name'
        // It should also make sure a user can't sell more than the 'maxAmount'

        // After confirming popup --> send to database
      },
    }
  }
</script>