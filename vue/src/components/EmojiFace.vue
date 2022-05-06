<template>
  <svg :width="width" :height="height" viewBox="0 0 112 116" fill="none" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" clip-rule="evenodd" d="M51.0751 0.00157068C67.4647 -0.102846 83.8031 4.99419 95.3815 16.6822C106.95 28.3602 113.155 44.8781 111.822 61.3228C110.576 76.6889 100.108 88.9581 88.6898 99.2213C78.0607 108.775 65.3067 115.326 51.0751 115.923C36.1902 116.547 20.3372 113.506 10.1958 102.51C0.296765 91.7781 1.208 75.9656 0.71509 61.3228C0.195372 45.8838 -2.44893 29.145 7.32544 17.2432C17.7031 4.60669 34.7967 0.105278 51.0751 0.00157068Z" 
        :fill="color"/>
  </svg>
</template>

<script>
export default {
  name: 'EmojiFace',
  props: ['color', 'width', 'height']
}
</script>

<style>

</style>