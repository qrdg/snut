<template>
  <div class="make-note">
    <div class="make-note-body">
      <header>
        <div class="logo-area header-area">
          <img class="main-logo-text" src="@/assets/logo_text.png" @click="$router.push('/')" alt="text_logo">
        </div>
        <div class="make-note-header">
          <common-button 
              ref="makeNoteBtn"
              @click="seperateBtnEvent($event, btn, idx)"
              v-for="(btn, idx) in btnSet"
              width="150" 
              height="40" 
              :buttonName="btn.name"
              background="white"
              :style="
                      btn.id == 4 && openBool == true ? { background:'lightgrey' } : 
                      btn.id == 5 && openBool == false ? {background: 'lightgrey'} : ''
                      "
              border="none"
              fontSize="16"
              marginRight="20"
              :key="idx" />
        </div>
      </header>
      <main>

        <!-- 글 쓰기 구간 -->
        <div class="write-container">
            <div class="toggleBtn">
              <div class="circle-btn"
                  @click="withPicEvent(idx)"
                  v-for="(btn, idx) in circleBtnSet"
                  :style="{ background: btn.color }"
                  :key="idx"></div>
            </div>
          <div class="write-with-picture">
          
            <div class="write-area" v-if="withPic">
              
              <div class="write-area-with-pic">
                <div class="pictrue-div" @click="openModal()" v-if="inputImg">
                  <p>Click to upload images.</p>
                </div>

                <div class="pictrue-div" v-if="!inputImg">
                  <img 
                      class="pictrue-div-img"
                      v-for="(img, idx) in tempImgList" 
                      :src="img.src" 
                      :alt="'pic'+idx"
                      :key="idx" />
                </div>

                <div class="write-div">
                  <input type="text" placeholder="Title:" v-model="contentsWithPic.curationTitle">
                  <TipTap 
                      ref="textEditor"
                      :isEditable="true" 
                      :toolbar="false" 
                      @sendContents="receivedEditorContentsWithPic" />
                  <textarea name="" id="" cols="30" rows="10" placeholder="Contents"
                  v-model="contentsWithPic.curationText"/>
                </div>
              </div>
                  {{ contentsWithPic.curationText }}

            </div>

            <div class="write-area write-area-nonpic" v-if="!withPic">
              
              <div class="write-area-with-nonpic">

                <div class="emoji-view">
                  <emoji-face 
                      class="emoji-face" 
                      :color="contentsWithNonPic.pickedColor" />
                  <img 
                      class="emoji-expression" 
                      :src="require(`@/assets/face-emoji/emoji${contentsWithNonPic.pickedEmoji}.png`)" 
                      alt="expression">
                </div>
                <div class="emoji-color-container">
                  <div class="picker-box">
                    <p>emoji</p>
                    <div class="picker emoji-picker">
                      <img 
                          :src="require(`@/assets/face-emoji/emoji${imgNo}.png`)" 
                          @click="contentsWithNonPic.pickedEmoji = idx"
                          v-for="(imgNo, idx) in imgNumberSet" :key="idx"
                          alt="emoji_img">
                    </div>
                  </div>
                  <div class="picker-box">
                    <p>
                      color &nbsp;
                      <color-picker @getColors="getColors"/>
                    </p>
                      
                    <div class="picker color-picker">
                      <div class="picked-color" :style="{ background: contentsWithNonPic.pickedColor }"></div>
                        &nbsp; &nbsp; 
                      <p> {{ contentsWithNonPic.pickedColor }}</p>
                    </div>
                  </div>
                </div>

              </div>

              <div class="write-area-nonpic-title">
                <input type="text" placeholder="Title" v-model="contentsWithNonPic.curationTitle">
              </div>

              <div class="write-div nonpic">
                <TipTap ref="textEditor" 
                      :toolbar="true" 
                      isEditable="'true'"
                      @sendContents="receivedEditorContents"/>
              </div>

            </div>


            <div class="hash-tag-div">
              <!-- <input 
                  ref="hashTag"
                  class="hash-tag"
                  type="text"
                  style="width: 130px;"
                  @input="inputAutoWidth($event)"
                  v-model="tag.content" 
                  v-for="(tag, idx) in hashTagSet" 
                  :key="idx"> -->
              <div class="hash-tag"
                    v-for="(tag, idx) in hashTagSet"
                    :key="idx">
                <p>#</p>
                <input 
                    type="text"
                    ref="hashTag"
                    @input="inputAutoWidth($event)"
                    v-model="tag.content"
                    maxlength="14">
              </div>
              
            </div>
            
          </div>

        </div>

      </main>
      
      <footer>
        <main-footer/>
        <transition name="fade"> 
          <drag-and-drop-modal ref="dndModal" @receiveNoteImg="receiveNoteImg" />
        </transition>
      </footer>
      
    </div>
  </div>
