<template>
  <transition name="main">
    <router-view />
  </transition>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
@font-face {
  font-family: 'AlegreyaSans';
  src: url('@/assets/font/AlegreyaSans-Light.ttf') format('opentype'),
  url('@/assets/font/AlegreyaSans-Light.ttf') format('truetype');;
  font-weight: 300;
  unicode-range: U+0041-005A, U+0061-007A;
}

@font-face {
  font-family: 'AlegreyaSans';
  src: url('@/assets/font/AlegreyaSans-Regular.ttf') format('opentype'),
  url('@/assets/font/AlegreyaSans-Regular.ttf') format('truetype');;
  font-weight: 400;
  unicode-range: U+0041-005A, U+0061-007A;
}

@font-face {
  font-family: 'AlegreyaSans';
  src: url('@/assets/font/AlegreyaSans-Medium.ttf') format('opentype'),
  url('@/assets/font/AlegreyaSans-Medium.ttf') format('truetype');;
  font-weight: 500;
  unicode-range: U+0041-005A, U+0061-007A;
}

@font-face {
  font-family: 'AlegreyaSans';
  src: url('@/assets/font/AlegreyaSans-Bold.ttf') format('opentype'),
  url('@/assets/font/AlegreyaSans-Bold.ttf') format('truetype');;
  font-weight: 700;
  unicode-range: U+0041-005A, U+0061-007A;
}

@font-face {
  font-family: 'AlegreyaSans';
  src: url('@/assets/font/AlegreyaSans-ExtraBold.ttf') format('opentype'),
  url('@/assets/font/AlegreyaSans-ExtraBold.ttf') format('truetype');;
  font-weight: 800;
  unicode-range: U+0041-005A, U+0061-007A;
}

/* Noto Sans KR 구간 */
@font-face {
  font-family: 'NotoSansKR';
  src: url('@/assets/font/NotoSansKR-Regular.otf') format('opentype'),
  url('@/assets/font/NotoSansKR-Regular.otf') format('truetype');
  font-weight: 400;
  unicode-range: U+AC00-D7A3;
}

@font-face {
  font-family: 'NotoSansKR';
  src: url('@/assets/font/NotoSansKR-Medium.otf') format('opentype'),
  url('@/assets/font/NotoSansKR-Medium.otf') format('truetype');
  font-weight: 500;
  unicode-range: U+AC00-D7A3;
}

@font-face {
  font-family: 'NotoSansKR';
  src: url('@/assets/font/NotoSansKR-Bold.otf') format('opentype'),
  url('@/assets/font/NotoSansKR-Bold.otf') format('truetype');
  font-weight: 700;
  unicode-range: U+AC00-D7A3;
}


/* CSS 구간 */
* {
  margin: 0;
  padding: 0;
  font-family: 'AlegreyaSans', 'NotoSansKR', 'Apple SD Gothic Neo', sans-serif;
}

html, body {
  width: 100vw;
  height: 100%;
  background-color: #E8ECE6;
}
.main-enter-active,
.main-leave-active {
  transition: opacity .8s ease;
}
.main-enter-from,
.main-leave-to {
  opacity: 0;
}
</style>