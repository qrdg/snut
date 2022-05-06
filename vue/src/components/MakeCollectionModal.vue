<template>
  <div class="make-col-modal" v-if="showBool">
    <div class="modal-bg" @click="closeModal()"></div>
    <div class="modal-area">
      <img class="x-button" src="@/assets/modal/Close-Line.png" alt="x_img" @click="closeModal()">
      <div class="modal-container">

        <!-- modal-header 구간 -->
        <div class="modal-header">
          <div class="modal-hashTag">
            <common-tag 
                v-for="(tag, idx) in colCuData.hashTag" 
                width="150" 
                height="40"
                marginRight="15"
                :tagName="tag" 
                :key="idx" />
          </div>
          <div class="modal-iconSet">
            <img src="@/assets/modal/Like-Line.png" alt="like_img">
            <img src="@/assets/modal/Pin-Line.png" alt="pin_img">
            <img src="@/assets/modal/Share-Line.png" alt="share_img">
          </div>
        </div>

        <!-- modal-body 구간 -->
        <div class="modal-body">

          <!-- modal 사진 구간 -->
          <div class="modal-pic" 
              v-if="(sampleImg.length > 0)">
            <div class="img-container" 
                style="{cursor: pointer}" ref="imgContainer">
              <img
                v-for="(name, idx) in sampleImg"
                :src="require(`@/assets/sample/img_${name}.jpg`)"
                alt="cu_img"
                :key="idx" />
            </div>
            <input class="previous" type="button" value="<" @click.stop="previous()" ref="previous" disabled />
            <input class="next" type="button" value=">" @click.stop="next()" ref="next" />
          </div>

          <!-- modal 사진+글 구간 -->
          <div class="modal-content-pic">
            <p><b>{{ colCuData.title }}</b></p>
            <p><b>{{ colCuData.nickName }}</b></p>
            <p>{{ colCuData.content }}</p>
          </div>

        </div>

      </div>
    </div>
  </div>
</template>

<script>
import CommonTag from '@/components/CommonTag.vue'

export default {
  name: 'MakeCollectionModal',
  components: {
    CommonTag
  },
  data() {
    return {
      showBool: false,
      colCuData: {},
      sampleImg: [1, 2, 3 ,4],
      imgSlideData: {
        curPos: 0,
        position: 0,
        IMAGE_WIDTH: 400
      }
    }
  },
  methods: {
    openModal() {
      this.showBool = true;
    },
    closeModal() {
      this.imgSlideData.curPos = 0;
      this.imgSlideData.position = 0;
      this.showBool = false;
    },
    previous() {
      if(this.imgSlideData.curPos > 0) {
      this.$refs.next.removeAttribute("disabled");
      this.imgSlideData.position += this.imgSlideData.IMAGE_WIDTH;
      this.$refs.imgContainer.style.transform = `translateX(${this.imgSlideData.position}px`;
      this.imgSlideData.curPos -= 1;
      }
      if(this.imgSlideData.curPos == 0) {
        this.$refs.previous.setAttribute('disabled', 'true');
      }
    },
    next() {
      if(this.imgSlideData.curPos < this.sampleImg.length - 1 ) {
      this.$refs.previous.removeAttribute("disabled");
      this.imgSlideData.position -= this.imgSlideData.IMAGE_WIDTH;
      this.$refs.imgContainer.style.transform = `translateX(${this.imgSlideData.position}px`;
      this.imgSlideData.curPos += 1;
      }
      if(this.imgSlideData.curPos == this.sampleImg.length - 1 ) {
        this.$refs.next.setAttribute('disabled', 'true');
      }
    }
  }
}
</script>

<style scoped>
.make-col-modal {
  position: fixed;
  left: 0;
  top: 0;
  display: block;
  width: 100%;
  height: 100%;
  z-index: 4;
}
.modal-bg {
  background: rgba(0, 0, 0, 0.3);
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1;
}
.modal-area {
  width: 1000px;
  height: 650px;
  background: white;
  position: fixed;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  -moz-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  -o-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  z-index: 10;
}
.modal-container {
  width: 850px;
  height: 500px;
  margin: 60px 75px 90px 75px;
}


/* modal header 구간 */
.modal-header {
  width: 100%;
  height: 100px;
  /* background: gray; */
  display: flex;
}
.modal-hashTag {
  width: 590px;
  height: 100%;
  margin-top: 5px;
  margin-right: 20px;
  display: flex;
  justify-content: flex-end;
  /* background: lightyellow; */
}
.modal-iconSet {
  width: 260px;
  height: 100%;
  /* background: lightcoral; */

}


/* modal body 구간 */
.modal-body {
  width: 100%;
  height: 400px;
  display: flex;
  justify-content:space-between ;
}


/* modal-pic 구간 */
.modal-pic {
  overflow: hidden;
  position: relative;
  z-index: 2;
}
.img-container {
  width: 400px;
  height: 400px;
  display: flex;
  /* flex-direction: row-reverse; */
  position: relative;
  transition: all 0.5s;
  z-index: 1;
}
.modal-pic img {
  width: 100%;
  height: 100%;
  object-fit:fill;
}
.previous {
  width: 50px;
  height: 50px;
  position: absolute;
  top: 175px;
  left: 20px;
  z-index: 2;
}
.next {
  width: 50px;
  height: 50px;
  position: absolute;
  top: 175px;
  right: 20px;
  z-index: 2;
}


/* modal-content 구간 */
.modal-content-pic {
  width: calc(400px - (20px * 2));
  height: calc(400px - (45px * 2));
  padding: 45px 20px;
  border: 1px solid black;
  border-radius: 12px;
  overflow: scroll;
}
.modal-content-nonPic {
  width: 100%;
  height: 100%;
  padding: 45px 20px;
  border: 1px solid black;
  border-radius: 12px;
  overflow: scroll;
}

/* 기타 구간 */
.x-button {
  position: absolute;
  top: 35px;
  right: 40px;
}
.modalBodyContents * {
  font-size: 20px;
}
</style>