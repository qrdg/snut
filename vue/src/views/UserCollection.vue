<template>
  <div class="user-collections">
    <div class="user-collections-body">
      <header>
        <div class="logo-area header-area">
          <!-- <img class="main-logo-text" src="@/assets/logo_text.png" alt="text_logo"> -->
        </div>
        <div class="user-collections-title">
          <common-button 
              width="300" 
              height="40"
              :buttonName="$route.params.nickName+'\'s 컬렉션'"
              background="white" 
              border="none"
              fontSize="20" />
        </div>
        <div class="button-area">
          <!-- <img src="@/assets/btn_hamburger.png" alt="nav_btn" @click="openNavBar"> -->
        </div>
      </header>
      <main>

        <div class="main-col">
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

        <navigator-bar ref="navBar" />
      </main>
      
      <footer>
        <main-footer/>
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
import MainFooter from '@/components/MainFooter.vue'
import NavigatorBar from '../components/NavigatorBar.vue';
import CommonModal from '../components/CommonModal.vue';
import axios from 'axios';


export default {
  components: {
    CommonButton, MainFooter, NavigatorBar, CommonModal, CommonCollection
  },
  name: "UserCollection",
  data() {
    return {
      loginBool: true,
      sampleData: [], 
      userCollection: []
    }
  },
  methods: {
    openNavBar() {
      this.$refs.navBar.openNavBar()
    },
    openModal(cuData) {
      this.$refs.modal.openModal(cuData);
    },
    doAxios() {
      console.log(this.$route.params)
      axios.get('http://localhost:8080/ucol', {
        params: {
          'collectionNo': this.$route.params.id
        }
      })
      .then(res => { 
          this.sampleData = res.data;
          this.sampleData.map(i => i.cuCo = 'Curation');
      })
      .catch(error => console.log(error));
    },
  },
  created() {
    this.doAxios();
  }
}
</script>

<style scoped>
.user-collections-body {
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
.main-logo-text {
  width: 113px;
  height: 39px;
  margin-top: 60px;
  /* position: absolute; */
  left: 0;
  top: 0;
}
.user-collections-title {
  width: 40%;
  display: flex;
  justify-content: center;
  margin-top: 60px;
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

/* 컬렉션 구간 */
.main-col {
  width: 100%;
  max-width: 1200px;
  /* background: green; */
}
.main-col-area {
  width: 100%;
  display: flex;
  /* justify-content: space-between; */
  flex-wrap: wrap;
}
.main-searched-col {
  font-family: 'Noto-sans KR','Apple SD Gothic Neo', sans-serif ;
  margin-top: 20px;
  margin-right: 20px;
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