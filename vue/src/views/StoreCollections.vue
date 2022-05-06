<template>
  <div class="store-collections">
    <div class="store-collections-body">

      <header>
        <div class="header-area">
          <div class="toggleBtn" 
              @click="changeTitle(btn)"
              v-for="(btn, idx) in storeCollectionsData" 
              :key="idx" 
              :style="{background: btn.color}">
          </div>
        </div>
        <div class="store-col-header-center">
          <common-button 
              width="300" 
              height="40" 
              :buttonName="toggleBtnTitle" 
              background="white" 
              border="none"
              fontSize="20" />
          <div class="addBtn" @click="!selectMode && addFolder()">
            <img src="@/assets/icon/plus.png" alt="plus_button">
          </div>
        </div>
        <div class="button-area">
          <common-button 
              width="150" 
              height="40" 
              buttonName="선택하기" 
              :background="selectMode == true ? 'lightgrey' : 'white'" 
              border="none"
              fontSize="20"
              marginRight="20"
              @click="selectCol()" />
          <common-button 
              width="150" 
              height="40" 
              buttonName="삭제하기" 
              background="white" 
              border="none"
              fontSize="20"
              @click="deleteCol(this.toDeleteIdxList)"
              v-if="selectMode" />
          <common-button 
              width="150" 
              height="40" 
              buttonName="저장" 
              background="white" 
              border="none"
              fontSize="20"
              @click="saveCol" 
              v-if="!selectMode" />
        </div>
      </header>

      <main>
        <div class="main-col">
          <div class="main-col-area">
            <common-collection
                ref="showCol"
                class="main-show-col"
                @dragstart="startDrag($event, idx, col)"
                @drop="onDrop($event, idx, col)"
                @dragenter.prevent
                @dragover.prevent
                @click="!selectMode && openModal(col, true), selectMode && selectMethods(idx, col)"
                v-for="(col, idx) in sampleData"
                :info="col"
                :id="idx"
                :delColBoolean="true"
                :loginBool="loginBool"
                :selectMode="selectMode"
                @deleteCol="deleteCol"
                @sendFolderData="receivedFolderData" 
                @convertDisabled="convertDisabled"
                :key="idx"
                draggable="true" />
            <!-- <common-collection
                ref="showCol"
                class="main-show-col"
                @click="!selectMode && openModal(col, true), selectMode && selectMethods(idx, col)"
                v-for="(col, idx) in sampleData"
                :info="col"
                :id="idx"
                :delColBoolean="true"
                :loginBool="loginBool"
                :selectMode="selectMode"
                @deleteCol="deleteCol"
                @sendFolderData="receivedFolderData" 
                @convertDisabled="convertDisabled"
                :key="idx"
                draggable="true" /> -->
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
import CommonModal from '@/components/CommonModal.vue';


