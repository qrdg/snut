<template>
<div class="join-wrapper">
  <div class="join-p">
  <!--  header-->
  <header>
    <div class="logo-area header-area">
      <router-link to="/"><img class="logo-text" src="@/assets/logo_text.png" alt="text_logo"></router-link>
    </div>
  </header>

    <!--  body  -->
    <div class="login-form">
      <div class="form-body">
        <div class ="form-title"> JOIN</div>

        <div class="form-wrapper">
          <div class="form-input">
<!--  name-->
            <div class="name flex">
              <div class="span">
                <div class="name2">Name</div>
                <div class="flex">
                  <img class="icon" src="@/assets/icon/User-Line.png" width="24" height="24" alt="name"/>
                  <input class="put" placeholder="Name" v-model="urname">
                </div>
              </div>
            </div>

            <!--email-->
            <div class="mail flex">
              <div class="span">
                <div class="mail2">Email</div>
                <div class="flex">
                  <img class="icon" src="@/assets/icon/Mail-Closed-Line.png" width="18" height="18" alt="email" margin-right="20"/>
                  <input class="put" placeholder="Email" v-model="uremail">

                </div>
              </div>
            </div>


            <!--  password -->
            <div class="pw flex">
              <div class="span">
                <div class="pw2">Password</div>
                <div class="flex">
                  <img class="icon" src="@/assets/icon/Key-Line.png" width="24" height="24" alt="pw">
                  <input class="put" placeholder="Password" type="password" v-model="urpw">
                </div>
              </div>
            </div>

            <!-- confirm pw-->
            <div class="pw flex">
              <div class="span">
                <div class="pw2">Confirm Password</div>
                <div class="flex">
                  <img class="icon" src="@/assets/icon/Key-Line.png" width="24" height="24" alt="pw">
                  <input class="put" placeholder="Confirm Password" type="password" v-model="urpwcheck"
                  @blur="checkpw()">
                </div>
              </div>
            </div>

            <!--Mobile-->
            <div class="mobile flex">
              <div class="span">
                <div class="mobile2">Mobile</div>
                <div class="flex">
                  <img class="icon" src="@/assets/icon/Call-Line.png" width="24" height="24" alt="mobile">
                  <input class="put" placeholder="Mobile" v-model="urmobile">
                </div>
              </div>
              <div class="mobile-bu">
                <!-- <common-button buttonName="인증하기" background="white" border="none" width="85" height="35"
                              marQgin-left="40" font-weight="400"></common-button> -->
              </div>
            </div>

            <!--Nickname-->
            <div class="birth flex">
              <div class="span">
                <div class="birth2">BirthDay</div>
                <div class="flex">
                  <img class="icon" src="@/assets/icon/Flag-Triangular-Line.png" width="24" height="24" alt="nick">
                  <input class="put" placeholder="Birthday" v-model="urbirth">
                </div>
              </div>
            </div>

            <!--Nickname-->
            <div class="nick flex">
              <div class="span">
                <div class="nick2">Nickname</div>
                <div class="flex">
                  <img class="icon" src="@/assets/icon/Flag-Triangular-Line.png" width="24" height="24" alt="nick">
                  <input class="put" placeholder="Nickname" v-model="urnick">
                </div>
              </div>
            </div>

            <!--Gender-->
            <div class="gen flex">
              <div class="type">
                <div class="gen2">Gender</div>
                <div class="flex">
                  <input type="radio" name="gender" value="male" v-model="urgender" checked>&nbsp;남&nbsp;
                  <input type="radio" name="gender" value="female" v-model="urgender">&nbsp;여
                </div>
              </div>
            </div>
<!--body done-->

          </div>
        </div>
      </div>
    </div>
    <!--send button-->
    <div class="send-button-wrapper">
      <common-button button-name="REGISTER" width="300" height="62" background="#DC8552" color="white" border="none"
                     border-radius="0" margin-top="30" font-size="16" @click="btnRegister()"></common-button>
    </div>
  </div>
</div>
</template>

<script>
import CommonButton from "@/components/CommonButton.vue";
import axios from 'axios';

export default {
  name: "Join-page",
  components:{CommonButton},
data(){
    return{
      urname:'',
      uremail:'',
      urpw:'',
      urpwcheck: '',
      urmobile:'',
      urnick:'',
      urbirth:'',
      urgender:'male',
      password_check: false,
    }
},
  methods:{
    checkpw(){
      if(this.urpw === '' || this.urpwcheck === ''){
        alert('비밀번호를 입력해주세요.');
      }else{
        if(this.urpw === this.urpwcheck){
          alert('비밀번호가 일치합니다.');
          this.password_check = true;
          return
        }else{
          alert('비밀번호가 일치하도록 입력해주세요.');
          this.password_check =false;
          return
        }
      }
    },
    btnRegister(){
      if(this.urname ==''){
        alert('이름을 입력하세요.');
        return;
      }else if(this.urpw == ''){
        alert('비밀번호를 입력하세요.');
        return;
      }else if(this.urpwcheck ==''){
        alert('비밀번호를 확인해주세요.');
        return;
      }else if(this.uremail=='') {
        alert('이메일을 입력하세요.');
        return;
      }else if(this.urmobile ==''){
        alert('번호를 확인해주세요.');
        return;
      }else if(this.urgender == ''){
        alert('성별을 선택해주세요.');
        return;
      }
      try{
        if(this.password_check === true){
          const sendData ={
            name: this.urname,
            pw: this.urpw,
            email: this.uremail,
            mobile: this.urmobile,
            gender: this.urgender,
            nickName: this.urnick,
            birth: this.urbirth
          }
          const result ={
            ret:1
          }
          if(result.ret === 1){
            const url = "http://localhost:8080/register"
            const header = {
              "Content-Type": "application/json", 
            }
            axios.post(url, sendData, header).then((res) => {
              console.log(res);
              alert('회원가입 성공.');
              this.$router.push({path:'/'});
            });
          }
        }
      } catch (error){
        console.error(error);
      }
    }
  }


};
</script>

<style scoped>
.join-p{
  max-width: 1200px;
  min-width: 1200px;
  width: 100vw;
  height: 100vh;
  /* background: lightcoral; */
  margin: 0 auto;
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
.login-form{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.form-body{
  width: 1000px;
  height: 750px;
  display: flex;
  flex-direction: column;
  justify-content: center;

}
.form-title{
  height: 150px;
  font-size: 30px;
  font-weight: 700;
  display: flex;
  justify-content: center;
}
.form-wrapper{
  display: flex;
  justify-content: center;
}
.flex {
  display: flex;
  align-items: center;
  padding: 10px 0 0 0;
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
  width: 300px;

}
input:focus {
  outline: none;
}
.span {
  display: block;
  border-bottom: 1px solid #000000;
}
.send-button-wrapper{
  display: flex;
  justify-content: center;
  margin-top: 30px;
  margin-bottom: 50px;
}
img{
  padding-right: 10px;
}


</style>