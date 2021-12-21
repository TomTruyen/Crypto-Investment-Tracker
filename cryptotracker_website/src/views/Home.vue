<template>
  <div class="home">
    <div class="row">
      <div class="col position-relative my-5">
        <DoughnutChart
          ref="portfolio_chart"
          :chart-data="getPortfolioChartData"
          v-if="getPortfolioChartData.labels.length > 0"
        />
        <DoughnutChartText
          :value="getPortfolioValue"
          :count="getPortfolioAssets"
          :refreshTime="formattedTime"
        />
      </div>
    </div>
    <div class="row">
      <div class="col margin-vertical-1em">
        <input
          type="text"
          placeholder="Search..."
          class="form-control width-200px info-sub-title"
          v-model="search"
          @keyup="searchUpdate"
        />
      </div>
      <div class="col margin-vertical-1em">
        <b-button class="btn-custom float-right width-200px" v-b-modal.buy
          >Buy</b-button
        >
      </div>
    </div>

    <BuyCryptoModal :coingeckoCryptos="getCoingeckoCryptoOptions" />
    <SellCryptoModal
      :id="sellId"
      :crypto="sellCrypto"
      :maxAmount="sellMaxAmount"
      :coingeckoCryptos="getCoingeckoCryptoOptions"
    />
    <SetPriceAlertModal :alertId="alertId" :alertCrypto="alertCrypto" />
    <DeletePriceAlertModal
      :alertId="alertId"
      :alertPrice="alertPrice"
      :alertCrypto="alertCrypto"
    />
    <CryptoInfoModal :info="info" />

    <b-row>
      <b-col class="overflow-x-auto">
        <b-table hover :items="getPortfolioOptions" :fields="fields">
          <template #cell(alert)="row">
            <b-icon-bell
              class="price-alert-icon"
              v-if="row.item.alert == 0"
              v-b-modal.alertSet
              @click="setupAlert(row.item.id, row.item.ticker, 0)"
            ></b-icon-bell>
            <b-icon-bell-fill
              class="price-alert-icon filled"
              v-else
              v-b-modal.alertDelete
              @click="setupAlert(row.item.id, row.item.ticker, row.item.alert)"
            ></b-icon-bell-fill>
          </template>

          <template #cell(name)="row">
            <img width="20" height="20" :src="row.item.image" />
            <div class="spacer"></div>
            <span class="info-value my-auto">{{ row.item.name }}</span>
            <div class="spacer-5"></div>
            <span class="info-title-ticker my-auto">{{ row.item.ticker }}</span>
          </template>

          <template #cell(avg_price)="row">
            <span class="info-value my-auto">{{ row.item["avg_price"] }}</span>
          </template>

          <template #cell(price)="row">
            <span class="info-value my-auto">{{ row.item.price }}</span>
          </template>

          <template #cell(change_24h)="row">
            <span
              :class="
                row.item.details['price_percent_change_24h'] >= 0
                  ? 'info-value my-auto up'
                  : 'info-value my-auto down'
              "
            >
              {{
                row.item.details["price_percent_change_24h"] >= 0
                  ? "&#9650;"
                  : "&#9660;"
              }}
              {{ row.item.details.getPriceChangePercentage() }}
            </span>
          </template>

          <template #cell(balance)="row">
            <span class="info-value my-auto">{{ row.item.balance }}</span>
          </template>

          <template #cell(value)="row">
            <span class="info-value my-auto">{{ row.item.value }}</span>
          </template>

          <template #cell(profit)="row">
            <span
              :class="
                row.item.profitGreaterThanZero
                  ? 'info-value my-auto up'
                  : 'info-value my-auto down'
              "
              >{{ row.item.profitGreaterThanZero ? "&#9650;" : "&#9660;" }}
              {{ row.item.profit }}</span
            >
          </template>

          <template #cell(infoAction)="row">
            <div class="text-center">
              <b-button
                size="sm"
                class="mr-1 btn-custom"
                @click="setInfo(row.item, row.index, $event.target)"
                v-b-modal.info
                >Info</b-button
              >
            </div>
          </template>
          <template #cell(expandAction)="row">
            <div class="text-center">
              <b-button
                size="sm"
                class="mr-1 btn-custom"
                @click="toggleShowDetails(row.item.ticker)"
                :disabled="row.item.instances.length <= 0"
                v-if="!row.item._showDetails"
                ><b-icon-chevron-down></b-icon-chevron-down
              ></b-button>
              <b-button
                size="sm"
                class="mr-1 btn-custom"
                @click="toggleShowDetails(row.item.ticker)"
                :disabled="row.item.instances.length <= 0"
                v-else
                ><b-icon-chevron-up></b-icon-chevron-up
              ></b-button>
            </div>
          </template>
          <template slot="row-details" slot-scope="row">
            <b-table :items="row.item.instances" :fields="detailFields">
              <template #cell(name)="row">
                <img width="20" height="20" :src="row.item.image" />
                <div class="spacer"></div>
                <span class="info-value my-auto">{{ row.item.name }}</span>
                <div class="spacer-5"></div>
                <span class="info-title-ticker my-auto">{{
                  row.item.ticker
                }}</span>
              </template>

              <template #cell(date)="row">
                <span class="info-value my-auto">{{ row.item.date }}</span>
              </template>

              <template #cell(price)="row">
                <span class="info-value my-auto">{{ row.item.price }}</span>
              </template>

              <template #cell(amount)="row">
                <span class="info-value my-auto">{{ row.item.balance }}</span>
              </template>

              <template #cell(value)="row">
                <span class="info-value my-auto">{{ row.item.value }}</span>
              </template>

              <template #cell(profit)="row">
                <span
                  :class="
                    row.item.profitGreaterThanZero
                      ? 'info-value my-auto up'
                      : 'info-value my-auto down'
                  "
                  >{{ row.item.profitGreaterThanZero ? "&#9650;" : "&#9660;" }}
                  {{ row.item.profit }}</span
                >
              </template>

              <template #cell(sellAction)="row">
                <div class="text-center">
                  <b-button
                    size="sm"
                    @click="sell(row.item, row.index, $event.target)"
                    variant="danger"
                    class="mr-1 btn-custom btn-custom-danger"
                    v-b-modal.sell
                    >Sell</b-button
                  >
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
import {
  BIconChevronDown,
  BIconChevronUp,
  BIconBell,
  BIconBellFill,
} from "bootstrap-vue";

