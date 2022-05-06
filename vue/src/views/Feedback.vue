<template>
  <form>
  <div class="feedback-page">
    <!--  header-->
    <header>
      <div class="feedback-p">
        <div class="snutlogo-text">
          <router-link to="/">
            <img class="homebu" src="@/assets/logo_text.png" alt="logo_text">
          </router-link>
        </div>
        <div class="text-feedback">FEEDBACK</div>
      </div>
    </header>
<!--    feed back form  -->
    <div class="feedback-form-wrapper">
      <div class="feedback-form">
        <div class="feedback-sender-box">
          <div class="sender"> 보내는 사람:</div>
          <input class="sender-placeholder" placeholder="이메일을 입력하세요.">
        </div>
          <div class="feedback-sender-box2">
          <div class="title"> 제목:</div>
          <input class="title-placeholder" placeholder="제목을 입력하세요.">
          </div>
        <div class="feedback-sender-box3"> 의견 내용 </div>
        <div class="send-form">
          <textarea class="send-form-textarea"></textarea>
        </div>
      </div>
    </div>
<!--send button-->
    <div class="send-button-wrapper">
      <button class="send-button" type="submit">의견 보내기</button>
    </div>
  </div>
  </form>
</template>

<script>

export default {
  name: "Feedback-page",
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

.feedback-p {
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

.text-feedback {
  display: flex;
  position: relative;
  text-align: center;
  font-style: normal;
  font-weight: 800;
  font-size: 40px;
  letter-spacing: 0.1em;
  color: #F6F6F6;

}

.feedback-form-wrapper {
  margin: 120px auto 40px auto;
  width: 1000px;
  height: 620px;
  background: #F7F7F7;
  border: 1px solid #707070;
  border-radius: 12px;
  box-sizing: border-box;
}

.feedback-sender-box {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 19px 11px;
  text-align: center;
  position: absolute;
  height: 38px;
  background: #FFFFFF;
  border: 1px solid #000000;
  box-sizing: border-box;
  border-radius: 12px;
  margin-left: 30px;
  margin-top: 60px;
  justify-content: flex-start;
  width: 500px;
}

.sender-placeholder {
  color: #868686;
  flex: none;
  order: 0;
  flex-grow: 1;
  margin: 0 10px;
  height: 38px;
  border: none;
  font-size: 16px;
}

.sender-placeholder:focus {
  outline: none;
}

.feedback-sender-box2 {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 19px 11px;
  text-align: center;
  position: absolute;
  height: 38px;
  background: #FFFFFF;
  border: 1px solid #000000;
  box-sizing: border-box;
  border-radius: 12px;
  margin-left: 30px;
  margin-top: 140px;
  justify-content: flex-start;
  width: 500px;
}

.title-placeholder {
  color: #868686;
  flex: none;
  order: 0;
  flex-grow: 1;
  margin: 0 10px;
  height: 38px;
  border: none;
  font-size: 16px;
}

.title-placeholder:focus {
  outline: none;
}
.feedback-sender-box3{
  font-size: 16px;
  position: absolute;
  padding-top: 215px;
  padding-left: 30px;

}
.send-form{
  position: absolute;
  background: #FFFFFF;
  border: 1px solid #000000;
  box-sizing: border-box;
  border-radius: 12px;
  justify-content: center;
  width: 930px;
  height: 330px;
  margin-top: 250px;
  margin-left: 34px;
}
.send-form-textarea{
  color: #868686;
  flex: none;
  order: 0;
  flex-grow: 1;
  margin: 10px 10px 10px 10px;
  height: 300px;
  width: 900px;
  border: none;
  font-size: 16px;
  resize: none;
}
.send-form-textarea:focus{
  outline: none;
}
.send-button-wrapper{
  width:100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.send-button{
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