<template>
  <div class="home">  
    <div class="row">
      <div class="col position-relative my-5">
        <DoughnutChart ref="portfolio_chart" :chart-data="getPortfolioChartData" v-if="getPortfolioChartData.labels.length > 0"></DoughnutChart>
        <h2 class="absolute-center text-center">
          <span class="info-value font-weight-normal font-size-2-half-rem">{{'$' + getPortfolioValue}}</span>
          <br />
          <span class="info-sub-title font-size-1-quarter-rem">{{getPortfolioAssets + ' Assets'}}</span>
        </h2>
        <p class="position-absolute refresh-position info-title">Refresh in: {{formattedTime}}</p>
      </div>
    </div>
    <div class="row ">
      
      <div class="col margin-vertical-1em ">
        <input type="text" placeholder="Search..." class="form-control width-200px info-sub-title" v-model="search" v-on:keyup="searchUpdate()" />
      </div>
      <div class="col margin-vertical-1em">
        <b-button class="btn-custom float-right width-200px" v-b-modal.buy>Buy</b-button>
      </div>
    </div>

    <b-modal id="buy" ref="modal" title="Buy crypto" @show="resetModal" @hidden="resetModal">
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

      <template slot="modal-footer">
        <button class="btn btn-custom" @click="handleBuy">Buy</button>
      </template>
    </b-modal>

    <b-modal id="sell" ref="modal" title="Sell crypto" @hidden="resetModal">
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

      <template slot="modal-footer">
        <button class="btn btn-custom" @click="handleSell">Sell</button>
      </template>
    </b-modal>

    <b-modal id="info" ref="modal" :title="info.title" v-if="info.item != null" :hide-footer="true">
      <b-row>
        <b-col class="info-chapter"><caption>{{info.item.name}} Price Today</caption></b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">{{info.item.name}} Price</b-col>
        <b-col class="text-right my-auto info-value">{{info.item.details.getPriceDollar()}}</b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">Price Change (24h)</b-col>
        <b-col class="text-right my-auto">
          <b-row>
            <b-col class="info-value">{{info.item.details.getPriceChangeDollar()}}</b-col>
          </b-row>
          <b-row>
            <b-col :class="info.item.details['price_change_24h'] >= 0 ? 'info-sub-value-up' : 'info-sub-value-down'">{{info.item.details['price_change_24h'] >= 0 ? '&#9650;' : '&#9660;'}}{{info.item.details.getPriceChangePercentage()}}</b-col>
          </b-row>
        </b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">24h Low / 24h High</b-col>
        <b-col class="text-right my-auto info-value">{{info.item.details.getLow24hDollar()}} / {{info.item.details.getHigh24hDollar()}}</b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">Trading Volume (24h)</b-col>
        <b-col class="text-right my-auto info-value">{{info.item.details.getVolume24hDollar()}}</b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">Market Rank</b-col>
        <b-col class="text-right my-auto info-value">#{{info.item.details.rank}}</b-col>
      </b-row>
      <div class="info-spacing"></div>
      <b-row>
        <b-col class="info-chapter"><caption>{{info.item.name}} Market Cap</caption></b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">Market Cap</b-col>
        <b-col class="text-right my-auto">
          <b-row>
            <b-col class="info-value">{{info.item.details.getMarketCapDollar()}}</b-col>
          </b-row>
          <b-row>
            <b-col :class="info.item.details['market_cap_percent_change_24h'] >= 0 ? 'info-sub-value-up' : 'info-sub-value-down'">{{info.item.details['market_cap_percent_change_24h'] >= 0 ? '&#9650;' : '&#9660;'}}{{info.item.details.getMarketCapPercentage()}}</b-col>
          </b-row>
        </b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">Fully Diluted Market Cap</b-col>
        <b-col class="text-right my-auto">
          <b-row>
            <b-col class="info-value">{{info.item.details.getFullyDilutedMarketCapDollar()}}</b-col>
          </b-row>
          <b-row>
            <b-col :class="info.item.details['market_cap_percent_change_24h'] >= 0 ? 'info-sub-value-up' : 'info-sub-value-down'">{{info.item.details['market_cap_percent_change_24h'] >= 0 ? '&#9650;' : '&#9660;'}}{{info.item.details.getMarketCapPercentage()}}</b-col>
          </b-row>
        </b-col>
      </b-row>
      <div class="info-spacing"></div>
      <b-row>
        <b-col class="info-chapter"><caption>{{info.item.name}} Price History</caption></b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">24h Price Change (Percentage)</b-col>
        <b-col :class="info.item.details['price_percent_change_24h'] >= 0 ? 'text-right my-auto info-value up' : 'text-right my-auto info-value down'">{{info.item.details['price_percent_change_24h'] >= 0 ? '&#9650;' : '&#9660;'}}{{info.item.details.getPriceChangePercentage()}}</b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">7d Price Change (Percentage)</b-col>
        <b-col :class="info.item.details['price_percent_change_7d'] >= 0 ? 'text-right my-auto info-value up' : 'text-right my-auto info-value down'">{{info.item.details['price_percent_change_7d'] >= 0 ? '&#9650;' : '&#9660;'}}{{info.item.details.getPriceChange7dPercentage()}}</b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">30d Price Change (Percentage)</b-col>
        <b-col :class="info.item.details['price_percent_change_30d'] >= 0 ? 'text-right my-auto info-value up' : 'text-right my-auto info-value down'">{{info.item.details['price_percent_change_30d'] >= 0 ? '&#9650;' : '&#9660;'}}{{info.item.details.getPriceChange30dPercentage()}}</b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">1y Price Change (Percentage)</b-col>
        <b-col :class="info.item.details['price_percent_change_1y'] >= 0 ? 'text-right my-auto info-value up' : 'text-right my-auto info-value down'">{{info.item.details['price_percent_change_1y'] >= 0 ? '&#9650;' : '&#9660;'}}{{info.item.details.getPriceChange1yPercentage()}}</b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col>
          <b-row class="my-auto">
            <b-col class="info-title">All Time High</b-col>
          </b-row>
          <b-row>
            <b-col class="info-sub-title">{{info.item.details.getAllTimeHighDate()}}</b-col>
          </b-row>
        </b-col>
        <b-col class="text-right my-auto">
          <b-row>
            <b-col class="info-value">{{info.item.details.getAllTimeHighDollar()}}</b-col>
          </b-row>
          <b-row>
            <b-col :class="info.item.details['allTimeHighPercentage'] >= 0 ? 'info-sub-value-up' : 'info-sub-value-down'">{{info.item.details['allTimeHighPercentage'] >= 0 ? '&#9650;' : '&#9660;'}}{{info.item.details.getAllTimeHighPercentage()}}</b-col>
          </b-row>
        </b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col>
          <b-row class="my-auto">
            <b-col class="info-title">All Time Low</b-col>
          </b-row>
          <b-row>
            <b-col class="info-sub-title">{{info.item.details.getAllTimeLowDate()}}</b-col>
          </b-row>
        </b-col>
        <b-col class="text-right my-auto">
          <b-row>
            <b-col class="info-value">{{info.item.details.getAllTimeLowDollar()}}</b-col>
          </b-row>
          <b-row>
            <b-col :class="info.item.details['allTimeLowPercentage'] >= 0 ? 'info-sub-value-up' : 'info-sub-value-down'">{{info.item.details['allTimeLowPercentage'] >= 0 ? '&#9650;' : '&#9660;'}}{{info.item.details.getAllTimeLowPercentage()}}</b-col>
          </b-row>
        </b-col>
      </b-row>
      <div class="info-spacing"></div>
      <b-row>
        <b-col class="info-chapter"><caption>{{info.item.name}} Supply</caption></b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">Circulating Supply</b-col>
        <b-col class="text-right my-auto info-value">{{info.item.details.getCirculatingSupply()}}</b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">Total Supply</b-col>
        <b-col class="text-right my-auto info-value">{{info.item.details.getTotalSupply()}}</b-col>
      </b-row>
      <hr />
      <b-row>
        <b-col class="info-title my-auto">Max Supply</b-col>
        <b-col class="text-right my-auto info-value">{{info.item.details.getMaxSupply()}}</b-col>
      </b-row>
    </b-modal>

    <b-row>
      <b-col class="overflow-x-auto">
        <b-table hover :items="getPortfolioOptions" :fields="fields">
          <template #cell(name)="row">
            <img width="20" height="20" :src="row.item.image" />
            <div class="spacer"></div>
            <span class="info-value my-auto">{{row.item.name}}</span>
            <div class="spacer-5"></div>
            <span class="info-title-ticker my-auto">{{row.item.ticker}}</span>
          </template>

          <template #cell(avg_price)="row">
            <span class="info-value my-auto">{{row.item['avg_price']}}</span>
          </template>

          <template #cell(price)="row">
            <span class="info-value my-auto">{{row.item.price}}</span>
          </template>

          <template #cell(change_24h)="row">
            <span :class="row.item.details['price_percent_change_24h'] >= 0 ? 'info-value my-auto up' : 'info-value my-auto down'">
              {{row.item.details['price_percent_change_24h'] >= 0 ? '&#9650;' : '&#9660;'}} {{row.item.details.getPriceChangePercentage()}}
            </span>
          </template>

          <template #cell(balance)="row">
            <span class="info-value my-auto">{{row.item.balance}}</span>
          </template>

          <template #cell(value)="row">
            <span class="info-value my-auto">{{row.item.value}}</span>
          </template>

          <template #cell(profit)="row">
            <span :class="row.item.profitGreaterThanZero ? 'info-value my-auto up' : 'info-value my-auto down'">{{row.item.profitGreaterThanZero ? '&#9650;' : '&#9660;'}} {{row.item.profit}}</span>
          </template>

         
          <template #cell(infoAction)="row">
            <div class="text-center">
              <b-button size="sm" class="mr-1 btn-custom" @click="setInfo(row.item, row.index, $event.target)" v-b-modal.info>Info</b-button>
            </div>
          </template>
          <template #cell(expandAction)="row">
            <div class="text-center">
              <b-button size="sm" class="mr-1 btn-custom" @click="row.toggleDetails" :disabled="row.item.instances.length <= 0">{{!row.detailsShowing ? 'MORE' : 'LESS'}}</b-button>
            </div>
          </template>
          <template slot="row-details" slot-scope="row">
            <b-table :items="row.item.instances" :fields="detailFields">
                <template #cell(name)="row">
                  <img width="20" height="20" :src="row.item.image" />
                  <div class="spacer"></div>
                  <span class="info-value my-auto">{{row.item.name}}</span>
                  <div class="spacer-5"></div>
                  <span class="info-title-ticker my-auto">{{row.item.ticker}}</span>
                </template>

                <template #cell(date)="row">
                  <span class="info-value my-auto">{{row.item.date}}</span>
                </template>
                
                <template #cell(price)="row">
                  <span class="info-value my-auto">{{row.item.price}}</span>
                </template>

                <template #cell(amount)="row">
                  <span class="info-value my-auto">{{row.item.balance}}</span>
                </template>

                <template #cell(value)="row">
                  <span class="info-value my-auto">{{row.item.value}}</span>
                </template>

                <template #cell(profit)="row">
                  <span :class="row.item.profitGreaterThanZero ? 'info-value my-auto up' : 'info-value my-auto down'">{{row.item.profitGreaterThanZero ? '&#9650;' : '&#9660;'}} {{row.item.profit}}</span>
                </template>

               <template #cell(sellAction)="row">
                <div class="text-center">
                  <b-button size="sm" @click="sell(row.item, row.index, $event.target)" variant="danger" class="mr-1 btn-custom btn-custom-danger" v-b-modal.sell>Sell</b-button>
                </div>
              </template>
            </b-table>
          </template>
        </b-table>
      </b-col>
    </b-row>
    
  </div>
