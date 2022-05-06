<template>
  <svg viewBox="0 0 24 24">
    <use
        :xlink:href="require('@/assets/icons/Mail-Cloed-Line.svg') + `#${svg}`"
    ></use>
  </svg>
</template>

<script>
export default {
  name: "Icon-file",
  data() {
    return {
      svg:''
    };
  },
  props: {

          name:{
            type:String,
            required:true
          }
        },
  created() {
    this.svg = require(`@/assets/icon/${this.name}.svg`);
  }




}
</script>

<style scoped>

</style>