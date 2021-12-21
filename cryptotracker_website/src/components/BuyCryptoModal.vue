<template>
  <b-modal
    id="buy"
    ref="modal"
    title="Buy crypto"
    @show="resetModal"
    @hidden="resetModal"
    no-close-on-backdrop
  >
    <form ref="form" @submit.stop.prevent="handleBuySubmit">
      <b-form-group label="Asset" label-for="crypto-select">
        <v-select
          id="crypto-select"
          :options="coingeckoCryptos"
          :reduce="(option) => option.value"
          v-model="crypto"
        >
          <template slot="option" slot-scope="option">
            <img width="32" height="32" :src="option.img" />
            <div class="spacer"></div>
            {{ option.label }}
          </template>
        </v-select>
      </b-form-group>
      <b-form-group label="Amount" label-for="crypto-amount">
        <b-form-input
          id="crypto-amount"
          type="number"
          v-model="amount"
          placeholder="0"
        ></b-form-input>
      </b-form-group>
      <b-form-group label="Price per coin" label-for="crypto-price">
        <b-form-input
          id="crypto-price"
          type="number"
          v-model="price"
          placeholder="0"
        ></b-form-input>
      </b-form-group>

      <b-alert show variant="danger" v-if="error != null">{{ error }}</b-alert>
    </form>

    <template slot="modal-footer">
      <button class="btn btn-custom" @click="handleBuySubmit">Buy</button>
    </template>
  </b-modal>
</template>

<script>
export default {
  props: ["coingeckoCryptos"],
  data() {
    return {
      crypto: "",
      amount: 0,
      price: 0,
      error: null,
    };
  },
  methods: {
    resetModal() {
      this.crypto = "";
      this.amount = 0;
      this.price = 0;
      this.error = null;
    },
    handleBuySubmit() {
      this.error = null;

      const _amount = parseFloat(this.amount);
      const _price = parseFloat(this.price);

      if (this.isValidInput(_amount, _price)) {
        this.$store
          .dispatch("buyCrypto", {
            token: this.$session.get("access_token"),
            crypto: this.crypto,
            amount: _amount,
            price: _price,
          })
          .then((result) => {
            if (!result.success) {
              this.error = result.message;
            } else {
              this.$nextTick(() => {
                this.$bvModal.hide("buy");
              });
            }
          });
      }
    },
    isValidInput(_amount, _price) {
      if (this.crypto == "") {
        this.error = "Crypto missing";
        return false;
      } else if (_amount <= 0) {
        this.error = "Buy amount must be greater than 0";
        return false;
      } else if (_price <= 0) {
        this.error = "Buy price must be greater than 0";
        return false;
      }

      return true;
    },
  },
};
</script>