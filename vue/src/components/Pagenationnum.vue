<template>
  <div class="btn-container" v-if="pageData.totalPage">
    <ul class="btn-list">
      <li class="previous">
        <img @click="(getStart && moveToPage(pageData.page - 2))
                  || (pageData.prev && moveToPage(pageData.page - 2))" 
            src="@/assets/icon/pre-page.png" alt="arrow" />
      </li>
      <li v-for="(page, idx) in pageData.pageList" 
          @click="moveToPage(page)" :key="idx">
        {{ commuInside && page || page + 1 }}
      </li>
      <li class="next">
        <img @click="(getEnd && moveToPage(pageData.page + 1))
            || pageData.next && moveToPage(pageData.page + 1)" 
            src="@/assets/icon/next-page.png" alt="arrow"/> 
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Pagenation-num",
  props: ['pageData', 'word', 'commuInside'],
  data() {
    return {
      page: this.pageData,
      pageWord: this.word
    }
  },
  methods: {
    moveToPage(page) {
      console.log(page);
      this.$emit('move', page);
    }
  },
  computed: {
    getStart() {
      if (this.pageData.start == this.pageData.page) return false;
      else return true;
    },
    getEnd() {
      if (this.pageData.end == this.pageData.page) return false;
      else return true;
    }
  },
  created() {
    console.log("false?", this.pageData.end == this.pageData.page);
    console.log("pageData.prev", this.pageData)
  }
}
</script>

<style scoped>
.btn-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
  padding-bottom: 50px;
}
ul {
  list-style: none;
  display: flex;
}
li {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 30px;
  font-weight: 600;
  margin-right: 40px;
  cursor: pointer;
}
</style>