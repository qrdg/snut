<template>
  <div class="make-col-save">
    <div class="make-col-save-body">
      <header>
        <div class="logo-area header-area">
          <img class="main-logo-text" src="@/assets/logo_text.png" @click="$router.push('/')" alt="text_logo">
        </div>
        <div class="make-col-save-header">
          <common-button 
              ref="makeNoteBtn"
              @click="seperateBtnEvent($event, btn, idx)"
              v-for="(btn, idx) in btnSet"
              width="150" 
              height="40" 
              :buttonName="btn.name"
              background="white"
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
              <div class="circle-btn" style="background: #EECAC6"></div>
            </div>
          <div class="write-with-picture">

            <div class="write-area">
              
              <div class="write-area-with-pic">

                <div class="pic-area" >
                  <div class="img-container" ref="imgContainer">

                    <div class="picture-div">

                    </div>
                  </div>
                  <input class="previous" type="button" value="<" @click.stop="previous()" ref="previous" disabled />
                  <input class="next" type="button" value=">" @click.stop="next()" ref="next" />
                </div>

                <div class="write-div">
                  <input type="text" placeholder="Title:" v-model="collectionData.collectionTitle">
                  
                  <TipTap 
                      ref="textEditor"
                      :isEditable="true" 
                      :toolbar="false" 
                      @sendContents="getTextEditorContents" />
                  
              
                  <textarea name="" id="" cols="30" rows="10" maxlength="500" placeholder="Contents"
                  v-model="collectionData.collectionText"/>
                </div>
              </div>

            </div>


            <div class="hash-tag-div">
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
      </footer>
      
    </div>
  </div>
</template>

<script>
import CommonButton from '@/components/CommonButton.vue';
import MainFooter from '@/components/MainFooter.vue'
import TipTap from '@/components/TextEditor.vue';