export default {
  components: {  CommonButton, MainFooter, NavigatorBar, CommonModal
                ,CommonCollection
                },
  name: "StoreCollections",
  data() {
    return {
      loginBool: true,
      selectMode: false,
      indexList: {
        Collection: [],
        Curation: []
      },
      colIdList: {
        Collection: [],
        Curation: [],
      },
      listToSave: {
        Collection: [],
        Curation: []
      },
      deletedList: {
        Collection: [],
        Curation: []
      },
      sampleData: {}, 
      userCollection: [],
      toDeleteIdxList: [],
      toggleBtnTitle: '전부',
      storeCollectionsData: [
        {
          id: 1,
          name: '전부',
          color: '#EECAC6'
        },
        {
          id: 2,
          name: '큐레이션',
          color: '#F3D675'
        },
        {
          id: 3,
          name: '컬렉션',
          color: '#AED8EA'
        },
        {
          id: 4,
          name: '폴더별',
          color: '#F6F6F6'
        },
      ]
    }
  },
  methods: {
    convertDisabled(bool, idx) {
      this.sampleData.Collection[idx].folderNameIsDisabled = bool;
    },
    openNavBar() {
      this.$refs.navBar.openNavBar()
    },
    openModal(cuData, moveToPageBool) {
      if(cuData.cuCo != 'Folder') {
        this.$refs.modal.openModal(cuData, moveToPageBool);
      }
    },
    startDrag(event, idx, dragStartItem) {
      event.dataTransfer.dropEffect = "move";
      event.dataTransfer.effectAllowed = "move";

      event.dataTransfer.setData("startIdx", JSON.stringify(idx));
      event.dataTransfer.setData("dragStartItem", JSON.stringify(dragStartItem));
    },
    onDrop(event, endIdx, dragEndItem) {
      let dragStartItem = JSON.parse(event.dataTransfer.getData("dragStartItem"));
      let startIdx = JSON.parse(event.dataTransfer.getData("startIdx"));     

      // 드래그 시 위치 바꾸기
      if(startIdx != endIdx) {

        if (this.selectMode == false) {
          const comp = this.sampleData[endIdx];
          this.sampleData[endIdx] = this.sampleData[startIdx];
          this.sampleData[startIdx] = comp;
        }

        if (this.selectMode == true && dragEndItem.cuCo == 'Folder' && dragStartItem.cuCo != 'Folder') {
          this.deleteCol(this.toDeleteIdxList);
          this.intoFolder(this.colIdList, dragEndItem.id);
        }

      }
    },
    deleteCol(arr) {
      let b = [];
      for(let i = arr.length - 1; i >= 0; i--) {
        console.log('arr', arr[i]);
        b.push(this.sampleData.splice(arr[i], 1));
      }
      // console.log(b);
      b.map(v => {
        console.log(v[0].id);
        this.deletedList.push(v[0].id);
      });

      console.log(this.deletedList);

      this.toDeleteIdxList = [];
      for (let i = 0; i < this.$refs.showCol.length; i++) {
        this.$refs.showCol[i].selected = false;
      }
      // this.selectMode = false;
    },
    intoFolder(arr, idx) {
      arr.Collection.map((v) => {
        this.sampleData.Folder[idx].push(v)
      });
      arr.Curation.map((v) => {
        this.sampleData.Folder[idx].push(v)
      });

      this.colIdList.Collection = [];
      this.colIdList.Curation = [];
    },

    changeTitle(btnData) {
      this.toggleBtnTitle = btnData.name;
      this.makeDummies();

      if(btnData.id == 2) {
        let curations = this.sampleData.Collection.filter(i => i.cuCo == 'Curation');
        this.sampleData.Collection = [];
        this.sampleData.Collection = curations;

      } else if (btnData.id == 3) {
        let collections = this.sampleData.Collection.filter(i => i.cuCo == 'Collection');
        this.sampleData.Collection = [];
        this.sampleData.Collection = collections;

      } else if (btnData.id == 4) {
        let collections = this.sampleData.Collection.filter(i => i.cuCo == 'Folder');
        this.sampleData.Collection = [];
        this.sampleData.Collection = collections;
      }

      this.clearCol();
    },
    saveCol() {
      console.log('this.deletedList', this.deletedList)
      console.log(this.deletedList.Collection[0] != null);
        if(this.colIdList.Collection[0] == null
            && this.colIdList.Curation[0] == null
            && this.deletedList.Collection[0] == null
            && this.deletedList.Curation[0] == null
            && this.sampleData.Folder.Collection[0] == null
            && this.sampleData.Folder.Curation[0] == null
            ) {
          console.log('변경사항이 없습니다');
        } else {
          this.sampleData.Curation = this.sampleData.Collection.filter(v => v.cuCo == 'Curation');
          this.sampleData.Collection = this.sampleData.Collection.filter(v => v.cuCo == 'Collection');
          console.log(this.sampleData);
        }


    },
    clearCol() {
      for (let i = 0; i < this.$refs.showCol.length; i++) {
        this.$refs.showCol[i].selected = false;
      }
      this.listToSave.Collection = [];
      this.listToSave.Curation = [];
      this.colIdList.Collection = [];
      this.colIdList.Curation = [];
      this.toDeleteIdxList = [];
    },
    selectCol() {
      this.clearCol();
      this.selectMode == true ? this.selectMode = false : this.selectMode = true;
    },
    makeIdx(idx, col) {
      let obj = {
        id: idx,
        colId: col.id,
        dataName: col.cuCo
      }
      return obj;
    },
    makeSelectIdxList(list, idx, text) {
      var findIndex;
      if (text == 'Collection') {
        findIndex = list.Collection.findIndex(v => v == idx);
        if(findIndex != -1) list.Collection.splice(findIndex, 1);
        else list.Collection.push(idx);
        
        } else if (text == 'Curation') {
        findIndex = list.Curation.findIndex(v => v == idx);
        if(findIndex != -1) list.Curation.splice(findIndex, 1); 
        else list.Curation.push(idx);

      } else if (text == null) {
        findIndex = list.findIndex(v => v == idx);
        if(findIndex != -1) list.splice(findIndex, 1); 
        else list.push(idx);
      }
    },
    selectMethods(id, col) {
      this.$refs.showCol[id].selectedMethod();

      if(col.cuCo != 'Folder') {
        let obj = this.makeIdx(id, col);
        
        // indexList : 전체에 대한 idx 리스트로 만들었음
        this.makeSelectIdxList(this.indexList, obj.id, col.cuCo);

        // colIdList : 전체에 대한 colId 리스트로 만들었음
        this.makeSelectIdxList(this.colIdList, obj.colId, col.cuCo);
      
        // toDeleteIdxList : 일괄적으로 지우기 위한 리스트
        this.makeSelectIdxList(this.toDeleteIdxList, obj.id);
        this.toDeleteIdxList.sort((a, b) => {
          if(a > b) return 1;
          if(a === b) return 0;
          if(a < b) return -1;
        });
        console.log('indexList', this.indexList);
        console.log('colIdList', this.colIdList);
        console.log('toDeleteIdxList', this.toDeleteIdxList);
      }
    },
    addFolder() {
      console.log('this.sampleData.Collection before', this.sampleData.Collection);
        let sampleFolder = {};

        let keyLength = Object.keys(this.sampleData.Folder).length;

        sampleFolder.id = keyLength;
        sampleFolder.title = '';
        sampleFolder.regDate = '2022-03-01';
        sampleFolder.modDate = '2022-03-11';
        sampleFolder.folderNameIsDisabled = false;

        sampleFolder.cuCo = 'Folder';

        this.sampleData.Collection.unshift(sampleFolder);
        this.increaseListValue(this.indexList);


        let obj = {
          Collection: [],
          Curation: []
        }
        this.sampleData.Folder[keyLength] = obj;

    },
    increaseListValue(list) {
      let comp;
      for(let i = 0; i < 2; i++) {
        comp = list[Object.keys(list)[i]];
        for(let j = 0; j < comp.length; j++) {
          comp[j] = comp[j] + 1;
        }
      }
    },
    receivedFolderData(folderName, id) {
      console.log('received > ', folderName, id)
      console.log(this.sampleData.Collection.findIndex((data, idx) => idx == id))
      let findIndex = this.sampleData.Collection.findIndex((data, idx) => idx == id);
      this.sampleData.Collection[findIndex].title = folderName;
    },

    doAxios() {
      const calledAxios = this.$store.state.storedAxios;
      calledAxios.get('/store', {
        params: {
          email: sessionStorage.getItem('email')
        }
      })
        .then(res => {
          console.log(res.data)
          this.sampleData = res.data.Collection;
          let a = res.data.Collection;
          let b = res.data.Curation;
          this.sampleData = a.concat(b);
          this.sampleData.map(i => {
            if(i.collectionNo) i.cuCo = "Collection"
            else i.cuCo = "Curation"
          })
          console.log("this.sampleData", this.sampleData);
          console.log(this.$store.state.storedData);
          this.$store.commit('setStoredData', this.sampleData);
          console.log(this.$store.state.storedData);
        });
    }

  },
  created() {
    this.doAxios();
    
  },
}
</script>

<style scoped>
.store-collections-body {
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
.store-col-header-center {
  width: 40%;
  display: flex;
  justify-content: center;
  margin-top: 60px;
  margin-left: 50px;
}
.addBtn {
  width: 40px;
  height: 40px;
  margin-left: 10px;
  cursor: pointer;
}
.addBtn img{
  width: 40px;
  height: 40px;
  object-fit: cover;
}
.addBtn p {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 30px;
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
  /* background: green; */
}
.main-col-area {
  width: 100%;
  display: flex;
  /* justify-content: space-between; */
  flex-wrap: wrap;
}
.main-show-col {
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