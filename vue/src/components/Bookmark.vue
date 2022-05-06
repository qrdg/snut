<template>
  <div class="bookmark-block" @click="moveToPage( bookmarkData.no || bookmarkData.jcommunity.no )">
    <div class="icon"><img :src="require(`@/assets/icon/${bookmarkData.icon}.png`)" width="20" height="20" alt="circle"></div>
    <div class="bookmark">{{ bookmarkData.title || bookmarkData.jcommunity.title }}</div>
  </div>
</template>

<script>
export default {
  name: "Bookmark-block",
  props: ['bookmarkData'],
  methods: {
    moveToPage(communityNo) {
      console.log(communityNo)
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
.bookmark-block {
  display: flex;
  text-align: center;
  align-items: center;
  padding: 15px 10px 15px 5px;
  border-bottom: 1px solid black;
  cursor: pointer;
}
.bookmark-block:hover {
  background: rgba(0, 0, 0, 0.1);
}
.icon{
  margin-right: 10px;
  padding-left: 10px;
  padding-top: 5px;
}
.bookmark{
  font-size: 18px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

</style>