<template>
  <b-modal
    id="alertSet"
    ref="modal"
    title="Set price alert"
    @hidden="resetModal"
    no-close-on-backdrop
  >
    <form ref="form" @submit.stop.prevent="handleSetPriceAlert">
      <b-form-group
        :label="'Price per ' + alertCrypto"
        label-for="crypto-price"
      >
        <b-form-input
          id="crypto-price"
          type="number"
          v-model="alertPrice"
          placeholder="0"
        ></b-form-input>
      </b-form-group>

      <p>
        NOTE: Your price alert will be removed when it is reached! You will
        receive an email to notify you.
      </p>

      <b-alert show variant="danger" v-if="error != null">{{ error }}</b-alert>
    </form>

    <template slot="modal-footer">
      <button class="btn btn-custom" @click="handleSetPriceAlert">
        Set Alert
      </button>
    </template>
  </b-modal>
</template>

<script>
export default {
  props: ["alertId", "alertCrypto"],
  data() {
    return {
      alertPrice: 0,
      error: null,
    };
  },
  methods: {
    resetModal() {
      this.alertPrice = 0;
      this.error = null;
    },
    handleSetPriceAlert() {
      this.error = null;

      const _id = parseInt(this.alertId);
      const _price = parseFloat(this.alertPrice);

      if (this.isValidInput(_id, _price)) {
        this.$store
          .dispatch("setPriceAlert", {
            token: this.$session.get("access_token"),
            id: this.alertId,
            alert: this.alertPrice,
          })
          .then((result) => {
            if (!result.success) {
              this.error = result.message;
            } else {
              this.$nextTick(() => {
                this.$bvModal.hide("alertSet");
              });
            }
          });
      }
    },
    isValidInput(_id, _price) {
      if (_id <= 0) {
        this.error = "Crypto missing";
        return false;
      } else if (_price <= 0) {
        this.error = "Price alert be greater than 0 per coin";
        return false;
      }

      return true;
    },
  },
};
</script>