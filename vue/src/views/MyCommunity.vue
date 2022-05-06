<template>
  <div class="mycom-page">
    <!--  header-->
    <header>
      <div class="mycom-head">
        <div class="snutlogo-text">
          <router-link to="/">
            <img class="homebu" src="@/assets/logo_text.png" alt="logo_text">
          </router-link>
        </div>
      </div>
      <div class="mycom-head-bu">
        <common-button width="150" marginRight="30" height="40" buttonName="커뮤니티 홈" border="none" background="white" color="black" @click="moveToComHome()"/>
        <common-button width="150" height="40" buttonName="커뮤니티 만들기" border="none" background="black" color="white" @click="moveToPage()"/>
      </div>
    </header>
    <!--header-->

<!--body-->
    <div class="mycom-body-wrapper">
      <div class="mycom-body">


<!--bookmark-->
      <div class="bookmark-wrapper">
        <div class="mycom-font">내 커뮤니티</div>
        <div class="bookmark-form">
          <Bookmark
              :bookmarkData="bookmark" v-for="(bookmark, idx) in bookmarkDataSet.joinCommunity" :key="idx"/>
          <Bookmark
              :bookmarkData="bookmark" v-for="(bookmark, idx) in bookmarkDataSet.myCommunity" :key="idx"/>
        </div>
      </div>


<!-- alarm-->
      <div class="alarm-wrapper">
        <div class="mycom-font">알람</div>
        <div class="alarm-form">
          <alarm :alarmData="alarm" 
              v-for="(alarm, idx) in alarmDataSet.commentAlerts" 
              @click="alertCheck(alarm.ano, alarm.cmo)"
              :key="idx" />
          <alarm :alarmData="alarm" 
              v-for="(alarm, idx) in alarmDataSet.joinAlertList" 
              @click="permi(alarm)"
              :key="idx" />
          <!-- <alarm :alarmData="alarm" v-for="(alarm, idx) in alarmDataSet" :key="idx" @click="permi($event, alarm)"/> -->
        </div>
      </div>

      <small-modal ref="modal" 
          :modalBtnData="modalBtnData"
          smallModal="닉네임 가입을 승인하시겠습니까?"
          @applyJoin="applyJoin"
          @rejectJoin="rejectJoin"
          width="350" height="100">

      </small-modal>

      </div>


    </div>
    </div>

</template>

<script>
import CommonButton from "@/components/CommonButton";
import Bookmark from "@/components/Bookmark";
import Alarm from "@/components/Alarm";
import SmallModal from "@/components/SmallModal";

export default {
  name: "MyCommunity-page",
  components: { CommonButton, Bookmark,Alarm, SmallModal},
  data() {
    return {
      modalBtnData: [
        {
          name: '수락',
          background: 'white',
          color:'black'
        },
        {
          name: '거절',
          background: 'black',
          color: 'white'
        }
      ],
      alarmDataSet: [
        {
          id: 1,
          alarmTitle: '댓글 알람',
          src: '',
          icon: 'Bell-Line'
        },
        {
          id: 2,
          alarmTitle: '커뮤니티 공지사항',
          src: '',
          icon:'Keyhole-Line'
        },
        {
          id: 3,
          alarmTitle: '커뮤니티 가입 승인',
          src: '',
          icon: 'Users-Line'
        },
      ],
      bookmarkDataSet:[
        {
          title: '커뮤니티 리스트',
          icon: 'circle'
        }
      ]
    }
  },
  methods:{
    permi(alarmData){
        this.$refs.modal.openModal(alarmData);
    },
    moveToPage(){
      this.$router.push({path:"/com/mcom"});
    },
    moveToComHome(){
      this.$router.push({path:"/com"});
    },
    applyJoin(data) {
      console.log("data", data);
      this.alarmDataSet.joinAlertList = this.alarmDataSet.joinAlertList.filter(i => {
        if (i.tcommunity.no != data.tcommunity.no) return i;
      })
    },
    rejectJoin(data) {
      const calledAxios = this.$store.state.storedAxios;
      console.log(data);

      calledAxios.delete('/commuJoinReject', {
        params: {
          num: data.tcommunity.no
        }
      })
        .then(res => {
          console.log(res)
          this.alarmDataSet.joinAlertList = this.alarmDataSet.joinAlertList.filter(i => {
            if (i.tcommunity.no != data.tcommunity.no) return i;
          })
          alert(res.data);
        }).then(() => this.$refs.modal.modalBoolean = false);
    },
    alertCheck(ano, cmo) {
      const calledAxios = this.$store.state.storedAxios;

      calledAxios.post('/commentAletDelete', {
        id: ano
      })
      .then(() => {
        this.$router.push({
          path: `/com/in/${cmo}`,
          params: {
            communityNo: cmo
          }
        });
      })

    },
    getCommunityData() {
      const calledAxios = this.$store.state.storedAxios;

      calledAxios.get('/myCommuList')
        .then(res => {
          console.log(res)
          this.bookmarkDataSet = res.data;
          this.bookmarkDataSet.myCommunity.map(i => {
            i.icon = {}
            i.icon = 'circle';
          })
          this.bookmarkDataSet.joinCommunity.map(i => {
            i.icon = {}
            i.icon = 'circle';
          })
          this.getCommentData(calledAxios);
        })
    },
    getCommentData(calledAxios) {
      calledAxios.get('/commuMyPage')
        .then(res => {
          console.log(res.data)
          this.alarmDataSet = res.data;
          this.alarmDataSet.commentAlerts.map(i => {
            i.icon = {};
            i.icon = 'Bell-Line';
          });
          this.alarmDataSet.joinAlertList.map(i => {
            i.icon = {};
            i.icon = 'Users-Line';
          });
        });
    }
  },
  created() {
    this.getCommunityData();
  }

}
</script>

<style scoped>
.mycom-page {
  width: 100vw;
  height: 100vh;
}

.mycom-head {
  width: 800px;
  margin: 0 auto;
  height: 100%;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;

}

header {
  height: 160px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  max-width: 1200px;
  margin: 0 auto;

}

.snutlogo-text {
  width: 113px;
  height: 39px;
  position: absolute;
  left: 5px;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.mycom-head-bu {
  display: flex;
  align-items: center;
  width: 400px;
}

.mycom-body-wrapper{
  display: flex;
  align-items: center;
  justify-content: center;
}
.bookmark-wrapper{
  display: inline-block;
  justify-content: center;
  text-align: center;
  margin-right: 65px;
}
.bookmark-form{
  width: 500px;
  height: 837px;
  border: 0.5px solid #000000;
  border-radius: 12px;
  background: white;
  overflow: scroll;
}
.alarm-wrapper{
  display: inline-block;
  justify-content: center;
  text-align: center;

}
.alarm-form{
  width: 500px;
  height: 837px;
  border: 0.5px solid #000000;
  border-radius: 12px;
  background: white;
}
.mycom-font{
  font-size: 30px;
  font-weight: 400;
  margin-bottom: 30px;
}
.mycom-body{
margin-bottom: 60px;
  display: flex;
}
</style>