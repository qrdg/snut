<template>
  <div class="navigator closeNavBar">
    <div class="hamburgerBtn-area">
      <img class="hamburgerBtn" src="@/assets/btn_hamburger.png" alt="nav_btn" @click="closeNavBar">
    </div>
    <ul>
      <li v-for="(menu, idx) in menuDataSet" :key="idx" @click="goToPage(menu)">{{ menu.name }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'NavigatorBar',
  data() {
    return {
      menuDataSet: [
        {
          id: 1,
          name: 'MY COLLECTION',
          path: '/mcol/main'
        },
        {
          id: 2,
          name: 'COMMUNITY',
          path: '/com',
        },
        {
          id: 3,
          name: 'MY PAGE',
          path: '/mc'
        },
        {
          id: 4,
          name: 'ABOUT',
          path: '/ab'
        },
      ]
    }
  },
  methods: {
    openNavBar() {
      var a = document.querySelector('.navigator');
      a.classList.add('openNavBar');
      
    },
    closeNavBar() {
      var a = document.querySelector('.navigator');
      a.classList.remove('openNavBar');
    },
    goToPage(menu) {
      if(sessionStorage.getItem('token') && menu.id != 4) {
        this.$router.push(menu.path)
      } else if(menu.id === 4) {
        this.$router.push(menu.path)
      } else {
        alert("로그인 해주세요");
        this.$router.push('/logi');
      }
    }
  }
}
</script>

<style scoped>
.navigator {
  /* opacity: 0.5; */
  position: fixed;
  width: 400px;
  height: 200%;
  top: 0;
  background: #FFFFFF;
  transition: all .2s;
  right: -400px;
  z-index: 9999;
  border-left: 1px black solid;
}
.openNavBar {
  right: 0px;
}
.hamburgerBtn-area {
  position: relative;
}
.hamburgerBtn {
  position: absolute;
  top: 66px;
  right: 124px;
  cursor: pointer;
}
ul {
  margin-top: 300px;
  list-style: none;
}
li {
  display: block;
  margin: 85px auto;
  text-align: center;
  font-family: 'AlegreyaSans';
  font-weight: 800;
  font-size: 30px;
  cursor: pointer;
}
li * {
  font-family: 'AlegreyaSans';
  font-weight: 800;
  font-size: 30px;
  text-decoration: none;
  color: black;
}
</style>