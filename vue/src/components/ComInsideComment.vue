<template>
  <div class="comInsideComment">
    <!--  form-->
      <ul class="list-comment">

        <!--list-->
        <li v-for="(reply, idx) in communityReplySet" :key="idx">

          <div class="comment-section">
            <p class="re-reply" v-if="reply.parentNo">&nbsp;&nbsp;&nbsp;┖</p>
            <div class="comment-post">

              <div class="profile-info">
                
                <div class="nickName"><b>{{ reply.writer.nickName }}</b></div>
                <span class="time">{{ getDateFormat(reply.modDate) }}</span>
              </div>

              <div class="box-post">
                <span v-if="!reply.editcomment" class="origin-comment" tabindex="0">
                  {{ reply.text }}
                </span>
                <textarea v-if="reply.editcomment" 
                          v-model="reply.text"
                          @keyup.enter="modifyReply(reply.cno, reply.text) || (reply.editcomment = false)">
                </textarea>
              </div>

                <div class="replymore">
                  

                  <!--reply bu-->
                  <div class="reply">
                    <p v-if="!reply.parentNo" @click="replycom($event,idx)">reply</p>

                    
                  <!-- 더보기 버튼 클릭시 노출될 항목 -->
                  <div v-if="modalnumber==idx" class="modifyCon"
                      :style="{left:modifyleft+40+'px', top:modifytop-40+'px'}">


                    <div id="moreContent" @click="modifyModal($event,idx)">
                      <span id="Content">
                        <span class="moreDetail" id="modifyBoard"  @click="modifycom(idx)">
                          수정
                        </span><br>
                        <span class="moreDetail" id="deleteBoard" @click="deletecom($event, idx, reply)">
                          삭제
                        </span><br>
                      </span>
                    </div>

                  </div>


                  </div>
                  <!--more bu-->
                  <button class="action" @click="modifyModal($event,idx)">
                    <img class="actbu" src="@/assets/icon/com-button.png" alt="com-button" >
                  </button>
                  
                </div>

                <!-- 대댓글-->
                <div class="comment-text-write" v-if="replyOn == idx">
                  <div class="inner-text-write">

                    <div class="box-text-area">
                      <textarea 
                          v-model="reReplyData.text"
                          maxlength="800" placeholder="Enter your comments">
                      </textarea>
                    </div>

                    <div class="wrap-menu">
                      <div class="area">

                        <span class="num-text-area">
                          <span class="num-text">글자 수</span>
                          <span class="num-count-empty">{{ reReplyData.text.length }}</span>
                          /
                          <span class="num-text">총 글자 개수</span>
                          800
                        </span>

                        <div class="btn-post">
                          <common-button class="post-btn" buttonName="post" width="40" height="20" border-radius="12"
                                        font-size="14"
                                        background="#FBBC05"
                                        color="white" border="none" @click="postReply($event, idx, reReplyData, reply.cno)"></common-button>
                        </div>

                      </div>
                    </div>

                  </div>
                </div>


            </div>
          </div>

        </li>

        <!-- 하나의 댓글 끝 -->
      </ul>

    <!-- 댓글 페이징 처리-->


    <div class="comment-text-write">
      <div class="inner-text-write">

        <div class="box-text-area">
          <textarea v-model="replyData.text" maxlength="800" placeholder="Enter your comments">
          </textarea>
        </div>

        <div class="wrap-menu">
          <div class="area">

            <span class="num-text-area">
              <span class="num-text">글자 수</span>
              <span class="num-count-empty">{{ replyData.text.length }}</span>
              /
              <span class="num-text">총 글자 개수</span>
              800
            </span>

            <div class="btn-post">
              <common-button @click="newPostReply(replyData)" buttonName="post" class="post-btn"
                            width="40" height="20" font-size="14" background="#FBBC05"
                            border-radius="12" color="white" border="none" />
            </div>

          </div>
        </div>

      </div>
    </div>
    <!-- reply form -->

      <div class="comment-paging">
        <!-- <pagenationnum scale="scale(0.5, 0.5)" /> -->
        <pagenationnum :pageData="pageData" @move="moveToPage" />
      </div>
  </div>

