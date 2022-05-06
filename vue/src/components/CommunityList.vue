<template>
  <div class="commu-block" @click="moveToPage(list.no)">
      <p class="commu-num">
        {{list.no}}
      </p>
      <div class="commu-thum" v-if="list.thumbnail">
        <img :src="list.thumbnail" alt="thumsnail">
      </div>
      <div class="commu-text-area" :style="list.thumbnail && {width: 450+'px'} ">
        <p class="commu-title">{{list.title}}</p>
        <p class="commu-text" v-if="list.text">{{list.text}}</p>
    </div>
    <p class="commu-comment">
      댓글갯수({{list.replyCount}})
    </p>
  </div>
</template>

<script>
export default {
  name: "CommunityList",
  props:[
      "list"
  ],
  methods: {
    moveToPage(communityNo) {
      this.$router.push({
        path: `/com/in/${communityNo}`,
        params: {
          communityNo: communityNo
        }
      });
    }
  }
}
</script>

<style scoped>
.commu-block{
  display: flex;
  justify-content: center;
  align-items: center;
  width: 800px;
  height: 135px;
  background: white;
  border-bottom: 2px solid #D9D9D9;
  cursor: pointer;
}
.commu-block > p:first-child {
  text-align: center;
}
.commu-block:hover{
  background: lightgrey;
}
.commu-block:last-child {
  border: none;
}
.commu-num{
  width: 50px;
  font-weight: bold;
  font-size: 30px;
  color: #868686;
  margin-left: 25px;
  margin-right: 35px;
}
.commu-thum{
  display: flex;
  width: 80px;
  height: 80px;
  background: white;
  border: 1px solid #000000;
  margin-right: 20px;
  align-items: center;
  justify-content: center;
}
.commu-thum img {
  width: 100%;
  height: 100%;
  object-fit: fill;
}
.commu-text-area{
  width: 550px;
}
.commu-title{
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-weight: 700;
  font-size: 30px;
  margin-right: 20px;
}
.commu-text{
  height: 30px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-weight: 500;
  font-size: 18px;
  margin-right: 20px;
}
.commu-comment{
  width: 100px;
  display: flex;
  align-items: flex-end;
  justify-content: flex-end;
  padding-right: 20px;

}
</style>