</template>

<script>
import CommonButton from '@/components/CommonButton.vue';
import MainFooter from '@/components/MainFooter.vue'
import TipTap from '@/components/TextEditor.vue'
import DragAndDropModal from '@/components/DragAndDropModal.vue'
import ColorPicker from '@/components/ColorPicker.vue'
import EmojiFace from '../components/EmojiFace.vue';

export default {
  components: { 
    CommonButton, 
    MainFooter, 
    TipTap, 
    DragAndDropModal,
    ColorPicker,
    EmojiFace
    },
  name: "MakeNote",
  data() {
    return {
      circleBtnSet: [
        {
          id: 1, 
          color: '#EECAC6'
        },
        {
          id: 2, 
          color: '#F3D675'
        }
      ],
      btnSet: [{
        id: 1,
        name: '저장',
      },
      {
        id: 2,
        name: '글쓰기 취소',
      },
      {
        id: 3,
        name: '임시저장',
      },
      {
        id: 4,
        name: '공개',
      },
      {
        id: 5,
        name: '비공개',
      }],
      hashTagSet: [
        {
          id: 1,
          content: ''
        },
        {
          id: 2,
          content: ''
        },
        {
          id: 3,
          content: ''
        },
      ],
      tempImgList: [],
      openBool: true,
      withPic: true,
      imgNumberSet: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
      contentsWithPic: {
        email: sessionStorage.getItem('email'),
        nickname: sessionStorage.getItem('nickName'),
        curationTitle: '',
        curationText: '',
        imageDTOList: [],
        hashtag: [],
        open: true,
        cuCo: 'Curation'
      },
      contentsWithNonPic: {
        email: sessionStorage.getItem('email'),
        nickname: sessionStorage.getItem('nickName'),
        curationTitle: '',
        curationText: '',
        pickedEmoji: 0,
        pickedColor: 'rgb(240, 230, 190)',
        hashtag: [],
        open: true,
        imageDTOList:[],
        cuCo: 'Curation'
      },
      inputImg: true
    }
  },
  methods: {
    openModal() {
      this.$refs.dndModal.openModal();
    },
    inputAutoWidth(e) {
      const val = e.target.value;
      // const kor = /^[ㄱ-ㅎ|가-힣|ㅏ-ㅣ|0-9|]+$/;
      const eng = /^[a-z|A-Z|0-9|]+$/;

      if(eng.test(val)) {
        // console.log("eng", eng.test(val));
        e.target.style.width = 120 + (6 * val.length)+'px';
      } else {
        // console.log("kor", kor.test(val));
        e.target.style.width = 100 + (15 * val.length)+'px';
      }
      if(val.trim() == '') {
        e.target.style.width = 140+'px';
      }
    },
    withPicEvent(id) {
      id == 1 ? this.withPic = false : this.withPic = true;
    },
    seperateBtnEvent(e, btn) {
      console.log("btn", btn);
      if(btn.id == 1) {
        this.onSave();

        } else if (btn.id == 2) {
          console.log("글쓰기 취소를 눌렀다!")
          window.history.back();

      } else if (btn.id == 3) {
        console.log(this.contentsWithPic)
        console.log(this.contentsWithNonPic)
        console.log("임시저장을 눌렀다!")

      } else { 
        this.openBoolToggle(e, btn.id);
      }
    },
    inspectNull() {
      if(this.contentsWithPic.curationTitle
          && this.contentsWithPic.curationText
          && this.contentsWithPic.imageDTOList.length ) return true;
      else false;
    },
    onSave() {
      const pic = this.contentsWithPic;
      const nonPic = this.contentsWithNonPic;

      this.$refs.textEditor.sendContents();
        console.log('저장을 눌렀다!')
        if (this.withPic == true && this.inspectNull()) {
          console.log('Save with Image')
          console.log(pic)
          console.log(pic.imageDTOList);

          this.saveHashtag(pic);
          this.sendDataUseAxios(pic);
          this.$router.push("/mcol/main");

        } else if (this.withPic == false) {

            if (nonPic.curationTitle
                && nonPic.curationText != '<p></p>') {
                  console.log('Save with No Image')
                  console.log(nonPic)
                  console.log("this.tempImgList", this.tempImgList);
                  nonPic.imageDTOList = this.tempImgList;

                  this.saveHashtag(nonPic);
                  this.sendDataUseAxios(nonPic);
                  this.$router.push("/mcol/main");
                }

            } else {
              console.log('제목 또는 내용에 빈공간이 있습니다.')
            }
        
    },
    sendDataUseAxios(data) {
    const calledAxios = this.$store.state.storedAxios;

        console.log("data >>>>>", data)
        const obj = {
          email: data.email,
          curationTitle: data.curationTitle,
          curationText: data.curationText,
          imageDTOList: data.imageDTOList[0],
          hashtag: data.hashtag,
          pickedEmoji: data.pickedEmoji,
          pickedColor: data.pickedColor,
          open: data.open
        }
          calledAxios.post('/mcol/note/makenote/picture', obj)
            .then(res => console.log(res))
            .catch(err => console.log(err));
    },
    saveHashtag(willSendContents) {
      this.$refs.hashTag.map(tag => {
        if(tag.value.trim()) {
          willSendContents.hashtag.push(tag.value.trim());
        }
      })
    },
    sendImage(contentList, imgList) {
      console.log("imgList >>>> ", imgList)
      console.log("imgList >>>> ", contentList)

      if(imgList.length !=  0) {
        const a = this.$store.state.storedAxios;

        let frm = new FormData();
        for(let i = 0; i < imgList.length; i++) {
            frm.append("imgList", imgList[i]);
        }

          a.post('/uploadimg', frm, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => {
          contentList.push(res.data);
          console.log(res.data);
        }).catch(err => {
          console.log(err);
        });
      }

    },
    receivedEditorContents(contents, imgList) {
      this.contentsWithNonPic.curationText = contents;
      console.log('receivedEditorContents', imgList)
      // this.tempImgList = imgList;
      // this.sendImage(this.contentsWithNonPic.imageDTOList, imgList);
    },
    receivedEditorContentsWithPic(contents, imgList) {
      console.log("conteoawjioeijawog", contents)
      this.contentsWithPic.curationText = contents;
      console.log('receivedEditorContents', imgList)
    },
    openBoolToggle(e, idx) {
      if (idx == 4) {
        this.openBool = true;
        this.contentsWithPic.open = this.openBool;
        this.contentsWithNonPic.open = this.openBool;

      } else if (idx == 5) {
        this.openBool = false;
        this.contentsWithPic.open = this.openBool;
        this.contentsWithNonPic.open = this.openBool;
      }
    },
    receiveNoteImg(imgList) {
      if(imgList.length != 0) {
        this.sendImage(this.contentsWithPic.imageDTOList, imgList);
        this.tempImgList = imgList;
        this.inputImg = false;
      }
    },
    getColors(color) {
      this.contentsWithNonPic.pickedColor = color;
    }
  }
}
</script>

