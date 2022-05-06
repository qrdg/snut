<template>
<!-- form -->
<div class="alarm-block">
<!--  text list -->
  <div class="comment-list">
    <div class="com-icon">
      <img :src="require(`@/assets/icon/${alarmData.icon}.png`)" alt="bell" width="24" height="24">
    </div>
    <div class="com-list">
      {{ alarmData.nickName || alarmData.tmember.nickName}}님 : 
      {{ alarmData.text || alarmData.applyMessage }} <br>/ {{alarmData.commuName || alarmData.tcommunity.title}}</div>
  </div>
</div>
</template>

<script>
export default {
  name: "alarm-block",
  props: ['alarmData']
}
</script>

<style scoped>
.alarm-block {
  cursor: pointer;
}
.alarm-block:hover {
  background: rgba(0, 0, 0, 0.1);
}
.comment-list {
  display: flex;
  text-align: left;
  align-items: flex-start;
  padding: 15px 10px 15px 5px;
  border-bottom: 1px solid black;
}
.com-icon{
  margin-right: 10px;
  padding-left: 10px;
  padding-top: 5px;
}
.com-list{
  font-size: 18px;
}
</style>