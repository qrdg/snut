<template>
  <div class="main-with-login">
    <div class="main-with-login-body">
      <header>
        <div class="logo-area header-area">
          <img class="main-logo-text" src="@/assets/logo_text.png" @click="$router.push('/')" alt="text_logo">
        </div>
        <div class="input-area">
          <input-box 
              class="main-input" 
              @doSearch="doSearch"
              placeholder="SEARCH"
              width="100"
              height="38"/>
        </div>
        <div class="button-area">
          <!-- <input type="button" value="test" @click="sample"> -->
          <common-button buttonName="Log out" width="80" height="35" marginTop="5" marginRight="20" v-if="loginBool" @click="doLogout()"/>
          <common-button buttonName="Log in" width="80" height="35" marginTop="5" marginRight="20" v-if="!loginBool"
                          @click="gotoLogin"/>
          <common-button buttonName="Register" width="80" height="35" marginTop="5" marginRight="42" @click="gotoJoin" v-if="!loginBool"/>
          <img src="@/assets/btn_hamburger.png" alt="nav_btn" @click="openNavBar">
        </div>
      </header>
      <main>

        <div class="main-with-login-main">
          <img src="@/assets/large_logo.png" alt="main_logo">
          <div class="pick-your-snut">
            <div>PICK YOUR "<span>S</span><span>N</span><span>UT</span>"</div>
          </div>
        </div>

        <div class="main-col">
          <div class="main-col-title">
            <p>
              인기컬렉션
            </p>
            <p @click="gotocol">
              더보기
            </p>
          </div>
          <div class="main-col-area">
            <common-collection 
                class="main-searched-col"
                @click="openModal(col, true)"
                v-for="(col, idx) in sampleData" 
                :info="col" 
                :id="idx"
                :loginBool="loginBool"
                :key="idx" />
          </div>
        </div>
        <div class="main-col">
          <p class="main-col-title" >
            개인 추천 컬렉션
          </p>
          <div class="main-col-area" v-if="loginBool">
            <common-collection 
                class="main-searched-col"
                @click="openModal(col, true)"
                v-for="(col, idx) in sampleData2" 
                :info="col" 
                :id="idx"
                :loginBool="loginBool"
                :tempName="'p_'"
                :key="idx" />
          </div>
          <div class="main-col-area" v-if="!loginBool">
            <div class="loginSign">
              {{ loginSignText }}
            </div>
          </div>
        </div>

        <navigator-bar ref="navBar" />
      </main>
      
      <footer>
        <main-footer />
      <transition name="fade">
        <common-modal ref="modal" />
      </transition>
      </footer>

    </div>
  </div>
</template>

<script>
import CommonButton from '@/components/CommonButton.vue';
import CommonCollection from '@/components/CommonCollection.vue';
import InputBox from '@/components/InputBox.vue';
import MainFooter from '@/components/MainFooter.vue'
import NavigatorBar from '@/components/NavigatorBar.vue';
import CommonModal from '@/components/CommonModal.vue';
import axios from 'axios';


export default {
  components: { InputBox, CommonButton, MainFooter, NavigatorBar, CommonModal,
  CommonCollection,
  },
  name: "MainWithLogin",
  data() {
    return {
      loginBool: sessionStorage.getItem('token'),
      sampleData: {dataSet:[]},
      sampleData2: {dataSet:[]},
      loginSignText: 'If You Want To See More, Just Sign In!',
    }
  },
  methods: {
    openNavBar() {
      this.$refs.navBar.openNavBar()
    },
    openModal(colData, moveToPageBool) {
        // this.$router.push(`/ucol/${colData.collectionNo}/${colData.nickname}`)
        this.$refs.modal.openModal(colData, moveToPageBool);
    },
    sample() {
      this.loginBool == true ? this.loginBool = false : this.loginBool =true;
    },
    doSearch(searchWord) {
      console.log(searchWord);
        this.$router.push({
          path: `/col`,
          query: {
            searchWord: searchWord
          }
        });
    },
    gotoLogin(){
      this.$router.push('/logi')
    },
    gotoJoin(){
      this.$router.push('/regi')
    },
    doLogout() {
      sessionStorage.clear();
      alert('로그아웃 되었습니다');
      location.reload();
    },
    doAxios() {
      axios.get('http://localhost:8080/main/hot', {
        params: {
          size: 10
        }
      })
        .then(res => { 
          console.log(res.data)
            this.sampleData = res.data.dtoList;
            this.sampleData.map(i => {
            i.cuCo = "Collection";
          });
          this.sampleData2 = this.sampleData.splice(5, 10)

        })
        .catch(error => console.log(error));
    },
  },
  created() {
    this.doAxios();
  },

}
</script>

<style scoped>
.main-with-login{
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
}
.main-with-login-body {
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
  height: 150px;
  /* background: lightblue; */
  position: relative;
  display: flex;
  justify-content: flex-end;
}
.header-area {
  width: 30%;
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
.input-area {
  width: 40%;
  text-align: center;
}
.main-input {
  margin-top: 67px;
  margin-right: 5%;
}
.button-area {
  width: 30%;
  /* background: red; */
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
  margin-top: 63px;
}
.button-area img {
  margin-right: 20px;
  cursor: pointer;
}
/* main 구간 */
.main-with-login-main {
  max-width: 1200px;
  height: 700px;
  /* background: blue; */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.main-with-login-main img {
  width: 341px;
  height: 274px;
  object-fit: cover;
}
.pick-your-snut *{
  margin-top: 30px;
  font-family: 'AlegreyaSans', 'sans-serif';
  font-size: 40px;
  letter-spacing: 2.5px;
  font-weight: 800;
}
.pick-your-snut span:nth-child(1) {
  color: #292F6D;
}
.pick-your-snut span:nth-child(2) {
  color: #4F4F4F;
}
.pick-your-snut span:nth-child(3) {
  color: #FBE017;
}
/* 컬렉션 구간 */
.main-col {
  width: 1000px;
  max-width: 1200px;
  height: 420px;
  /* background: green; */
  padding: 0 100px;
}
.main-col-title {
  margin-bottom: 100px;
  display: flex;
  justify-content: space-between;
  font-size: 30px;
  font-weight: bold;
}
.main-col-area {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
.main-col-title p:nth-child(2) {
  color: #666666;
  cursor: pointer;
}
.loginSign {
  width: 980px;
  height: 250px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #AED8EA;
  border-radius: 12px;
  font-size: 30px;
  font-weight: 800;
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