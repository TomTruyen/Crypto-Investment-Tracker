<template>
  <b-modal
    id="alertDelete"
    ref="modal"
    title="Delete price alert"
    @hidden="resetModal"
    no-close-on-backdrop
  >
    <form ref="form" @submit.stop.prevent="handleDeletePriceAlert">
      <p>
        NOTE: Your price alert of <b>${{ getAlertPriceAsCurrency }}</b> per
        <b>{{ alertCrypto }}</b> will be deleted!
      </p>

      <b-alert show variant="danger" v-if="error != null">{{ error }}</b-alert>
    </form>

    <template slot="modal-footer">
      <button class="btn btn-custom" @click="handleDeletePriceAlert">
        Delete Alert
      </button>
    </template>
  </b-modal>
</template>

<script>
import Utils from "@/utils/Utils.js";

export default {
  props: ["alertId", "alertPrice", "alertCrypto"],
  data() {
    return {
      error: null,
    };
  },
  computed: {
    getAlertPriceAsCurrency() {
      const _alertPrice = parseFloat(this.alertPrice);

      return Utils.numberWithCommas(_alertPrice, 2, true, true);
    },
  },
  methods: {
    resetModal() {
      this.error = null;
    },
    handleDeletePriceAlert() {
      this.error = null;

      const _id = parseInt(this.alertId);

      if (this.isValidInput(_id)) {
        this.$store
          .dispatch("deletePriceAlert", {
            token: this.$session.get("access_token"),
            id: _id,
          })
          .then((result) => {
            if (!result.success) {
              this.error = result.message;
            } else {
              this.$nextTick(() => {
                this.$bvModal.hide("alertDelete");
              });
            }
          });
      }
    },
    isValidInput(_id) {
      if (_id <= 0) {
        this.error = "Crypto missing";
        return false;
      }

      return true;
    },
  },
};
</script>