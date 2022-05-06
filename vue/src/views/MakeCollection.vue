<template>
  <div class="make-collections">
    <div class="make-collections-body">

      <header>
        <div class="header-area">
          <common-button 
              width="150" 
              height="40" 
              v-for="(btn, idx) in btnDataSet"
              :buttonName="btn.name"
              @click="btnMethods(idx)"
              background="white" 
              marginRight="30"
              border="none" 
              :key="idx" />
        </div>
        <div class="make-col-header-center">
        </div>
        <div class="button-area">
          <common-button 
              width="150" 
              height="40" 
              buttonName="선택하기" 
              :background="selectMode ? 'lightgrey' : 'white'"
              border="none"
              @click="selectCol" />
        </div>
      </header>

      <main>

        <div class="main-col">
          <div class="main-col-area">
            <common-curation
                ref="showCol"
                class="main-show-col"
                @click="!selectMode && openModal(col), selectMode && selectCurations(idx, col.curationNo)"
                @deleteCol="deleteCol"
                v-for="(col, idx) in sampleData"
                :info="col"
                :id="idx"
                :delColBoolean="true"
                :loginBool="loginBool"
                :selectMode="selectMode"
                :key="col"
                draggable="true" />
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
import CommonCuration from '@/components/CommonCuration.vue';
import MainFooter from '@/components/MainFooter.vue'
import NavigatorBar from '../components/NavigatorBar.vue';
import CommonModal from '../components/CommonModal.vue';


export default {
  components: { CommonButton, CommonCuration, MainFooter, NavigatorBar, CommonModal },
  name: "MakeCollection",
  data() {
    return {
      loginBool: true,
      selectMode: false,
      sampleData: {}, 
      userCollection: [],
      btnDataSet: [
        {
          name: '발행하기',
        },
        {
          name: '발행취소',
        }
      ],
      selectedCurationsData: [],
      sendToCuration: [],
    }
  },
  methods: {
    openNavBar() {
      this.$refs.navBar.openNavBar()
    },
    openModal(data) {
      this.$refs.modal.openModal(data);
      },
    deleteCol(id, cuId) {
      const calledAxios = this.$store.state.storedAxios;
      console.log("cuId", cuId);
      this.sampleData.splice(id, 1);
      
      calledAxios.delete("/cu/del", {
        params: {
          no: cuId
        }
      });
    },

    selectCol() {
      if (this.selectMode == true) {
        this.selecteMode = false;
        this.resetSelectMode();
      } else {
        this.selectMode = true;
      }
    },
    selectCurations(id, colId) {
      console.log(colId);
      this.$refs.showCol[id].selectedMethod();

      let arr = Array.from(this.selectedCurationsData);
      let findIndex = arr.findIndex(v => v === colId);

      if(findIndex == -1) arr.push(colId);
      else arr.splice(findIndex, 1);


      this.selectedCurationsData = arr;
            
      console.log("data", this.selectedCurationsData);
    
    },
    btnMethods(idx){
      if(idx == 0) {
        console.log('발행하기 버튼을 눌렀다!');
        let sendArr = Array.from(this.selectedCurationsData);
        console.log("sendArr", sendArr);
        if(sendArr.length) {
          this.$store.commit('setSendToCuration', sendArr);
          this.$router.push('/mcol/mc/save');
        }

        this.resetSelectMode();
      } else {
        window.history.back();
      }
    },
    resetSelectMode() {
        this.selectMode = false;
        this.selectedCurationsData = [];
        for(var cu of this.$refs.showCol) {
          cu.selected = false;
        }
    },
    getMyCurations() {
      const calledAxios = this.$store.state.storedAxios;
      
      calledAxios.get('/mcol/mc/em', {
        params: {
          email: sessionStorage.getItem('email'),
        }
      })
      .then(res => {
        this.sampleData = res.data;
        this.sampleData.map(i => i.cuCo = "Curation");
      })
    },
  },
  created() {
    this.getMyCurations();
  },
}
</script>

<style scoped>
.make-collections-body {
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
  display: flex;
  width: 30%;
  margin-top: 70px;
}
.toggleBtn {
  width: 30px;
  height: 30px;
  border-radius: 15px;
  cursor: pointer;
  margin-right: 50px;
}
.make-col-header-center {
  width: 40%;
  display: flex;
  justify-content: center;
  margin-top: 60px;
  margin-left: 50px;
}
.button-area {
  width: 30%;
  /* background: red; */
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
  margin-top: 60px;
}
.button-area img {
  margin-right: 20px;
  cursor: pointer;
}

/* 컬렉션 구간 */
.main-col {
  width: 100%;
  max-width: 1200px;
  min-height: 800px;
  /* background: green; */
}
.main-col-area {
  width: 100%;
  display: flex;
  /* justify-content: space-between; */
  flex-wrap: wrap;
}
.main-show-col {
  width: 180px;
  height: 180px;
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