</template>

<script>
  import DoughnutChart from '@/components/DoughnutChart.vue';

  export default {
    mounted() {
      this.$store.commit('clearSearch');

      this.fetchCryptos();
    },
    components: { DoughnutChart },
    data() {
      return {
        search: '',
        timer: 0,
        formattedTime: '01:00',
        crypto: '',
        amount: 0,
        price: 0,
        sellCrypto: '',
        sellId: 0,
        sellAmount: 0,
        sellMaxAmount: 0,
        sellPrice: 0,
        errorMessage: null,
        info: {
          title: '',
          item: null,
        },
        // used for table displaying
        fields: [
          {key: 'name', label: 'Name', sortable: true},
          {key: 'avg_price', label: 'Avg. Price', sortable: true},
          {key: 'price', label: 'Price', sortable: true},
          {key: 'change_24h', label: '24h %', sortable: true},
          {key: 'balance', label: 'Balance', sortable: true},
          {key: 'value', label: 'Value', sortable: true},
          {key: 'profit', label: 'Profit/Loss', sortable: true},
          {key: 'infoAction', label: ''},
          {key: 'expandAction', label: ''},
        ],
        detailFields: [
          {key: 'name', label: 'Name', sortable: true},
          {key: 'date', label: 'Buy Date', sortable: true},
          {key: 'price', label: 'Buy Price', sortable: true},
          {key: 'amount', label: 'Amount', sortable: true},
          {key: 'value', label: 'Value', sortable: true},
          {key: 'profit', label: 'Profit/Loss', sortable: true},
          {key: 'sellAction', label: ''},
        ]
      }
    }, 
    computed: {
      getCoingeckoCryptoOptions() {
        const coingeckoCryptos = this.$store.getters.getCoingeckoCryptosAsOptions;

        if(crypto != '') {
          const found = coingeckoCryptos.find(crypto => crypto.value.toUpperCase() == this.$data.sellCrypto.toUpperCase());

          if(found != undefined) return [found];
        }

        return coingeckoCryptos;
      },
      getPortfolioOptions() {
        return this.$store.getters.getPortfolioOptions;
      },
      getPortfolioValue() {
        return this.$store.getters.getPortfolioValue;
      },
      getPortfolioAssets() {
        return this.$store.getters.getPortfolioAssets;
      },
      getPortfolioChartData() {
        return this.$store.getters.getPortfolioChartData
      },
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
        if(this.crypto == "") this.errorMessage = "Crypto missing";
        else if(this.amount == 0) this.errorMessage = "Buy amount must be greater than 0";
        else if(this.price == 0) this.errorMessage = "Buy price must be greater than 0";


        if(this.crypto != "" && this.amount != 0 && this.price != 0) {
          this.$store.dispatch('buyCrypto', {
            'token': this.$session.get('access_token'),
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
            'token': this.$session.get('access_token'),
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
        const token = this.$session.get('access_token');
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
      setInfo(item, index, button) {
        this.$data.info.title = `${item.name} (${item.ticker}) Statistics`;
        this.$data.info.item = item;
      },
      searchUpdate() {
        this.$store.commit('updateSearch', this.search);
      }
    }
  }
</script>