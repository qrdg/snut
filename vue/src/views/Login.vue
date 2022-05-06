<template>
<div class="login wrapper">

  <div class="login-p">
  <!--  header-->
  <header>
      <div class="logo-area header-area">
        <router-link to="/"><img class="logo-text" src="@/assets/logo_text.png" alt="text_logo"></router-link>
      </div>
  </header>
<!--  body  -->
    <div class="login-form">
      <div class="form-body">
        <div class ="form-title"> LOGIN </div>

        <div class="form-wrapper">
          <div class="form-input">
            <!--email-->
            <div class="mail flex">
              <div class="span">
                <div class="mail2">Email</div>
                <div class="flex">
                  <img class="icon" src="@/assets/icon/Mail-Closed-Line.png" width="18" height="18" alt="email"/>
                  <input class="put" v-model="userId" placeholder="Email">
                </div>
              </div>
            </div>
            <!--  password -->
            <div class="pw flex">
              <div class="span">
                <div class="pw2">Password</div>
                <div class="flex">
                  <img class="icon" src="@/assets/icon/Key-Line.png" alt="pw" width="24" height="24">
                  <input class="put" type="password" v-model="userPw" placeholder="Password">
                </div>
              </div>
            </div>
<!--keep me sign in button-->
            <div class="selec-bu-wrapper">
              <input type="checkbox" style="margin-right: 20px">
              <p>Keep me Signed in</p>
            </div>
            <common-button button-name="LOGIN" width="300" height="62" background="#DC8552" color="white" border="none"
                          border-radius="0" margin-top="30" font-size="16" @click="doLogin()"> </common-button>

          </div>
          <div class="bor"></div>
<!--social login form-->
          <div class="social-login">
<p class="ifyou">If you don’t already have an account click the button below to create your account.</p>
            <div class="create-acc">
              <img src="../assets/button/create-acount.png" alt="create" @click="goJoin()">
            </div>
            <p class="continue">OR contiue with</p>
            <div class="signin-google">
              <img src="@/assets/button/sign-in-with-google.png" alt="google">
            </div>
          </div>
        </div>
<!-- forget pw-->
            <div class="forgot">
              <p>So you can’t get in to your account? Did you &nbsp;<router-link to="/fgpw"> forget your password?</router-link></p>
            </div>
      </div>

    </div>



    </div>



</div>
</template>

<script>
import CommonButton from "@/components/CommonButton";
import axios from 'axios';

export default {
  name: "Login-page",
  components: {CommonButton},
  data() {
    return {
      userId: '',
      userPw: ''
    }
  },
  methods: {
    doLogin() {
      const loginData = {
        email: this.userId,
        pw: this.userPw
      }
  
      const url = "http://localhost:8080/jwt/login";
      const header = {
        "Content-Type": "application/json"
      }
      axios.post(url, loginData, header).then((res) => {
        console.log(res);
        const data = res.data;
        sessionStorage.setItem("email", data.email);
        sessionStorage.setItem("name", data.name);
        sessionStorage.setItem("gender", data.gender);
        sessionStorage.setItem("nickName", data.nickname);
        sessionStorage.setItem("mobile", data.mobile);
        sessionStorage.setItem("birth", data.birth);
        sessionStorage.setItem("token", data.token);
        alert("로그인 완료!");
        this.$router.push("/");
      }).catch(res => {
        console.log(res);
        alert("ID 또는 비밀번호를 다시 확인해주세요!")
        });
      
    },
    goJoin(){
      this.$router.push({path:"/regi"});
    }
  }
}
</script>

<style scoped>

.login wrapper{
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.login-p{
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
  align-items: center;
  justify-content: center;
}

.form-body{
  width:1000px;
  height: 700px;
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
  max-width: 1000px;
  min-width: 1000px;
  height: 600px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.bor{
  border-right: 1.5px solid #868686;
  padding-left: 200px;
  padding-top: 20px;
  margin-right: 80px;
  height: 400px;

}

input:focus {
  outline: none;
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
}
.flex {
  display: flex;
  align-items: center;
}
.icon {
  margin-right: 10px;
}

.mail {
  margin-bottom: 30px;
}
.icon {
  margin-right: 10px;
}
.selec-bu-wrapper{
  display: flex;
  align-items: center;
}
.ifyou{
  font-size: 14px;
  font-weight: 500;
  margin: 0 0 30px auto;
}
.social-login{
 display: flex;
 flex-direction: column;
  align-items: center;
}
.continue{
  font-weight: 700;
  font-size: 14px;
  margin: 20px auto 20px auto;
}
.forgot p{
  font-weight: 700;
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding-bottom: 50px;
}
a{
  text-decoration-color: black;
}

</style>