<script>
import { Doughnut, mixins } from "vue-chartjs";
const { reactiveProp } = mixins;

export default {
  extends: Doughnut,
  mixins: [reactiveProp],
  mounted() {
    const options = {
      legend: {
        display: false,
      },
      responsive: true,
      maintainAspectRatio: false,
      cutoutPercentage: 95,
      tooltips: {
        enabled: true,
        callbacks: {
          title: function (tooltipItem, data) {
            return data["labels"][tooltipItem[0]["index"]];
          },
          label: function (tooltipItem, data) {
            const dataset = data["datasets"][0];

            // Total value
            let value = dataset["data"][tooltipItem["index"]];

            // Get percentage
            let total = 0;

            for (let i = 0; i < dataset.data.length; i++) {
              total += dataset.data[i];
            }

            const percentage = parseFloat(
              ((value / total) * 100).toFixed(2)
            ).toLocaleString("en-US", { minimumFractionDigits: 2 });

            if (value == -1 && total == value) {
              return "$0.00";
            }

            value = parseFloat(value.toFixed(2)).toLocaleString("en-US", {
              minimumFractionDigits: 2,
            });

            return `$${value} (${percentage}%)`;
          },
        },
        displayColors: false,
      },
    };

    this.renderChart(this.chartData, options);
  },
};
</script>