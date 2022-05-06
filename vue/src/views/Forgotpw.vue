<template>
  <div class="forgot-page">

      <!--  header-->
      <header>
        <div class="logo-area header-area">
          <router-link to="/"><img class="logo-text" src="@/assets/logo_text.png" alt="text_logo"></router-link>
        </div>
      </header>
    <div class="forgot-form">
<div class="title">
  FORGOT PASSWORD
</div>
      <div class="middle">
        Don’t worry we can help you out! if you still remember your email address you can quickly reset your password. <br>
        Just input that information in the fields below and click on the button. <br>
        This will send you a new email that will link you to the password change website.
      </div>

<!-- form body-->
      <div class="form-input">
        <!--email-->
        <div class="mail flex">
          <div class="span">
            <div class="mail2">Email</div>
            <div class="flex">
              <img class="icon" src="@/assets/icon/Mail-Closed-Line.png" width="18" height="18" alt="email"/>
              <input class="put" placeholder="Email">
            </div>
          </div>
        </div>
      </div>

<!-- button -->
      <div class="bu-wrapper">
      <common-button button-name="Request password change" width="300" height="62" background="#DC8552" color="white" border="none"
                     border-radius="0" margin-top="30" font-size="16"> </common-button>
      </div>

        </div>
    </div>

</template>

<script>
import CommonButton from "@/components/CommonButton";

export default {
  name: "Forgotpw-page",
  components:{CommonButton}
}
</script>

<style scoped>
.forgot-page{
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
  justify-content: center;
  padding-top: 150px;
}
 input:focus {
  outline: none;
}
.mail {
  margin-bottom: 30px;
}
.flex {
  display: flex;
  align-items: center;
}
.span {
  display: block;
  border-bottom: 1px solid #000000;
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
.bu-wrapper{
  display: flex;
  justify-content: center;
}
</style>