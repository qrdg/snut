<template>
  <div class="search-collections">
    <div class="search-collections-body">
      <header>
        <div class="logo-area header-area">
          <img class="main-logo-text" src="@/assets/logo_text.png" @click="$router.push('/')" alt="text_logo">
        </div>
        <div class="input-area">
          <input-box 
              ref="inputBox"
              class="search-input"
              @doSearch="doSearch"
              placeholder="SEARCH"
              width="100"
              height="38" />
        </div>
        <div class="button-area">
<!--   dropdown menu-->
          <div  >
            <div v-for="(list, idx) in dropdownData" :key="idx" class="dropdown">
              <common-button @click="openlist(idx)"  v-if=" openData || active == idx"  :buttonName="list" width="80" height="35" marginTop="5" marginRight="20" />
            </div>
          </div>
          <img src="@/assets/btn_hamburger.png" alt="nav_btn" @click="openNavBar">
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
          <common-modal ref="modal" @loginBool="loginBool" @applyLike="applyLike"/>
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
import NavigatorBar from '../components/NavigatorBar.vue';
import CommonModal from '../components/CommonModal.vue';
import axios from 'axios';

export default {
  components: { InputBox, CommonButton, CommonCollection, MainFooter, NavigatorBar, CommonModal },
  name: "SearchCollections",
  data() {
    return {
      loginBool: false,
      sampleData: {},
      // sampleData: { 
      //   Collection: [],
      //   Curation: [],
      //   Folder: {
      //     Curation:[],
      //     Collection: []
      //   },
      
      loginSignText: 'If You Want To See More, Just Sign In!',
      searchedWord: '',

      dropdownData: ["전체보기","큐레이션","컬렉션","최신순","오래된순"],
      active: 0,
      openData: false
    }
  },
  methods: {
    openNavBar() {
      console.log('a');
      this.$refs.navBar.openNavBar()
    },
    openModal(cuData, moveToPageBool) {
      this.$refs.modal.openModal(cuData, moveToPageBool);
    },
    doSearch(searchWord) {
      if(searchWord != '') {
        this.$router.push(`/col`, {
          query: {
            searchWord: searchWord
          }
        });
        console.log("?/????", searchWord);
        this.doAxios(searchWord);
        this.$refs.inputBox.clearWord();
      }
    },
    openlist(idx){
      console.log(idx)
      let temp = this.dropdownData[0];
      this.dropdownData[0] = this.dropdownData[idx];
      this.dropdownData[idx] = temp;
      this.openData = !this.openData
    },
    applyLike(separate, no) {
      console.log("separate", separate)
      console.log("no", no)
      this.sampleData.map(data => {
        if(data.cuCo == "Collection" && data.cuCo == separate && data.collectionNo == no) data.like = !data.like;
        if(data.cuCo == "Curation" && data.cuCo == separate && data.collectionNo == no) data.like = !data.like;
      });
    },
    doAxios(word) {
      // let calledAxios = this.$store.state.storedAxios;
      console.log("searchWord", word)
      
      let obj = {
        searchWord: word
      }
      console.log("obj", obj);

      axios.get('http://localhost:8080/main', {
        params: {
          word: word
        }
      })
        .then(res => { 
          // console.log(res)
          console.log(res.data);
          const combined = res.data.Collection.concat(res.data.Curation)
          combined.map(i => {
            if('collectionNo' in i) i.cuCo = "Collection";
            else if('curationNo' in i) i.cuCo = "Curation";
          })
          this.sampleData = combined;
      
        })
        .catch(error => console.log(error));
    },
  },
  created() {
    this.doAxios(this.$route.query.searchWord);

  }
}
</script>

<style scoped>
.search-collections-body {
  max-width: 1200px;
  min-width: 1200px;
  /* width: 100vw;
  height: 100vh; */
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
  cursor: pointer;
}
.input-area {
  width: 40%;
  text-align: center;
}
.search-input {
  margin-top: 67px;
  margin-right: 5%;
}
.button-area {
  width: 30%;
  /* background: red; */
  display: flex;
  justify-content: space-between;
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