<style scoped>
.make-note-body {
  max-width: 1200px;
  min-width: 1200px;
  width: 100vw;
  height: 100vh;
  /* background: lightcoral; */
  margin: 0 auto;
}

/* header 구간 */
header {
  width: 100%;
  height: 200px;
  /* background: lightblue; */
  position: relative;
  display: flex;
  justify-content: flex-end;
}
.header-area {
  width: 30%;
}
.logo-text {
  width: 113px;
  height: 39px;
  margin-top: 60px;
  /* position: absolute; */
  left: 0;
  top: 0;
}
.make-note-header {
  width: 70%;
  display: flex;
  justify-content: center;
  margin-top: 60px;
}
.main-logo-text {
  width: 113px;
  height: 39px;
  margin-top: 60px;
  /* position: absolute; */
  left: 0;
  top: 0;
  cursor: pointer;
}

/* main 구간 */
.write-container {
  max-width: 1200px;
  /* height: 800px; */
  padding: 0 50px;
  background: #F6F6F6;
  border: 1px solid black;
  border-radius: 12px;
}
.write-with-picture {
  width: 100%;
}
.toggleBtn {
  display: flex;
  height: 120px;
}
.circle-btn {
  width: 30px;
  height: 30px;
  border-radius: 15px;
  margin-top: 40px;
  margin-right: 60px;
  cursor: pointer;
}
.write-area {
  width: 100%;
  /* height: 800px; */
}
.write-area-with-pic {
  display: flex;
  justify-content: space-between;
}
.write-area-nonpic {
  width: 900px;
  height: 970px;
  padding: 0 100px;
}

