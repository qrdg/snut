<template>
  <div class="myprofile-page">
    <!--  header-->
    <header>
      <div class="myprofile-p">
        <div class="snutlogo-text">
          <router-link to="/">
            <img class="homebu" src="@/assets/logo_text.png" alt="logo_text">
          </router-link>
        </div>
        <div class="text-myprofile">MY PROFILE</div>
      </div>
    </header>
    <!--  profile form -->
    <div class="profile-wrapper">
      <div class="profile-form-wrapper">
      <div class="profile-form">
        <!--form inside-->
        <!--profile-->
        <div class="profile flex">
          <p>프로필 변경</p>
          <div class="pic-bu">
            <div class="pf-pic" v-if="inputImg">

<!--            <img class="icon" src="@/assets/icon/profile.png" alt="pf">-->
            </div>
            <button class="pf-bu" @click="openModal()" v-if="inputImg">프로필 변경</button>
          </div>
        </div>
        <!--  password -->
        <div class="repw flex">
          <p>비밀번호</p>
          <div class="span">
            <div class="repw2">Re-Password</div>
            <div class="flex">
              <img class="icon" src="@/assets/icon/Key-Line.png" width="24" height="24" alt="pw">
              <input class="put" placeholder="Re-Password" type="password">
            </div>
          </div>
          <div class="span">
            <div class="repw3">Confirm-Password</div>
            <div class="flex">
              <img class="icon" src="@/assets/icon/Key-Line.png" width="24" height="24" alt="pw">
              <input class="put" placeholder="Confirm-Password" type="password">
            </div>
          </div>
        </div>
        <!--email-->
        <div class="mail flex">
          <p>이메일</p>
          <div class="span">
            <div class="mail2">Email</div>
            <div class="flex">
              <img class="icon" src="@/assets/icon/Mail-Closed-Line.png" width="18" height="18"
                      viewBox="0 0 18 16" alt="email"/>
              <input placeholder="Email">
            </div>
          </div>
        </div>
        <!--Mobile-->
        <div class="mobile flex">
          <p>핸드폰 번호</p>
          <div class="span">
            <div class="mobile2">Mobile</div>
            <div class="flex">
              <img class="icon" src="@/assets/icon/Call-Line.png" width="24" height="24" alt="mobile">
              <input placeholder="Mobile">
            </div>
          </div>
        </div>
        <!--Nickname-->
        <div class="nick flex">
          <p>큐레이터명</p>
          <div class="span">
            <div class="nick2">Nickname</div>
            <div class="flex">
              <img class="icon" src="@/assets/icon/Flag-Triangular-Line.png" width="24" height="24" alt="nick">
              <input placeholder="Nickname">
            </div>
          </div>
        </div>
        <!--탈퇴 버튼-->
      </div>

      </div>
      <div class="btn-area"/>
        <CommonButton buttonName="탈퇴" width="150" height="40" @click="quit()"/>
      </div>
      <small-modal ref="modal" :modalBtnData="modalBtnData" smallModal="정말 스넛 이용을 그만하실건가요?">

      </small-modal>
      <div class="send-button-wrapper">
        <button class="send-button" type="submit">변경 완료</button>
      </div>

      <drag-and-drop-modal ref="dndModal" @receiveNoteImg="receiveNoteImg" />
    </div>
    <!--send button-->
</template>

<script>
import CommonButton from "@/components/CommonButton.vue";
import SmallModal from "@/components/SmallModal";
import DragAndDropModal from "@/components/DragAndDropModal";


export default {
  name: "Myprofile-page",
  components:{ CommonButton, SmallModal, DragAndDropModal },
  props:['quitData'],
  data(){
    return{
      modalBtnData: [
        {
          name: '네',
          background: 'white',
          color:'black'
        },{
          name: '아니오',
          background: 'white',
          color:'black'
        }

        ],
      inputImg: true
    }
  },

  methods:{
    quit(){
      console.log();
      this.$refs.modal.openModal();
    },
    openModal(){
      this.$refs.dndModal.openModal();
    }
  }

}
</script>

<style scoped>

header {
  height: 160px;
  width: 100%;
  background: #EECAC6;
  display: flex;
  align-items: center;
  justify-content: center;

}

.myprofile-p {
  width: 90%;
  max-width: 1200px;
  margin: 0 auto;
  height: 100%;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;

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

.text-myprofile {
  display: flex;
  position: relative;
  text-align: center;
  font-style: normal;
  font-weight: 800;
  font-size: 40px;
  letter-spacing: 0.1em;
  color: #F6F6F6;
}

.profile-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 1000px;
  height: 700px;
  background: #F7F7F7;
  border: 1px solid #707070;
  border-radius: 12px;
  box-sizing: border-box;
  margin: 120px auto 40px auto;
}
.profile-form-wrapper{


}
.profile-form {
  display: flex;
  width: 1000px;
  height: 620px;
  flex-direction: column;
  justify-content: center;
}

input:focus {
  outline: none;
}

input {
  color: #868686;
  flex: none;
  order: 0;
  flex-grow: 1;
  height: 38px;
  border: none;
  font-size: 16px;
  background: #F7F7F7;
  margin-bottom: 3px;

}

.span {
  display: block;
  border-bottom: 1px solid #000000;
  margin-left: 50px;
}

.flex {
  display: flex;
  align-items: center;
}

.flex > p {
  width: 200px;
  margin-left: 50px;
}

.profile {
  margin-bottom: 30px;
}

.repw {
  margin-bottom: 30px;
}

.mail {
  margin-bottom: 30px;
}

.mobile {
  margin-bottom: 30px;
}

.nick {
  margin-bottom: 30px;
}

.pic-bu {
  display: flex;
  margin-left: 40px;
  align-items: center;

}

.pf-bu {
  width: 100px;
  height: 30px;
  background: #FFFFFF;
  border-radius: 50px;
  box-shadow: none;
  outline: none;
  border: none;
}
.pf-pic{
  width: 110px;
  height: 110px;
  border-radius: 70%;
  background: white;
  box-shadow: none;
  outline: none;
  border: none;
  margin-right: 10px;
}

/*.icon {*/
/*  margin-right: 10px;*/
/*}*/

.send-button-wrapper {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.send-button {
  display: flex;
  position: relative;
  width: 385px;
  height: 62px;
  background: #DC8552;
  justify-content: center;
  border: none;
  align-items: center;
  margin-bottom: 120px;
  font-size: 16px;
  color: #FFFFFF;
  font-weight: 600;
}

</style>