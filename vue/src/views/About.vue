<template>
  <div class="about">
    <header>
      <div class="aboutp">
        <div class="snutlogo-text">
          <router-link to="/">
            <img class="homebu" src="@/assets/logo_text.png" alt="logo_text">
          </router-link>
        </div>
        <div class="text-about">ABOUT</div>
      </div>
    </header>
    <div class="snutinfo">
      <div class="info-area">
        <p class="maker">만든 사람</p>
        <div class="makerpeo">
          <div class="jin makerp">
          <img class="maker-pic" src="@/assets/maker/maker1.png" alt="maker1" width="200" height="200">
          <p class="textpeo">진성진</p>
          </div>
          <div class="kim makerp">
          <img class="maker-pic" src="@/assets/maker/maker2.png" alt="maker2" width="200" height="200">
          <p class="textpeo">김해창</p>
          </div>
          <div class="sso makerp">
          <img class="maker-pic" src="@/assets/maker/maker3.png" alt="maker3" width="200" height="200">
          <p class="textpeo">김소연</p>
          </div>
          <div class="park makerp">
          <img class="maker-pic" src="@/assets/maker/maker4.png" alt="maker4" width="200" height="200">
          <p class="textpeo">박건아</p>
          </div>
        </div>

        <p class="whysnut">왜 "snut"일까요?</p>
        <div class="snut-textarea">
          <p>snut 이란, space+nut 이 합쳐진 의미로,
            <br>공유하는 공간을 담은 space와 가볍게 즐기는 스낵 콘텐츠(snack contents)의 의미를 담은 nut가 합쳐져,<br>
            우리만의 취향과 관심들을 담은 것들이 모여 <br>
            다같이 공유, 추천, 커뮤니티를 이루는 공유 큐레이션 사이트로 다가갈 수 있는 공간이 되고싶다는 의미입니다.</p>
        </div>
        <img class="center-logo" src="@/assets/large_logo.png" alt="Large_logo">
        <div class="small-logo">
          <img class="peanut-logo" src="@/assets/peanut.png" alt="Peanut_logo">
          <img class="main-logo-text" src="@/assets/logo_text.png" alt="logo_text">
        </div>
        <div class="snut-textarea2">
          <p>스낵 컨텐츠 같이 간편함이라는 점에서 피넛 캐릭터를 넣어보자는 생각과 피넛 뒤에 큰 원은 저희만의 스페이스를 나타내보았습니다.<br>
            정형적인 박스형태의 공간적 개념이 아닌, 다양한 사람들의 다양함을 품는 원의 형태로 의미를 담아 보았습니다.<br>
            또한, 땅콩이 가지는 부드러운 곡선형태를 담아내고자, 폰트 형식 휘어지는 형식으로해서 폰트 로고로 만들었습니다.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Information-page'
};
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

.aboutp {
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
}

.text-about {
  display: flex;
  position: relative;
  text-align: center;
  font-family: 'AlegreyaSans', sans-serif;
  font-style: normal;
  font-weight: 800;
  font-size: 40px;
  letter-spacing: 0.1em;
  color: #F6F6F6;

}

.info-area {
  display: block;
  position: relative;
  text-align: center;
  justify-content: center;
}

.maker {
  padding-top: 200px;
  font-family: 'NotoSans', 'Apple SD Gothic Neo', sans-serif;
  font-style: normal;
  font-weight: 600;
  font-size: 30px;
  color: #000000;

}

.whysnut {
  padding-top: 150px;
  font-family: 'NotoSans', 'Apple SD Gothic Neo', sans-serif;
  font-style: normal;
  font-weight: 600;
  font-size: 30px;
  color: #000000;
}

.snut-textarea {
  padding-top: 80px;
  font-family: 'NotoSans', 'Apple SD Gothic Neo', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
  text-align: center;
  line-height: 21.79px;
  color: #000000;

}

.center-logo {
  margin-top: 45px;

}

.small-logo {
  align-items: center;
  justify-content: center;
  text-align: center;
}

.main-logo-text {
  padding-left: 40px;
  padding-bottom: 80px;
}

.snut-textarea2 {
  font-family: 'NotoSans', 'Apple SD Gothic Neo', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
  text-align: center;
  line-height: 21.79px;
  color: #000000;
  padding-top: 45px;
  padding-bottom: 50px;
}
.makerpeo {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  margin-top: 50px;
}

.maker-pic{
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 30px 30px auto 30px;
  text-align: center;
}

.textpeo{
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  text-align: center;
  margin-top: 50px;
}
.makerp{
  display: block;
  flex-direction: column;
  justify-content: center;
text-align: center;
  align-items: center;

}
</style>