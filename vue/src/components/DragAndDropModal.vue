<template>
  <div class="common-modal" v-if="showBool">
    <div class="modal-bg" @click="closeModal()"></div>
    <div class="modal-area">
      <img class="x-button" src="@/assets/modal/Close-Line.png" alt="x_img" @click="closeModal(imgs)">
      <div class="modal-container">
        <drag-and-drop-image ref="pickImg" @receiveImg="receiveImg" />
      </div>
      <input @click="confirm()" class="confirmBtn" type="button" value="확인">
    </div>
  </div>
</template>

<script>
import DragAndDropImage from './DragAndDropImage.vue';
export default {
  components: { DragAndDropImage },
  name: 'DragAndDropModal',
  data() {
    return {
      showBool: false
    }
  },
  methods: {
    openModal() {
      this.showBool = true;
    },
    closeModal() {
      this.showBool = false;
    },
    confirm() {
      this.$refs.pickImg.sendImg();
      this.closeModal();
    },
    receiveImg(imgs) {
      this.$emit('receiveNoteImg', imgs);
    }
  }
}
</script>

<style scoped>
.common-modal {
  position: fixed;
  left: 0;
  top: 0;
  display: block;
  width: 100%;
  height: 100%;
  z-index: 1;
}
.modal-bg {
  background: rgba(0, 0, 0, 0.3);
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 0;
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
  display: flex;
  flex-direction: column;
  align-items: center;
}
.modal-container::-webkit-scrollbar{
  display: none;
  width: 0
}
.modal-container {
  width: 850px;
  height: 500px;
  margin: 60px 75px 20px 75px;
  overflow: scroll;
}
.confirmBtn {
  width: 100px;
  height: 50px;
  background: white;
  padding: 5px;
  border: 1px solid powderblue;
  border-radius: 6px;
  cursor: pointer;
}
.confirmBtn:active {
  background: #eee;
}

/* 기타 구간 */
.x-button {
  position: absolute;
  top: 35px;
  right: 40px;
}
</style>