import DoughnutChart from "@/components/DoughnutChart.vue";
import DoughnutChartText from "@/components/DoughnutChartText.vue";
import BuyCryptoModal from "@/components/BuyCryptoModal.vue";
import SellCryptoModal from "@/components/SellCryptoModal.vue";
import SetPriceAlertModal from "@/components/SetPriceAlertModal.vue";
import DeletePriceAlertModal from "@/components/DeletePriceAlertModal.vue";
import CryptoInfoModal from "@/components/CryptoInfoModal.vue";

export default {
  mounted() {
    this.$store.commit("clearSearch");

    this.fetchCryptos();
  },
  components: {
    BIconChevronDown,
    BIconChevronUp,
    BIconBell,
    BIconBellFill,
    DoughnutChart,
    DoughnutChartText,
    BuyCryptoModal,
    SellCryptoModal,
    SetPriceAlertModal,
    DeletePriceAlertModal,
    CryptoInfoModal,
  },
  data() {
    return {
      search: "",
      timer: 0,
      formattedTime: "01:00",
      crypto: "",
      sellCrypto: "",
      sellId: 0,
      sellMaxAmount: 0,
      errorMessage: null,
      alertId: 0,
      alertPrice: 0,
      alertCrypto: "",
      info: {
        title: "",
        item: null,
      },
      // used for table displaying
      fields: [
        { key: "alert", label: "" },
        { key: "name", label: "Name", sortable: true },
        { key: "avg_price", label: "Avg. Price", sortable: true },
        { key: "price", label: "Price", sortable: true },
        { key: "change_24h", label: "24h %", sortable: true },
        { key: "balance", label: "Balance", sortable: true },
        { key: "value", label: "Value", sortable: true },
        { key: "profit", label: "Profit/Loss", sortable: true },
        { key: "infoAction", label: "" },
        { key: "expandAction", label: "" },
      ],
      detailFields: [
        { key: "name", label: "Name", sortable: true },
        { key: "date", label: "Buy Date", sortable: true },
        { key: "price", label: "Buy Price", sortable: true },
        { key: "amount", label: "Amount", sortable: true },
        { key: "value", label: "Value", sortable: true },
        { key: "profit", label: "Profit/Loss", sortable: true },
        { key: "sellAction", label: "" },
      ],
    };
  },
  computed: {
    getCoingeckoCryptoOptions() {
      return this.$store.getters.getCoingeckoCryptosAsOptions;
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
      return this.$store.getters.getPortfolioChartData;
    },
  },
  methods: {
    resetTimer() {
      this.timer = 60000;
      this.setFormattedTime();
    },
    startTimer() {
      if (this.timer <= 0) {
        this.fetchCryptos();
      } else {
        setTimeout(() => {
          this.timer -= 1000;
          this.setFormattedTime();
          this.startTimer();
        }, 1000);
      }
    },
    setFormattedTime() {
      let minutes = Math.floor(this.timer / 60000);
      let seconds = ((this.timer % 60000) / 1000).toFixed(0);

      if (seconds == 60) {
        minutes++;
        seconds = 0;
      }

      if (minutes < 10) {
        minutes = "0" + minutes;
      }

      if (seconds < 10) {
        seconds = "0" + seconds;
      }

      this.formattedTime = `${minutes}:${seconds}`;
    },
    async fetchCryptos() {
      const token = this.$session.get("access_token");

      if (token) {
        const isSuccess = await this.$store.dispatch(
          "setCoingeckoCryptos",
          token
        );

        if (isSuccess) {
          this.resetTimer();
          this.startTimer();
        }
      }
    },
    sell(item) {
      this.sellId = item.id;
      this.sellCrypto = item.ticker;
      this.sellMaxAmount = item.balanceValue;
    },
    setInfo(item) {
      this.$data.info.title = `${item.name} (${item.ticker}) Statistics`;
      this.$data.info.item = item;
    },
    setupAlert(id, ticker, price) {
      this.alertId = id;
      this.alertPrice = price;
      this.alertCrypto = ticker;
    },
    searchUpdate() {
      this.$store.commit("updateSearch", this.search);
    },
    toggleShowDetails(ticker) {
      this.$store.commit("toggleShowDetails", ticker);
    },
  },
};
</script>