export default {
  components: { 
    CommonButton, 
    MainFooter,
    TipTap
    },
  name: "MakeNote",
  data() {
    return {
      btnSet: [{
        id: 1,
        name: '저장',
        src: ''
      },
      {
        id: 2,
        name: '글쓰기 취소',
        src: ''
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
      imgNumberSet: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
      collectionData: {
        email: sessionStorage.getItem('email'),
        nickname: sessionStorage.getItem('nickName'),
        collectionTitle: '',
        collectionText: '',
        curations: this.$store.state.sendToCuration,
        hashtag: []
      },
      receivedData:[],
      imgSlideData: {
        curPos: 0,
        position: 0,
        IMAGE_WIDTH: 500
      }
    }
  },
  methods: {
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
    seperateBtnEvent(e, btn) {
      console.log("btn", btn);
      if(btn.id == 1) {
        this.onSave();
        this.$router.push('/mcol/main');
      } else if (btn.id == 2) {
        console.log("글쓰기 취소를 눌렀다!")
        window.history.back();
      }
    },
    inspectNull() {
      if(this.collectionData.collectionTitle
          && this.collectionData.collectionText != '<p></p>' ) return true;
      else false;
    },
    onSave() {
      const pic = this.collectionData;
        this.$refs.textEditor.sendContents();

        console.log('저장을 눌렀다!')
        if (this.inspectNull()) {
          console.log('Save with Image')
          console.log(pic)
          this.saveHashtag(pic);
          console.log("oseijrogji", pic)

          this.sendDataUseAxios(pic);

        } else {
          console.log('제목 또는 내용에 빈공간이 있습니다.')
        }
        
    },
    getTextEditorContents(contents, imgList) {
      console.log("conteoawjioeijawog", contents)
      this.collectionData.collectionText = contents;
      console.log('receivedEditorContents', imgList)
    },
    sendDataUseAxios(data) {
    const calledAxios = this.$store.state.storedAxios;

      console.log("data >>>>>", data)
      const collectionDTO = {
        email: data.email,
        nickname: data.nickname, 
        collectionTitle: data.collectionTitle,
        collectionText: data.collectionText,
        curations: data.curations,
        hashtag: data.hashtag
      }
      console.log(collectionDTO);
        calledAxios.post('/mcol/mc', collectionDTO )
          .then(res => console.log(res))
          .catch(err => console.log(err));
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
      if(this.imgSlideData.curPos < this.getImageCount - 1 ) {
      this.$refs.previous.removeAttribute("disabled");
      this.imgSlideData.position -= this.imgSlideData.IMAGE_WIDTH;
      this.$refs.imgContainer.style.transform = `translateX(${this.imgSlideData.position}px`;
      this.imgSlideData.curPos += 1;
      }
      if(this.imgSlideData.curPos == this.getImageCount - 1 ) {
        this.$refs.next.setAttribute('disabled', 'true');
      }
    },

    saveHashtag(willSendContents) {
      this.$refs.hashTag.map(tag => {
        if(tag.value.trim()) {
          willSendContents.hashtag.push(tag.value.trim());
        }
      })
    },
    startSave() {
      this.getCurationData();
      console.log("this.receivedData", this.receivedData)
      setTimeout(this.inputImage, 1000);

    },
    inputImage() {
      const pictureDiv = document.querySelector(".picture-div");
      for(let i = 0; i < this.receivedData.length; i++) {
        console.log(this.receivedData[i].imageDTOList.length)

        if(this.receivedData[i].imageDTOList.length) {
            let url = this.receivedData[i].imageDTOList[0].imageURL;
            console.log(this.receivedData[i].imageDTOList[0].imageURL);
            pictureDiv.innerHTML += 
              `<img src="http://localhost:8080/get/img?fileName=${url}" 
                  style="width:500px; height: 500px; object-fit: fill;">`
        }
      }
    },
    getCurationData() {
      const calledAxios = this.$store.state.storedAxios;
        for(let i = 0; i < this.collectionData.curations.length; i++) {
          calledAxios.get('/mcol/store', {
            params: {
              'curationId': this.collectionData.curations[i]
            }
          }).then(res => {
            this.receivedData.push(res.data);
          })
        }
    }
  },
  computed: {
    getImageCount() {
      let count = 0;
      this.receivedData.map(i => {if(i.imageDTOList.length > 0) return count++ });
      return count;
    }
  },
  created() {
    console.log("this.collectionData.curations", this.collectionData.curations);
    console.log("ssss", Array.from(this.$store.getters.getSendToCuration))
    this.startSave();
  },
}
</script>

<style scoped>
.make-col-save-body {
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
.make-col-save-header {
  width: 70%;
  display: flex;
  justify-content: flex-end;
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



.pic-area {
  width: 500px;
  height: 500px;
  position: relative;
  overflow: hidden;
  border-radius: 12px;
}
.img-container {
  width: 500px;
  height: 500px;
  display: flex;
  position: relative;
  transition: all 0.5s;
}
.picture-div {
  display: flex;
  background: #eee;
}
.picture-div img{
  width: 500px;
  height: 500px;
  object-fit: cover;
}
.previous{
  width: 75px;
  height: 75px;
  position: absolute;
  top: 215px;
  left: 20px;
  z-index: 2;
  font-size: 30px;
  background: none;
  border: 1px solid grey;
  border-radius: 75px;
  transition: all 0.5s;
  color: grey;
}
.previous:hover {
  background: white;
  font-weight: 200;
  border: 1px solid black;
  color: grey;
}
.next {
  width: 75px;
  height: 75px;
  position: absolute;
  top: 215px;
  right: 20px;
  z-index: 2;
  font-size: 30px;
  background: none;
  border: 1px solid grey;
  border-radius: 75px;
  transition: all 0.5s;
  color: grey;
}
.next:hover {
  background: white;
  font-weight: 200;
  border: 1px solid black;
  color: grey;
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
.write-div > input {
  width: 350px;
  margin-bottom: 10px;
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


.hash-tag-div {
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 50px;
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