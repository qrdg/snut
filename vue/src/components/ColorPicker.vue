<template>
  <color-input v-model="colors" @change="getColors" />
</template>

<script>
import ColorInput from 'vue-color-input'

export default {
  components: {
    ColorInput
  },
  data() {
    return {
      colors: "rgb(240, 230, 190)"
    }
  },
  methods: {
    getColors() {
      this.$emit('getColors', this.colors);
    }
  }
}
</script>

<style>
.color-input.user .box {
    /* make clickable box a 100x100 circle */
    width: 20px;
    height: 20px;
    border-radius: 50px;
}
</style>