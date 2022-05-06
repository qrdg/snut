<template>
  <div class="snutinfo">
    <div class="info-area">
      <p class="maker">만든 사람</p>
      <br>
      <p class="whysnut">왜 "sunt"일까요?</p>
      <div class="snut-textarea">
        <p>snut 이란, space+nut 이 합쳐진 의미로, 공유하는 공간을 담은 space와 가볍게 즐기는 스낵 콘텐츠(snack contents)의 의미를 담은 nut가 합쳐져,<br>
          우리만의 취향과 관심들을 담은 것들이 모여 다같이 공유, 추천, 커뮤니티를 이루는 공유 큐레이션 사이트로 다가갈 수 있는 공간이 되고싶다는 의미입니다.</p>
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
</template>

<script>
export default {
  name: 'Information-page'
};
</script>

<style scoped>
.snutinfo {
  min-width: 1520px;
  height: 100%;
  display: flex;
}
.info-area {
  min-width: 1520px;
  margin-left: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.maker {
  /* 만든사람 */


  font-family: 'Noto-sans KR', 'Apple SD Gothic Neo', sans-serif;
  font-style: normal;
  font-weight: 600;
  font-size: 30px;
  /* identical to box height, or 0% */


  color: #000000;
}

.whysnut {
  /* 왜 sunt일까요? */


  font-family: 'Noto-sans KR', 'Apple SD Gothic Neo', sans-serif;
  font-style: normal;
  font-weight: 600;
  font-size: 30px;
  /* identical to box height, or 0% */


  color: #000000;


}
.snut-textarea2 {


  /* 한글 폰트 버튼2/ 바디 */

  font-family: 'Noto-sans KR', 'Apple SD Gothic Neo', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
  text-align: center;

  color: #000000;
}
.small-logo {
  display: flex;
}
.main-logo-text {
  /* logo */


}

.peanut-logo {
  /* peanut */


}

.center-logo {
  width: 350px;
  height: 350px;

}

.snut-textarea {


  /* 한글 폰트 버튼2/ 바디 */

  font-family: 'Noto-sans KR', 'Apple SD Gothic Neo', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
  text-align: center;

  color: #000000;


}


</style>