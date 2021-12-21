<template>
  <b-modal
    id="info"
    ref="modal"
    :title="info.title"
    v-if="info.item != null"
    :hide-footer="true"
    no-close-on-backdrop
  >
    <b-row>
      <b-col class="info-chapter"
        ><caption>
          {{
            info.item.name
          }}
          Price Today
        </caption></b-col
      >
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">{{ info.item.name }} Price</b-col>
      <b-col class="text-right my-auto info-value">{{
        info.item.details.getPriceDollar()
      }}</b-col>
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">Price Change (24h)</b-col>
      <b-col class="text-right my-auto">
        <b-row>
          <b-col class="info-value">{{
            info.item.details.getPriceChangeDollar()
          }}</b-col>
        </b-row>
        <b-row>
          <b-col
            :class="
              info.item.details['price_change_24h'] >= 0
                ? 'info-sub-value-up'
                : 'info-sub-value-down'
            "
            >{{
              info.item.details["price_change_24h"] >= 0
                ? "&#9650;"
                : "&#9660;"
            }}{{ info.item.details.getPriceChangePercentage() }}</b-col
          >
        </b-row>
      </b-col>
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">24h Low / 24h High</b-col>
      <b-col class="text-right my-auto info-value"
        >{{ info.item.details.getLow24hDollar() }} /
        {{ info.item.details.getHigh24hDollar() }}</b-col
      >
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">Trading Volume (24h)</b-col>
      <b-col class="text-right my-auto info-value">{{
        info.item.details.getVolume24hDollar()
      }}</b-col>
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">Market Rank</b-col>
      <b-col class="text-right my-auto info-value"
        >#{{ info.item.details.rank }}</b-col
      >
    </b-row>
    <div class="info-spacing"></div>
    <b-row>
      <b-col class="info-chapter"
        ><caption>
          {{
            info.item.name
          }}
          Market Cap
        </caption></b-col
      >
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">Market Cap</b-col>
      <b-col class="text-right my-auto">
        <b-row>
          <b-col class="info-value">{{
            info.item.details.getMarketCapDollar()
          }}</b-col>
        </b-row>
        <b-row>
          <b-col
            :class="
              info.item.details['market_cap_percent_change_24h'] >= 0
                ? 'info-sub-value-up'
                : 'info-sub-value-down'
            "
            >{{
              info.item.details["market_cap_percent_change_24h"] >= 0
                ? "&#9650;"
                : "&#9660;"
            }}{{ info.item.details.getMarketCapPercentage() }}</b-col
          >
        </b-row>
      </b-col>
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">Fully Diluted Market Cap</b-col>
      <b-col class="text-right my-auto">
        <b-row>
          <b-col class="info-value">{{
            info.item.details.getFullyDilutedMarketCapDollar()
          }}</b-col>
        </b-row>
        <b-row>
          <b-col
            :class="
              info.item.details['market_cap_percent_change_24h'] >= 0
                ? 'info-sub-value-up'
                : 'info-sub-value-down'
            "
            >{{
              info.item.details["market_cap_percent_change_24h"] >= 0
                ? "&#9650;"
                : "&#9660;"
            }}{{ info.item.details.getMarketCapPercentage() }}</b-col
          >
        </b-row>
      </b-col>
    </b-row>
    <div class="info-spacing"></div>
    <b-row>
      <b-col class="info-chapter"
        ><caption>
          {{
            info.item.name
          }}
          Price History
        </caption></b-col
      >
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">24h Price Change (Percentage)</b-col>
      <b-col
        :class="
          info.item.details['price_percent_change_24h'] >= 0
            ? 'text-right my-auto info-value up'
            : 'text-right my-auto info-value down'
        "
        >{{
          info.item.details["price_percent_change_24h"] >= 0
            ? "&#9650;"
            : "&#9660;"
        }}{{ info.item.details.getPriceChangePercentage() }}</b-col
      >
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">7d Price Change (Percentage)</b-col>
      <b-col
        :class="
          info.item.details['price_percent_change_7d'] >= 0
            ? 'text-right my-auto info-value up'
            : 'text-right my-auto info-value down'
        "
        >{{
          info.item.details["price_percent_change_7d"] >= 0
            ? "&#9650;"
            : "&#9660;"
        }}{{ info.item.details.getPriceChange7dPercentage() }}</b-col
      >
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">30d Price Change (Percentage)</b-col>
      <b-col
        :class="
          info.item.details['price_percent_change_30d'] >= 0
            ? 'text-right my-auto info-value up'
            : 'text-right my-auto info-value down'
        "
        >{{
          info.item.details["price_percent_change_30d"] >= 0
            ? "&#9650;"
            : "&#9660;"
        }}{{ info.item.details.getPriceChange30dPercentage() }}</b-col
      >
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">1y Price Change (Percentage)</b-col>
      <b-col
        :class="
          info.item.details['price_percent_change_1y'] >= 0
            ? 'text-right my-auto info-value up'
            : 'text-right my-auto info-value down'
        "
        >{{
          info.item.details["price_percent_change_1y"] >= 0
            ? "&#9650;"
            : "&#9660;"
        }}{{ info.item.details.getPriceChange1yPercentage() }}</b-col
      >
    </b-row>
    <hr />
    <b-row>
      <b-col>
        <b-row class="my-auto">
          <b-col class="info-title">All Time High</b-col>
        </b-row>
        <b-row>
          <b-col class="info-sub-title">{{
            info.item.details.getAllTimeHighDate()
          }}</b-col>
        </b-row>
      </b-col>
      <b-col class="text-right my-auto">
        <b-row>
          <b-col class="info-value">{{
            info.item.details.getAllTimeHighDollar()
          }}</b-col>
        </b-row>
        <b-row>
          <b-col
            :class="
              info.item.details['allTimeHighPercentage'] >= 0
                ? 'info-sub-value-up'
                : 'info-sub-value-down'
            "
            >{{
              info.item.details["allTimeHighPercentage"] >= 0
                ? "&#9650;"
                : "&#9660;"
            }}{{ info.item.details.getAllTimeHighPercentage() }}</b-col
          >
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
          <b-col class="info-sub-title">{{
            info.item.details.getAllTimeLowDate()
          }}</b-col>
        </b-row>
      </b-col>
      <b-col class="text-right my-auto">
        <b-row>
          <b-col class="info-value">{{
            info.item.details.getAllTimeLowDollar()
          }}</b-col>
        </b-row>
        <b-row>
          <b-col
            :class="
              info.item.details['allTimeLowPercentage'] >= 0
                ? 'info-sub-value-up'
                : 'info-sub-value-down'
            "
            >{{
              info.item.details["allTimeLowPercentage"] >= 0
                ? "&#9650;"
                : "&#9660;"
            }}{{ info.item.details.getAllTimeLowPercentage() }}</b-col
          >
        </b-row>
      </b-col>
    </b-row>
    <div class="info-spacing"></div>
    <b-row>
      <b-col class="info-chapter"
        ><caption>
          {{
            info.item.name
          }}
          Supply
        </caption></b-col
      >
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">Circulating Supply</b-col>
      <b-col class="text-right my-auto info-value">{{
        info.item.details.getCirculatingSupply()
      }}</b-col>
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">Total Supply</b-col>
      <b-col class="text-right my-auto info-value">{{
        info.item.details.getTotalSupply()
      }}</b-col>
    </b-row>
    <hr />
    <b-row>
      <b-col class="info-title my-auto">Max Supply</b-col>
      <b-col class="text-right my-auto info-value">{{
        info.item.details.getMaxSupply()
      }}</b-col>
    </b-row>
  </b-modal>
</template>

<script>
export default {
  props: ["info"],
};
</script>