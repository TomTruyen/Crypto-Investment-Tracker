<template>
  <div class="history">
    <div class="row">
      <div class="col my-auto history-total-profit">
        <span :class="isProfit ? 'up' : 'down'"
          >{{ isProfit ? "&#9650;" : "&#9660;" }} ${{ getTotalProfit }}</span
        >
      </div>
    </div>

    <div class="row">
      <div class="col margin-vertical-1em">
        <input
          type="text"
          placeholder="Search..."
          class="form-control width-200px info-sub-title"
          v-model="search"
          v-on:keyup="searchUpdate()"
        />
      </div>
    </div>

    <b-row>
      <b-col class="overflow-x-auto">
        <b-table hover :items="getPortfolioHistoryOptions" :fields="fields">
          <template #cell(date)="row">
            <span class="info-value my-auto">{{ row.item.date }}</span>
          </template>

          <template #cell(name)="row">
            <img width="20" height="20" :src="row.item.details.image" />
            <div class="spacer"></div>
            <span class="info-value my-auto">{{ row.item.details.name }}</span>
            <div class="spacer-5"></div>
            <span class="info-title-ticker my-auto">{{
              `${row.item.details.ticker}`
            }}</span>
          </template>

          <template #cell(buy_price)="row">
            <span class="info-value my-auto">{{ row.item.buy_price }}</span>
          </template>

          <template #cell(sell_amount)="row">
            <span class="info-value my-auto">{{ row.item.sell_amount }}</span>
          </template>

          <template #cell(sell_price)="row">
            <span class="info-value my-auto">{{ row.item.sell_price }}</span>
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

          <template #cell(profit_usd)="row">
            <span
              :class="
                row.item.profitGreaterThanZero
                  ? 'info-value my-auto up'
                  : 'info-value my-auto down'
              "
              >{{ row.item.profitGreaterThanZero ? "&#9650;" : "&#9660;" }}
              {{ row.item.profit_usd }}</span
            >
          </template>
        </b-table>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import Utils from "@/utils/Utils.js";

export default {
  mounted() {
    this.$store.commit("clearSearch");

    this.fetchHistory();
  },
  data() {
    return {
      search: "",
      fields: [
        { key: "date", label: "Date", sortable: true },
        { key: "name", label: "Name", sortable: true },
        { key: "buy_price", label: "Buy Price", sortable: true },
        { key: "sell_amount", label: "Sell Amount", sortable: true },
        { key: "sell_price", label: "Sell Price", sortable: true },
        { key: "profit", label: "Profit/Loss %", sortable: true },
        { key: "profit_usd", label: "Profit/Loss USD", sortable: true },
      ],
    };
  },
  computed: {
    getPortfolioHistoryOptions() {
      return this.$store.getters.getPortfolioHistoryOptions;
    },
    isProfit() {
      return this.$store.getters.getPortfolioHistoryProfit >= 0;
    },
    getTotalProfit() {
      return Utils.numberWithCommas(
        this.$store.getters.getPortfolioHistoryProfit,
        2,
        true,
        true
      );
    },
  },
  methods: {
    fetchHistory() {
      const token = this.$session.get("access_token");
      this.$store.dispatch("setPortfolioHistory", token);
    },
    searchUpdate() {
      this.$store.commit("updateSearch", this.search);
    },
  },
};
</script>