</template>

<script>
import Pagenationnum from "@/components/Pagenationnum.vue";
import CommonButton from "@/components/CommonButton.vue";

export default {
  name: "ComInsideComment-page",
  components: {
    Pagenationnum,
    CommonButton,
  },
  data() {
    return {
      modalBtnData: [
        {
          name: '확인',
          background: 'white',
          color: 'black'
        }
      ],
      pageData: {},
      communityReplySet: [],
      replyData: {
        text: '',
        writer: {
          email: sessionStorage.getItem('email'),
        },
        communityName: {
          no: this.$route.params.communityNo
        },
        parentNo: null,
      },
      reReplyData: {
        text: '',
        writer: {
          email: sessionStorage.getItem('email'),
        },
        communityName: {
          no: this.$route.params.communityNo
        },
        parentNo: null,
      },
      modifyleft: 0,
      modifytop: 0,
      show: false,
      modalnumber: -1,
      replyOn: -1,
      axios: this.$store.state.storedAxios,
      respondMessage: ''
    }
  },
  methods: {
    newPostReply(data) {
      const calledAxios = this.$store.state.storedAxios;
      let obj = {
        text: data.text,
        writer: {
          email: sessionStorage.getItem('email'),
        },
        communityName: {
          no: this.$route.params.communityNo,
        } ,
        parentNo: data.parentNo,
      };
      calledAxios.post('/commentList',obj)
        .then(res => {
          console.log(res.data);
          
          if (res.data == '권한 없음') {
            this.respondMessage = res.data;
            this.replyData.text = '';
            alert("가입 신청을 해주세요!");
          } else {
            this.loadCommunity(this.pageData.page);
            location.reload();
          }
        })
    },
    postReply(e, idx, data, cno) {
      if (this.replyOn == idx) {
        this.reReplyData.parentNo = cno;

        this.newPostReply(data);
        this.replyOn = -1
      }
    },
    modifyReply(cno, content) {
      const calledAxios = this.$store.state.storedAxios;
      calledAxios.post('/comment/mod', {
        cno,
        content
      }).then(res => {
        console.log(res)
      })
    },
    getDateFormat(data) {
      let date = new Date(data)
      const formatDate = (date) => {
        // let dates = new Date().getDate() - date.getDate();
        // let hours = new Date().getHours() - date.getHours();
        // let minutes = new Date().getMinutes() - date.getMinutes();
        // console.log("dates", dates);
        // console.log("hours", hours);
        // console.log("minutes", minutes);
        let formatted_date = 
            // (date.getMonth() >= 10 ? date.getMonth() : '0'+ (date.getMonth() + 1)) 
            // + "/" + (date.getDate() >= 10 ? date.getDate() : '0' + date.getDate())
            + " " + (date.getHours())
            + ":" + (date.getMinutes());
        return formatted_date;
      }
      return formatDate(date);
    },
    modifyModal(e, idx) {
      if (this.modalnumber == -1) {
        this.modifyleft = e.offsetX+55
        this.modifytop = e.offsetY-40
        this.modalnumber = idx;

      } else {
        this.modalnumber = -1
      }
    },
    modifycom(idx) {
      this.communityReplySet[idx].editcomment =!this.communityReplySet[idx].editcomment
    },
    deletecom(e, idx, reply) {
      const calledAxios = this.$store.state.storedAxios;
      // alert("정말 삭제하시겠습니까?")
      this.communityReplySet.splice(idx, 1)
      
      let obj = {
        email: sessionStorage.getItem('email'),
        cno: reply.cno,
        commentEmail: reply.writer.email
      }

      calledAxios.post('/comment/delete', obj)
          .then(res => {
            console.log(res)
          })
      location.reload();
    },
    replycom(e, idx) {
      if (this.replyOn == -1) {
        this.replyOn = idx;
      } else {
        this.replyOn = -1
      }
    },
    loadCommunity(page) {
      const calledAxios = this.$store.state.storedAxios;
      this.replyData.communityName = this.$route.params.communityNo;
      calledAxios.get('/commentList', {
        params: {
          no: this.$route.params.communityNo,
          page: page
        }
      })
        .then(res => {
          console.log(res.data);
          this.communityReplySet = res.data.nomal.content;
          this.makePageData(res.data.nomal)
        });
    },
    makePageData(data) {
      // let showPage = 5

      // let total = data.totalElements;
      let page = data.number + 1
      // let size = data.size
    
      let totalPage = data.totalPages;

      let tempEnd = Math.ceil(page/10) * 10;
      let start = tempEnd - 9
      let end = totalPage > tempEnd ? tempEnd: totalPage;

      let next = page < end;
      let prev = page > 1;

      let pageList = [];
      for(let i = start-1 ; i < end; i++) {
        pageList.push(start-1 + i);
      }

      let pageData = { next, prev, pageList, page, totalPage }

      this.pageData = pageData;
    },
    moveToPage(page) {
      this.loadCommunity(page);
    }
  },
  mounted() {
    console.log(this.replyData.communityName.no);
    this.loadCommunity();
  }
}
</script>

