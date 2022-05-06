<template>
  <div class="footer">

    <div class="footer-line"></div>

    <div class="footer-body">
      <div class="footerMenu" v-for="(title, idx) in footerTitle" :key="idx">
        <p><a :href="title.src">{{ title.name }}</a></p>
      </div>
      <div class="footer-logo">
        <img src="@/assets/small_logo.png" alt="footer_img" @click="goToMain()">
        {{ writeCmt }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MainFooter',
  data() {
    return {
      footerTitle: [
        { name: 'ABOUT', 
          src: '/ab'
        },
        { name: 'FAQ', 
          src: '/faq'
        },
        { name: 'FEEDBACK', 
          src: '/fb'
        },
        { name: 'MY PROFILE', 
          src: '/pf'
        },
      ],
      writeCmt: '© snut. 2021 We love our users!'
    }
  },
  methods:{
    goToMain(){
      this.$router.push("/");
    }
  }
}
</script>

<style scoped>
.footer {
  /* background: lightgreen; */
  display: flex;
  width: 100%;
  flex-direction: column;
  margin-top: 150px;
}
.footer-body {
  height: 300px;
  position: relative;
  display: flex;
  padding: 0 100px;
  justify-content: center;
  align-items: center;
}
.footer-line {
  width: 100vw;
  position: absolute;
  left: 0;
  height: 1px;
  background: black;
}
.footerMenu {
  width: 300px;
  height: calc(100%-30px);
  /* background: lightgray; */
  display: flex;
  justify-content: center;
  align-items: center;
  color: #292F6D;
  font-size: 25px;
  padding-bottom: 30px;
}
.footerMenu:nth-child(2) {
  margin-right: 200px;
}
.footer-logo {
  height: 100%;
  position: absolute;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items:flex-start;
}
.footer-logo img {
  margin-left: 20px;
  margin-bottom: 30px;
}
a {
  text-decoration: none;
  color: #292F6D;
}
</style>