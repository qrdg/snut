<template>
  <div class="inputBox">
    <input 
        type="text" 
        @keyup.enter="doSearch"
        @input="inputWord"
        v-model="searchWord"
        :placeholder="placeholder"
        :style="{ width: width+'vw', height: height+'px' }">
  </div>
</template>

<script>
export default {
  name: "inputBox",
  props: ['width', 'height', 'placeholder'],
  data() {
    return {
      searchWord: ''
    }
  },
  methods: {
    doSearch() {
      console.log("inputBox >> ", this.searchWord)
      let word = this.searchWord;
      this.clearWord();
      if(word != '') this.$emit('doSearch', word);
    },
    clearWord() {
      this.searchWord = '';
    },
    inputWord(e) {
      this.searchWord = e.target.value;
    }
  }
}
</script>

<style scoped>
input {
  max-width: 376px;
  min-width: 300px;
  padding-left: 10px;
  background-color: #E8ECE6;
  border-color: #4F4F4F;
  border-style: solid;
}
input:focus {
  outline: none;
}
input::placeholder {
  color: #00000099;
  font-size: 16px;
}
</style>