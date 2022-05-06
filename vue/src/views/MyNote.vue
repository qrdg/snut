<template>
  <div class="my-note">
    <div class="my-note-body">
      <header>
        <div class="logo-area header-area">
          <!-- <img class="main-logo-text" src="@/assets/logo_text.png" alt="text_logo"> -->
        </div>
        <div class="my-note-title">
          <common-button 
              @click="moveToPage()"
              width="300" 
              height="40" 
              buttonName="오늘의 기록"
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
                :delColBoolean="true"
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


export default {
  components: { CommonButton, CommonCollection, MainFooter, NavigatorBar, CommonModal },
  name: "MainWithLogin",
  data() {
    return {
      loginBool: false,
      sampleData: { 

      }, 
    }
  },
  methods: {
    openNavBar() {
      this.$refs.navBar.openNavBar()
    },
    openModal(cuData, moveToPageBool) {
      this.$refs.modal.openModal(cuData, moveToPageBool);
    },
    moveToPage() {
      this.$router.push({path: '/mcol/note/makenote'})
    },
    doAxios() {
      const calledAxios = this.$store.state.storedAxios;
      calledAxios.get('/mcol/mn', {
        params: {
          'email': sessionStorage.getItem('email')
        }
      })
      .then(res => { 
        console.log(res.data);
        this.sampleData = res.data;
        this.sampleData = res.data.Collection.concat(res.data.Curation);
        this.sampleData.map(i => {
          if(i.collectionNo) i.cuCo = "Collection";
          else if(i.curationNo) i.cuCo = "Curation";
        })
        this.sampleData.sort((a, b) => {
          if(a.modDate < b.modDate) return -1;
          if(a.modDate > b.modDate) return 1;
          if(a.modDate === b.modDate) return 0;
        })
        console.log("this.sampleData", this.sampleData)
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
.my-note-body {
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
.my-note-title {
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
  flex-wrap: wrap;
}
.main-searched-col {
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