/* 사진 구간 */
.pictrue-div {
  width: 460px;
  height: 460px;
  padding: 20px;
  overflow: scroll;
  background: #eee;
}
.pictrue-div::-webkit-scrollbar{
  display: none;
  width: 0
}
.pictrue-div > p {
  width: 100%;
  height: calc(100%-50);
  text-align: center;
  font-size: 30px;
  padding-top: 50px;
}
.pictrue-div-img{
  width: 33%;
  height: 33%;
  object-fit: fill;;
}

/* 텍스트 구간 */
.write-div {
  width: 420px;
  height: 400px;
  border-radius: 12px;
  padding: 50px 40px;
  background: #FFFFFF;
  display: flex;
  flex-direction: column;
}
.write-div * {
  background: none;
  border: none;
  font-size: 16px;
  font-weight:normal;
}
.write-div *:focus {
  outline: none;
}
.write-div *::placeholder {
  color: #666666;
}
.write-div > input{
  padding-bottom: 5px;
  border-bottom: 1px solid #C4C4C4;
}
.write-div textarea {
  margin-top: 50px;
  margin-bottom: 20px;
  height: 380px;
  resize: none;
}


/* No Picture 구간 */
.write-area-with-nonpic {
  width: 100%;
  height: 280px;
  display: flex;  
}
.emoji-view {
  width: 300px;
  height: 100%;
  margin-right: 50px;
  position: relative;  
}
.emoji-face {
  width: 200px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-45%, -50%);
}
.emoji-expression {
  width: 80px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.emoji-color-container {
  width: 660px;
  display: flex;
  justify-content: space-between;
  background: white;
  padding: 15px 25px 5px 25px;
  border: 1px solid black;
  border-radius: 12px;
}
.emoji-color-container  p{
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}
.picker-box {
  width: 260px;
}
.picker-box > p {
  display: flex;
  align-items: center;
}
.picker {
  width: calc(100%-40px);
  height: 160px;
  padding:  20px;
  border: 1px solid black;
  overflow: scroll;
}
.picker::-webkit-scrollbar{
  display: none;
  width: 0
}
.emoji-picker {
  display: flex;
  flex-wrap: wrap;
}
.emoji-picker img {
  width: 40px;
  padding: 15px;
  object-fit: contain;
}
.emoji-picker img:hover {
  cursor: pointer;
  border: 1px solid black;
  padding: 15px;
}
/* .emoji-face {

} */
.color-picker {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
}
.picked-color {
  width: 30px;
  height: 30px;
}
.color-picker p {
  font-size: 16px;
}
.write-area-nonpic-title {
  width: 100%;
  height: 130px;
  display: flex;
  align-items: center;
}
.write-area-nonpic-title input {
  width: 100%;
  height: 50px;
  background: none;
  border: none;
  border-bottom: 2px solid #C4C4C4;
  font-size: 30px;
  font-weight: bold;
}
.write-area-nonpic-title input:focus {
  outline: none;
}


.nonpic {
  width: 860px;
  height: 500px;
  /* background: lightcoral; */
  border-radius: 12px;
  padding: 20px 20px;
}
.hash-tag-div {
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 30px;
  margin-bottom: 100px;
}
.hash-tag {
  display: flex;
  align-items: center;
  background: white;
  border: 1px solid black;
  border-radius: 20px;
  margin: 0 10px;
  padding: 0 20px;
  font-size: 20px;
  font-weight: bold;
  text-align: center;
}
.hash-tag input {
  width: 130px;
  height: 40px;
  border: none;
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  /* background: red; */
  margin: 0 5px;
}
.hash-tag input:focus {
  outline: none;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity .2s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>