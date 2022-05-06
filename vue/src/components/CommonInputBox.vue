<template>
  <input class="main-search-input" 
        type="text" 
        :placeholder="placeholderContent"
        v-model="upToWord"
        @keyup.enter="doSearch(), doSearchCuration()">
</template>

<script>
export default {
  name: 'CommonInputBox',
  props: ['placeholderContent'],
  data() {
    return {
      upToWord: ""
    }
  },
  methods: {
    doSearch() {
      this.$emit('mainDoSearch', this.upToWord);
    },
    doSearchCuration() {
      this.$emit('doSearchCuration', this.upToWord)
    }
  }
}
</script>

<style scoped>
.main-search-input {
    top: 70px;
    left: 702px;
    width: 516px;
    height: 28px;
    padding: 6px 12px;
    border-color: #00000099;
    border-style: solid;
    border-width: 0.5px;
    letter-spacing: 0.00px;
    line-height: normal;
    mix-blend-mode: normal;
    position: absolute;
    resize: none;
    text-align: left;
    background-color: rgb(232, 236, 230);
  font-family: 'Noto-sans KR','Apple SD Gothic Neo', sans-serif ;
  font-size: 16px;
}
.main-search-input::placeholder {
  color: #00000099;
  font-family: 'Noto-sans KR','Apple SD Gothic Neo', sans-serif ;
  font-size: 16px;
font-weight: 600;
}
</style>