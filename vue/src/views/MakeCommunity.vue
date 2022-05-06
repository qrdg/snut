<template>
  <div class="make-community">
    <div class="make-community-body">
      <header>
        <div class="logo-area header-area">
          <img class="main-logo-text" src="@/assets/logo_text.png" @click="$router.push('/')" alt="text_logo">
        </div>
        <div class="make-community-header" v-if="!modifyBool">
          <common-button 
              @click="registerCommunity()"
              width="150" height="40" 
              buttonName="생성하기"
              background="white" border="none"
              />
        </div>
        <div class="make-community-header" v-if="modifyBool">
          <common-button 
              @click="cancelMake()"
              width="150" height="40" 
              buttonName="취소"
              marginRight="30" background="white" border="none"
              />
          <common-button 
              @click="saveCommunity()"
              width="150" height="40" 
              buttonName="저장"
              background="white" border="none"
              />
        </div>
      </header>
      <main>

        <!-- 글 쓰기 구간 -->
        <div class="write-container">
            <div class="write-title">
              
            </div>
          <div class="write-body">

            <div class="write-wrapper">

              <div class="write-wrapper-title">
                <input type="text" placeholder="제목을 입력하세요" v-model="communityDataSet.title">
              </div>

              <div class="textEditor-area">
                <div class="textEditor-wrapper">
                  <TipTap ref="textEditor" 
                    :isEditable="true"
                    toolbar="true"
                    @sendContents="receivedEditorContents"/>
                </div>
              </div>

            </div>
            <div class="btnSet" v-if="!modifyBool">
              <div class="btn-area1">
                <common-button 
                    buttonName="취소"
                    width="150" height="40"
                    background="white" marginRight="30"
                    @click="cancelMake()"
                    />
                <common-button 
                    buttonName="미리보기"
                    width="150" height="40" background="white"
                    @click="moveToPreView()"
                    />
              </div>
              <div class="btn-area2">
                <common-button 
                    buttonName="등록하기"
                    width="150" height="40" border="none" background="#FBBC05"
                     color="white"
                    @click="saveCommunity()"
                    />
                
              </div>
            </div>

            
          </div>

        </div>

      </main>
      
      <footer>
        <main-footer/>
        <small-modal ref="modal" :modalBtnData="modalBtnData" smallModal="수정되었습니다." @backToPage="cancelMake()" width="300" height="150" font-size="24"
        font-weight="700"/>
      </footer>
      
    </div>
  </div>
</template>

<script>
import CommonButton from '@/components/CommonButton.vue';
import MainFooter from '@/components/MainFooter.vue'
import TipTap from '@/components/TextEditor.vue'
import SmallModal from '../components/SmallModal.vue';

export default {
  components: { 
    CommonButton, 
    MainFooter, 
    TipTap, 
    SmallModal
    },
  name: "MakeNote",
  data() {
    return {
      modifyBool: false,
      communityDataSet: {
        title: '',
        contents: '',
        imgList: '',
        email: sessionStorage.getItem('email')
      },
      modalBtnData: [
        {
          id: 1,
          name: '닫기',
          background: 'black',
          color: 'white'
        }
      ]
    }
  },
  methods: {
    cancelMake() {
      window.history.back();
    },
    registerCommunity() {
      console.log('생성하기 버튼을 눌렀다!');
      this.saveCommunity(this.communityDataSet);
    },
    saveCommunity() {
      this.$refs.textEditor.sendContents();
      let data = this.communityDataSet
      console.log(this.communityDataSet);
      const calledAxios = this.$store.state.storedAxios;

      const obj = {
        title: data.title,
        thumbnail: data.imgList[0],
        text: data.contents,
        creater: {
          email: data.email
        }
      }

      calledAxios.post('/commuList', obj)
        .then(res => console.log(res))
        .catch(err => console.log(err));

      this.$router.push("/mcol/main");
      
    },
    receivedEditorContents(contents, imgList) {
      this.communityDataSet.contents = contents;
      this.communityDataSet.imgList = imgList;

    },
    modifyCommunity() {
      console.log(this.communityDataSet);
      this.$refs.modal.openModal();
    },
    moveToPreView() {
      console.log('미리보기 버튼을 눌렀다!');
    },
  }
}
</script>

<style scoped>
.make-community{
  width: 100vw;
  height: 100vh;
  justify-content: center;
  display: flex;
}
.make-community-body {
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
.make-community-header {
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
.write-body {
  width: 100%;
}
.write-title {
  display: flex;
  height: 40px;
}
.write-wrapper {
  width: 1020px;
  height: 740px;
  padding: 0 40px;
}
.write-wrapper-title {
  /* width: calc(100%-40px); */
  height: 130px;
  display: flex;
  padding-left: 10px;
  align-items: center;
}
.write-wrapper-title input {
  width: 100%;
  height: 50px;
  background: none;
  border: none;
  border-bottom: 2px solid #C4C4C4;
  font-size: 30px;
  font-weight: bold;
}
.write-wrapper-title input:focus {
  outline: none;
}
.write-wrapper-title input::placeholder {
  font-weight: 300
}
.textEditor-area {
  width: 980px;
  height: 500px;
  background: white;
  border-radius: 12px;
  padding: 20px 20px 0 20px;
}

.btnSet {
  width: calc(100%-120px);
  height: 120px;
  padding: 0 60px;
  /* background: lightgreen; */
  display: flex;
}
.btn-area1 {
  width: 70%;
  display: flex;
}
.btn-area2 {
  width: 30%;
  display: flex;
  justify-content: flex-end;
}
</style>