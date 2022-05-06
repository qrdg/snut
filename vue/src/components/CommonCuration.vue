<template>
  <div class="collection" @mouseover="!selectMode && inCuration()" @mouseleave="outCuration()">
      <div :class="cuSelect">
        <emoji-face v-if="info.pickedColor" class="emoji-face" width="180" height="180" :color="info.pickedColor" />
        <img :src="inputImage(info)" 
        :style="info.pickedColor && 
          { background: 'none', 
            width: 80+'px', 
            height: 80+'px',
            position: 'absolute',
            'z-index': 1,
            top: 50+'px',
            left: 44+'px'}" 
        alt="sample_img">
      </div>
      <div class="check-icon" v-if="selected"></div>
      <div class="text1" v-if="hoverBool && (!storeBool || !delColBoolean || !loginBool || selectMode)">
        <p>{{ '#'+info.hashtag[0] }}</p>
        <p>{{ '#'+info.hashtag[1] }}</p>
        <p>{{ '#'+info.hashtag[2] }}</p>
        <p>{{ info.modDate }}</p>
        <!-- <p>{{ info.cuCo }}</p> -->
      </div>
      <div class="text1" v-if="storeBool && delColBoolean && loginBool && !selectMode">
        <button @click.stop="deleteCol()">삭제</button>
        <!-- <button @click.stop="shareCol()">공유</button> -->
      </div>

  </div>
</template>

<script>
import EmojiFace from './EmojiFace.vue';

export default {
  name: "CommonCuration",
  components: {
    EmojiFace
  },
  props: ['info', 'id', 'delColBoolean', 'loginBool', 'selectMode'],
  data() {
    return {
      cuSelect: 'cu-img' + this.id,
      
      // hashTag들만 뜨게 만들기
      hoverBool: false,

      // 내꺼가 아닌 다른 사람의 컬렉션을 보기
      storeBool: false,

      // 클릭했을 때 체크버튼 만들기
      selected: false,
    }
  },
  methods: {
    inCuration() {
      this.hoverBool = true;
      this.storeBool = true;
      document.querySelector('.'+this.cuSelect).classList.add('lowerBrightness');
    },
    outCuration() {
      this.hoverBool = false;
      this.storeBool = false;
      document.querySelector('.'+this.cuSelect).classList.remove('lowerBrightness');
    },
    deleteCol() {
      console.log('CommonCuration', this.id);
      this.$emit('deleteCol', this.id, this.info.curationNo);
    },
    shareCol() {
      console.log("공유 버튼을 눌렀다!");
    },
    selectedMethod() {
      this.selected == true ? this.selected = false : this.selected = true;
    },
    inputImage(info) {
      // console.log(info.imageDTOList[0])
      if(info.imageDTOList.length > 0) {
        const url = info.imageDTOList[0].thumbnailURL;
        return `http://localhost:8080/get/img?fileName=${url}`;
      }
      const emojiNo = info.pickedEmoji;
      return require(`@/assets/face-emoji/emoji${emojiNo}.png`)
    },
    
  },
  created() {
    // console.log(this.info)
  }
}
</script>

<style scoped>
.collection {
  position: relative;
}
img {
  width: 180px;
  height: 180px;
  background: white;
  object-fit: fill;
  /* margin-right: 0px; */
}
.check-icon {
  display: flex;
  width: 180px;
  height: 180px;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, .7);
}
.text1 {
  width: 180px;
  height: 180px;
  position: absolute;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  top: 0;
  left: 0;
  opacity: 100%;
}
.text1 p {
  color: white;
  font-weight: bold;
  position: relative;
}
.text1 button {
  width: 70px;
  height: 35px;
  margin: 10px;
  background: #DC8552;
  color: white;
  border: none;
  border-radius: 100px;
  cursor: pointer;
}
.lowerBrightness {
  transition: all .3s;
  filter: brightness(60%);
}
</style>