<style scoped>
li {
  display: list-item;
  list-style: none;
  padding: 20px 0;
  border-bottom: 1px solid #D9D9D9;
}
.comment-section {
  display: flex;
  width: 900px;
}
.comment-post {
  width: 900px;
}
.re-reply {
  display: block;
  content: "";
  width: 200px;
  height: 20px;
}
.profile-info {
  display: flex;
  align-items: center;
  padding-left: 20px;
  margin-bottom: 5px;
}
.time {
  padding-left: 10px;
  font-weight: 400;
  font-size: 16px;
  color: #868686;
}
.box-post {
  width: 850px;
  margin-left: 20px;
  margin-bottom: 5px;
}
.origin-comment {
  word-break: break-all;
}
.replymore {
  display: flex;
  position: relative;
  justify-content: flex-end;
}
.reply {
  width: 32px;
  position: relative;
  padding-right: 10px;
  cursor: pointer;
}
.action {
  width: 20px;
  border: none;
  background: none;
  margin-right: 20px;
  cursor: pointer;
}
.actbu {
  width: 4px;
  height: 16px;
  scale: 1;
}
.comment-text-write {
  margin-top: 20px;
  padding: 0 30px;
}
.inner-text-write {
  background: white;
  padding: 12px 10px 10px 20px;
  border: 1px solid #e4e4e4;
  border-radius: 2px;
}
textarea {
  height: 90px;
  resize: none;
  width: 100%;
  margin: -5px -5px 0;
  padding: 5px 5px 0;
  font-size: 13px;
  vertical-align: top;
  background-color: transparent;
  border: 0 none;
  font-size: 16px;
}
textarea:focus {
  /* outline: none; */
}
.area {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-top: 20px;
}
button {
  border: none;
  background: none;
}
button img {
  object-fit: contain;
}
.num-text-area {
  display: inline-block;
  margin: 10px 10px 0 3px;
  vertical-align: top;
  font-size: 13px;
}
.num-text {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
  color: #959595;
  font-size: 11px;
}
.modifyCon {
  position: absolute;
}

#moreContent {
  display: block;
  top: 22px;
  right: 33px;
  min-width: 56px;
  background-color: #fff;
  border-color: #d2d2d2;
  box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .1);
  border: 1px solid #d2d2d2;
  position: relative;
  z-index: 10;
  padding: 5px 0;
  border-radius: 2px;
  box-sizing: border-box;
}

#Content {
  display: inline-block;
  width: 100%;
  min-height: 34px;
  text-align: center;
  padding: 5px 10px;
  font-size: 13px;
  box-sizing: border-box;
}

.moreDetail {
  display: inline-block;
  cursor: pointer;
}
.moreDetail:first-child {
  padding-bottom: 6px;
}
.comment-paging {
  margin-top: 50px;
  transform: scale(0.8, 0.8)
}

</style>