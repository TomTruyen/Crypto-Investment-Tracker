<template>
  <div class="history">
    <div class="row margin-0">
      <div class="col my-auto">
        Total Profit (USD): {{'$' + getTotalProfit}}
      </div>
    </div>


    <b-table hover :items="getPortfolioHistoryOptions" :fields="fields">
      <template #cell(date)="row">
        <span class="info-value my-auto">{{row.item.date}}</span>
      </template>

      <template #cell(name)="row">
        <img width="20" height="20" :src="row.item.details.image" />
        <div class="spacer"></div>
        <span class="info-value my-auto">{{row.item.details.name}}</span>
        <div class="spacer-5"></div>
        <span class="info-title-ticker my-auto">{{`${row.item.details.ticker}`}}</span>
      </template>

      <template #cell(buy_price)="row">
        <span class="info-value my-auto">{{row.item.buy_price}}</span>
      </template>

      <template #cell(sell_amount)="row">
        <span class="info-value my-auto">{{row.item.sell_amount}}</span>
      </template>
    
      <template #cell(sell_price)="row">
        <span class="info-value my-auto">{{row.item.sell_price}}</span>
      </template>

      <template #cell(profit)="row">
        <span :class="row.item.profitGreaterThanZero ? 'info-value my-auto up' : 'info-value my-auto down'">{{row.item.profitGreaterThanZero ? '&#9650;' : '&#9660;'}} {{row.item.profit}}</span>
      </template>

      <template #cell(profit_usd)="row">
        <span :class="row.item.profitGreaterThanZero ? 'info-value my-auto up' : 'info-value my-auto down'">{{row.item.profitGreaterThanZero ? '&#9650;' : '&#9660;'}} {{row.item.profit_usd}}</span>      </template>

    </b-table>
  </div>
</template>

<script>
  export default {
    mounted() {
      this.fetchHistory();
    },
    data() {
      return {
        fields: [
          {key: 'date', label: 'Date', sortable: true},
          {key: 'name', label: 'Name', sortable: true},
          {key: 'buy_price', label: 'Buy Price', sortable: true},
          {key: 'sell_amount', label: 'Sell Amount', sortable: true},
          {key: 'sell_price', label: 'Sell Price', sortable: true},
          {key: 'profit', label: 'Profit/Loss %', sortable: true},
          {key: 'profit_usd', label: 'Profit/Loss USD', sortable: true},
        ]
      };
    }, 
    computed: {
      getPortfolioHistoryOptions() {
        return this.$store.getters.getPortfolioHistoryOptions;
      },
      getTotalProfit() {
        return this.$store.getters.getPortfolioHistoryProfit;
      }
    },
    methods: {
      fetchHistory() {
        const token = this.$session.get('access_token');
        this.$store.dispatch('setPortfolioHistory', token);
      }
    }
  }
</script>