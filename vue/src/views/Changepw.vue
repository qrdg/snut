<template>
<div class="change-p">

  <!--  header-->
  <header>
    <div class="logo-area header-area">
      <router-link to="/"><img class="logo-text" src="@/assets/logo_text.png" alt="text_logo"></router-link>
    </div>
  </header>

<!--  body-->
  <div class="change-form">
    <div class="title">
      CHNAGE PASSWORD
    </div>
    <div class="middle">
      Input your new desired password in the input fields below to create a new password. <br>
      We strongly advise you to store it safely.
    </div>
    <!-- form body-->
    <div class="form-input">
      <!--  password -->
      <div class="pw flex">
        <div class="span">
          <div class="pw2">New Password</div>
          <div class="flex">
            <img class="icon" src="@/assets/icon/Key-Line.png" alt="pw">
            <input class="put" placeholder="Password">
          </div>
        </div>
      </div>

<!-- comfirm pw-->
      <!--  password -->
      <div class="pw flex">
        <div class="span">
          <div class="pw2">Confirm Password</div>
          <div class="flex">
            <img class="icon" src="@/assets/icon/Key-Line.png" alt="pw">
            <input class="put" placeholder="Confirm Password">
          </div>
        </div>
      </div>

    </div>

    <!-- button -->
    <div class="bu-wrapper">
      <common-button button-name="Change Password" width="300" height="62" background="#DC8552" color="white" border="none"
                     border-radius="0" margin-top="30" font-size="16" @click="confirm()"> </common-button>
    </div>
    <small-modal ref="modal" :modalBtnData="modalBtnData" smallModal="비밀번호 수정이 완료되었습니다."/>

  </div>
</div>
</template>

<script>
import CommonButton from "@/components/CommonButton.vue";
import SmallModal from "@/components/SmallModal.vue";

export default {
  name: "Changepw-page",
  components:{CommonButton, SmallModal},
  props:['change data'],
  data(){
    return{
      modalBtnData: [
        {
          name: '확인',
          background: 'black',
          color:'white'
        }
        ]
    }
  },
  methods:{
    confirm(){
      console.log();
      this.$refs.modal.openModal();
    }
  }
}
</script>

<style scoped>
.change-p{
  max-width: 1200px;
  min-width: 1200px;
  width: 100vw;
  height: 100vh;
  /* background: lightcoral; */
  margin: 0 auto;
}
.logo-text {
  width: 113px;
  height: 39px;
  margin-top: 60px;
  /* position: absolute; */
  left: 0;
  top: 0;
}
.title{
  height: 150px;
  font-size: 30px;
  font-weight: 700;
  display: flex;
  justify-content: center;
}
.middle{
  font-weight: 400;
  font-size: 16px;
  text-align: center;
}
.form-input{
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding-top: 150px;
  align-items: center;
}
input:focus {
  outline: none;
}
.icon {
  margin-right: 10px;
}
.put {
  color: #868686;
  flex: none;
  order: 0;
  flex-grow: 1;
  height: 38px;
  border: none;
  font-size: 16px;
  margin-bottom: 3px;
  background: #e8ece6;

}
.span {
  display: block;
  border-bottom: 1px solid #000000;
  margin-bottom: 30px;
}
.flex {
  display: flex;
  align-items: center;
}
.bu-wrapper{
  display: flex;
  justify-content: center;
}

</style>