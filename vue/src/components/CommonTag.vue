<template>
  <div class="tag" :style="{ 
    width: width+'px', 
    height: height+'px', 
    marginTop: marginTop+'px' , 
    marginRight: marginRight+'px' }">
    {{ '#' + tagName }}
  </div>
</template>

<script>
export default {
  name: "CommonButton",
  props: ['tagName', 'width', 'height', 'margin-top', 'margin-right'],
}
</script>

<style scoped>
.tag {  
  min-width: 80px;
  min-height: 35px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px black solid;
  border-radius: 20px;
  font-weight: bold;
  font-size: 18px;
  cursor: pointer;
}
</style>