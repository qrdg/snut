<template>
  <div class="faq-page">
    <!--  header-->
    <header>
      <div class="faqp">
        <div class="snutlogo-text">
          <router-link to="/">
            <img class="homebu" src="@/assets/logo_text.png" alt="logo_text">
          </router-link>
        </div>
        <div class="text-faq">FAQ</div>
      </div>
    </header>

   <!--  expandable-faqlist-->
<div class="faq-wrapper">
      <div class="expandable"  v-for="(faq,idx) in faqDataset" :key="idx">
        <div class="expandable-title-wrapper">
          <div class="expandable-title" >
          <div @click="toggleOnOff($event,idx)">{{faq.title}}</div>
          </div>
          </div>
          <div class="expandable-text-wrapper" v-if="isStatusOn == idx">
            <div class="expandable-text">
              <div>
             {{faq.list}}
              </div>
            </div>
        </div>
      </div>

</div>


  </div>
</template>

<script>
export default {
  name: "Faq-page"
  ,
  data() {
    return {
      isStatusOn: -1,
      faqDataset:[
        {
          title: "01 스넛은 어떻게 이용하나요?",
          list: " 스넛은 여러분들의 큐레이션과 활동으로 이루어집니다!" +
              " 관심이 있는 것들을 마구마구 공유해주세요!" +
              " 다른 사용자들의 공유한 것을 통해 자기가 원하는 컬렉션으로 담아서 보실 수 있습니다." +
              " 또한, 자신만의 기록으로 새로운 일상들을 채워가보세요." +
              " 내가 기록한 것들 역시 다른 사용자들에게 공유할 수 있어요 :)" +
              " 관심사나 취미가 같은 것을 다른 사람들과 더 공유하고 싶다면 커뮤니티를 이용해 보시는건 어떨까요?"

        },
        {
          title: "02 스넛의 활동레벨의 기준은 무엇인가요? ",
          list: "스넛의 활동레벨은 총 처음 시작을 같이하는 '피넛' 친구를 더불어 총 열개입니다. \n" +
              "두두(호두), 캐슈넛, 피스타치오, 은행, 밤, 아몬드, 마카다미아, 해바라기씨, 피칸 친구들을 가져보세요 :) \n" +
              "활동레벨은 각 오늘의 기록의 갯수로만 올라갑니다. " +
              "1레벨 0개/2레벨 10개 /3레벨 20개/4레벨  50개/ 5레벨 70개 /6레벨 100개 /7레벨 120개" +
              " 8레벨 150개 /9레벨 170개 /10레벨 200개이며, 3레벨부터 원하시는 커뮤니티도 개설할 수 있습니다!\n"
        },
        {
          title: "03 검색을 할때 나오는 기준은 무엇인가요?",
          list:"저희의 검색의 기준은 사용자들이 기록을 하실때 넣는 \"키워드\"로 잡고 검색이 됩니다. " +
              "그 중에 상위 키워드 세개가 큐레이션을 누를시 나오게 됩니다. " +
              "검색이 되었으면 하는 검색어를 해쉬태그와 함께 적어주세요! "
        },
        {
          title: "04 큐레이션이나 나의 기록을 무조건 공개로 해야하나요? ",
          list: "아니요. 비공개로 돌려 오늘의 기록을 남기는 본인의 다이어리 형식으로 쓰셔도 괜찮습니다:) \n"
        },
        {
          title: "05 컬렉션은 어떻게 만드나요?",
          list:"컬렉션은 자신이 쓴 기록들을 합쳐서 컬렉션으로 발행할 수 있습니다. " +
              "MY COLLECTION에 들어가 collection 만들기를 누른 후, " +
              "발행이 되었으면 하는 컬렉션들을 선택 후 발행해주세요!\n"
        },
        {
          title: "06 사용중 불편한 점이 있다면 어디로 의견을 보내야하나요? ",
          list:"최대한 이용하는 점에 관해선 FAQ로 적어두고 있으나, " +
              "사이트의 부족한 점에 따라 의견을 주실 분은 " +
              "FEEDBACK 페이지에 있는 의견보내기로 의견을 보내주시면 이메일로 답을 회신 해드립니다. "
        },
        {
          title: "07 인기컬렉션은 어떻게 선정되어지는건가요?",
          list: "인기컬렉션의 경우, 좋아요의 수가 많은 것들 위주로 메인에 노출됩니다. \n"
        },
        {
          title: "08 개인 추천 컬렉션은 뭔가요?",
          list: "개인추천컬렉션이란, 사용자의 관심사에 대한 검색빈도나 " +
              "사용자가 설정해놓은 키워드와 비슷한 것을 추천해드리는 컬렉션입니다. " +
              "하지만, 이용하기 위해선 로그인이 필수입니다!"
        },
        {
          title: "09 커뮤니티 가입승인은 어디로 받나요? ",
          list: "가입승인에 관해서는 MY PAGE의 알람으로 한꺼번에 갑니다. " +
              "승인을 원하는 알람이 날아오면 가입승인을 누르면 회원의 가입이 확인 됩니다. \n"
        },
        {
          title: "10 제가 가입한 커뮤니티 리스트는 어디서 보나요?",
          list: "가입한 커뮤니티 리스트는 MY PAGE에서 확인이 가능합니다. " +
              "북마크 라인에 가입하신 전체 리스트가 나옵니다."
        },
        {
          title: "11 더 추천받기에는 어떤게 있나요? ",
          list: "메인에 있는 개인 추천 컬렉션과 비슷하게 추천을 해주는 페이지입니다. "
        }
      ]

    }
  },
  methods:{
    toggleOnOff(e,idx) {
      if(this.isStatusOn == -1){
        this.isStatusOn = idx;
      } else{
        this.isStatusOn = -1
      }
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

.faqp {
  width: 90%;
  max-width: 1200px;
  margin: 0 auto;
  height: 100%;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;

}
.snutlogo-text{
  width: 113px;
  height: 39px;
  position: absolute;
  left: 5px;
  align-items: center;
  justify-content: center;
  text-align: center;
}
.faq-wrapper{
  margin: 30px auto;
  max-width: 800px;
  padding-bottom: 100px;
}

.text-faq {
  display: flex;
  position: relative;
  text-align: center;
  font-family: 'Alegreya Sans', sans-serif;
  font-style: normal;
  font-weight: 800;
  font-size: 40px;
  letter-spacing: 0.1em;
  color: #F6F6F6;

}

.expandable {
  cursor: s-resize;
  padding-top: 50px;
  font-size: 30px;
  border-bottom: 3px solid #292f6d;
  text-align: center;
  transition: all .5s;

}

.expandable-title-wrapper {
  display: flex;
  justify-content: space-between;

}

.expandable-title {
  display: flex;
  align-items: center;
  font-weight: 600;
  font-size: 30px;
  color: #292f6d;
}
.expandable-text-wrapper{
  transition: all .5s;
}
.expandable-text{
  padding-top: 50px;
  padding-bottom: 30px;
  font-weight: 400;
  font-size: 25px;
  word-break: keep-all;

}

</style>