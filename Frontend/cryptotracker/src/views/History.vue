<template>
  <div class="history">
    <div class="row margin-0">
      <div class="col my-auto">
        Total Profit (USD): {{'$' + getTotalProfit}}
      </div>
    </div>


    <b-table striped hover :items="getPortfolioHistoryOptions" :fields="fields"></b-table>
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
          {key: 'name', label: 'Asset Name', sortable: true},
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