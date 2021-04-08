<template>
  <div class="home">  
    <div class="row margin-0">
      <div class="col my-auto">
        <h1>Portfolio Value: {{'$' + getPortfolioValue}}</h1>
      </div>
    </div>
    <div class="row margin-0">
      <div class="col my-auto">
        Refresh in: {{formattedTime}}
      </div>
      <div class="col margin-vertical-1em">
        <b-button variant="primary" class="float-right width-200px" v-b-modal.buy>BUY</b-button>
      </div>
    </div>

    <b-modal id="buy" ref="modal" title="Buy crypto" @show="resetModal" @hidden="resetModal" @ok="handleBuy">
      <form ref="form" @submit.stop.prevent="handleBuySubmit">
        <b-form-group label="Asset" label-for="crypto-select">
          <v-select id="crypto-select" :options="getCoingeckoCryptoOptions" :reduce="option => option.value" v-model="crypto">
              <template slot="option" slot-scope="option">
                  <img width="32" height="32" :src="option.img" />
                  <div class="spacer"></div>
                  {{ option.label }}
              </template>
          </v-select>
        </b-form-group>
        <b-form-group label="Amount" label-for="crypto-amount">
          <b-form-input id="crypto-amount" type="number" v-model="amount" placeholder="0"></b-form-input>
        </b-form-group>
        <b-form-group label="Price" label-for="crypto-price">
          <b-form-input id="crypto-price" type="number" v-model="price" placeholder="0"></b-form-input>
        </b-form-group>

        <b-alert show variant="danger" v-if="errorMessage != null">{{errorMessage}}</b-alert>
      </form>
    </b-modal>

    <b-modal id="sell" ref="modal" title="Sell crypto" @hidden="resetModal" @ok="handleSell">
      <form ref="form" @submit.stop.prevent="handleSellSubmit">
        <b-form-group label="Asset" label-for="crypto-select">
          <v-select id="crypto-select" :options="getCoingeckoCryptoOptions" :reduce="option => option.value" v-model="sellCrypto" disabled>
              <template slot="option" slot-scope="option">
                  <img width="32" height="32" :src="option.img" />
                  <div class="spacer"></div>
                  {{ option.label }}
              </template>
          </v-select>
        </b-form-group>
        <b-form-group label="Amount" label-for="crypto-amount">
          <b-form-input id="crypto-amount" type="number" v-model="sellAmount" placeholder="0"></b-form-input>
        </b-form-group>
        <b-form-group label="Price" label-for="crypto-price">
          <b-form-input id="crypto-price" type="number" v-model="sellPrice" placeholder="0"></b-form-input>
        </b-form-group>

        <b-alert show variant="danger" v-if="errorMessage != null">{{errorMessage}}</b-alert>
      </form>
    </b-modal>

    <b-table striped hover :items="getPortfolioOptions" :fields="fields">
      <template #cell(actions)="row">
        <b-button size="sm" @click="sell(row.item, row.index, $event.target)" class="mr-1" variant="danger" v-b-modal.sell>SELL</b-button>
      </template>
    </b-table>
  </div>
</template>

<script>
  export default {
    mounted() {
      this.fetchCryptos();
    },
    data() {
      return {
        timer: 0,
        formattedTime: '15:00',
        crypto: '',
        amount: 0,
        price: 0,
        sellCrypto: '',
        sellId: 0,
        sellAmount: 0,
        sellMaxAmount: 0,
        sellPrice: 0,
        errorMessage: null,
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
      getCoingeckoCryptoOptions() {
        const coingeckoCryptos = this.$store.getters.getCoingeckoCryptosAsOptions;

        const found = coingeckoCryptos.find(crypto => crypto.value.toUpperCase() == this.$data.sellCrypto.toUpperCase());

        if(found != undefined) return [found];

        return coingeckoCryptos;
      },
      getPortfolioOptions() {
        return this.$store.getters.getPortfolioOptions;
      },
      getPortfolioValue() {
        return this.$store.getters.getPortfolioValue;
      }
    },
    methods: {
      resetTimer() {
        this.timer = 60000;
        this.setFormattedTime();
      },
      startTimer() {
        if(this.timer <= 0) {
          this.fetchCryptos();
        } else {
          setTimeout(() => {
              this.timer -= 1000;
              this.setFormattedTime();
              this.startTimer()
          }, 1000)
        }
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
      resetModal() {
        this.crypto = "";
        this.amount = 0;
        this.price = 0;

        this.sellCrypto = "";
        this.sellAmount = 0;
        this.sellPrice = 0;

        this.errorMessage = null;
      },
      handleBuy(e) {
        e.preventDefault();
        this.handleBuySubmit();
      },
      handleBuySubmit() {
        if(this.sellCrypto == "") this.errorMessage = "Crypto missing";
        else if(this.sellAmount == 0) this.errorMessage = "Buy amount must be greater than 0";
        else if(this.sellPrice == 0) this.errorMessage = "Buy price must be greater than 0";


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
      handleSell(e) {
        e.preventDefault();
        this.handleSellSubmit();
      },
      handleSellSubmit() {
        if(this.sellCrypto == "") this.errorMessage = "Crypto missing";
        else if(this.sellAmount == 0) this.errorMessage = "Sell amount must be greater than 0";
        else if (this.sellMaxAmount < this.sellAmount) this.errorMessage = "Sell amount can't be more than " + this.sellMaxAmount;
        else if(this.sellPrice == 0) this.errorMessage = "Sell price must be greater than 0";

        if(this.sellCrypto != "" && this.sellAmount != 0 && this.sellPrice != 0) {
          this.$store.dispatch('sellCrypto', {
            'token': this.$cookie.get('access_token'),
            'id': this.sellId,
            'crypto': this.sellCrypto,
            'amount': this.sellAmount,
            'price': this.sellPrice,
          });

          this.$nextTick(() => {
            this.$bvModal.hide('sell')
          });
        }
      },
      async fetchCryptos() {
        const token = this.$cookie.get('access_token');
        const isSuccess = await this.$store.dispatch('setCoingeckoCryptos', token);

        if(isSuccess) {
          this.resetTimer();
          this.startTimer();
        }
      },
      sell(item, index, button) {
        const id = item.id; 
        const ticker = item.ticker;
        const maxAmount = item.balance;

        this.$data.sellCrypto = ticker;
        this.$data.sellId = id;
        this.$data.sellMaxAmount = maxAmount;
      },
    }
  }
</script>