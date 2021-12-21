<template>
  <div>
    <h2 class="absolute-center text-center">
      <span class="info-value font-weight-normal font-size-2-half-rem">{{
        "$" + value
      }}</span>
      <br />
      <span class="info-sub-title font-size-1-quarter-rem">{{
        count + " Assets"
      }}</span>
    </h2>
    <p class="position-absolute refresh-position info-title">
      Refresh in: {{ refreshTime }}
    </p>
  </div>
</template>

<script>
export default {
  props: ["value", "count", "refreshTime"],
};
</script>