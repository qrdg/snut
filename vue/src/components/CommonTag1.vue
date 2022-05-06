<template>
  <div class="commonTag">
    <div class="textConfig tagIcon" v-if="!canWrite && !tagName[2]" @click="test()">
      {{ tagName[1] == '' ? '' : "# " + tagName[1] }}
    </div>
    <div class="textConfig tagIcon" v-if="!canWrite && tagName[2]" @click="convertToWrite">
      {{ tagName[1] == '' ? '' : "# " + tagName[1] }}
    </div>
    <div class="textConfig tagIcon" v-if="canWrite" @click="convertToWrite">
      <span>#</span><input 
          class="textConfig" 
          maxlength="8"
          type="text" 
          v-model="inputText" 
          @keyup.enter="saveInputText" />
    </div>
  </div>
</template>

<script>
export default {
  name: "CommonTag",
  props: ['tagName'],
  data() {
    return {
      canWrite: false,
      inputText: ''
    }
  }, 
  methods: {
    convertToWrite() {
      this.canWrite = true;

    },
    saveInputText() {
      // console.log(this.tagName[0])
      // console.log(this.tagName[1])
      this.$emit('saveText', [this.tagName[0], this.inputText]);
      this.inputText = '';
      this.canWrite = false;
    },
    test() {
      console.log(this.tagName[2]);
    }
  }, 
  watch: {
    inputText(e) {
      console.log(e);
    }
  }
}



</script>

<style scoped>
.textConfig {
  font-family: 'Alegreya';
  font-weight: 700;
  color: black;
  font-size: 16px;
}
.tagIcon {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 160px;
  height: 40px;
  /* background-color: rgb(232, 236, 230); */
  border-radius: 20px;
  border: 1px solid black;
  cursor: pointer;
}
.tagIcon span {
  margin-left: 10px;
}
.tagIcon input {
  width: 120px;
  margin-left: 5px;
  text-decoration: none;
  border: none;
}
.tagIcon input:focus {
  outline: none;
}
</style>