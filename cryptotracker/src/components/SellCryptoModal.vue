<template>
  <b-modal id="sell" ref="modal" title="Sell crypto" @hidden="resetModal">
    <form ref="form" @submit.stop.prevent="handleSellSubmit">
      <b-form-group label="Asset" label-for="crypto-select">
        <v-select
          id="crypto-select"
          :options="coingeckoCryptos"
          :reduce="(option) => option.value"
          v-model="crypto"
          disabled
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
      <button class="btn btn-custom" @click="handleSellSubmit">Sell</button>
    </template>
  </b-modal>
</template>

<script>
export default {
  props: ["id", "crypto", "maxAmount", "coingeckoCryptos"],
  data() {
    return {
      price: 0,
      amount: 0,
      error: null,
    };
  },
  methods: {
    resetModal() {
      this.amount = 0;
      this.price = 0;
      this.error = null;
    },
    handleSellSubmit() {
      this.error = null;

      const _amount = parseFloat(this.amount);
      const _maxAmount = parseFloat(this.maxAmount);
      const _price = parseFloat(this.price);

      if (this.isValidInput(_amount, _maxAmount, _price)) {
        this.$store.dispatch("sellCrypto", {
          token: this.$session.get("access_token"),
          id: this.id,
          crypto: this.crypto,
          amount: this.amount,
          price: this.price,
        });

        // CHECK ERROR MESSAGE ON RETURN!!!

        this.$nextTick(() => {
          this.$bvModal.hide("sell");
        });
      }
    },
    isValidInput(_amount, _maxAmount, _price) {
      if (this.crypto == "") {
        this.error = "Crypto missing";
        return false;
      } else if (_amount <= 0) {
        this.error = "Sell amount must be greater than 0";
        return false;
      } else if (_maxAmount <= _amount) {
        this.error = "Sell amount can't be more than " + this.maxAmount;
        return false;
      } else if (_price <= 0) {
        this.error = "Sell price must be greater than 0";
        return false;
      }

      return true;
    },
  },
};
</script>