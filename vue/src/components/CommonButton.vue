<template>
  <div class="button" :style="{ 
    width: width+'px', 
    height: height+'px', 
    marginTop: marginTop+'px' , 
    marginRight: marginRight+'px',
    marginLeft:marginLeft+'px',
    background: background,
    fontSize: fontSize+'px',
    borderRadius: borderRadius+'px',
    border: border,
    color: color,
    margin: margin+'px',
    fontWeight: fontWeight,
    boxShadow: boxShadow

  }">
    {{ buttonName }}
  </div>
</template>

<script>
export default {
  name: "CommonButton",
  props: [
          'buttonName',
          'width',
          'height',
          'margin-top',
          'margin-right',
          'margin-left',
          'background',
          'fontSize',
          'borderRadius',
          'border',
          'color',
          'margin',
        'fontWeight',
      'boxShadow'

        ]
}
</script>

<style scoped>
.button {  
  font-family: AlegreyaSans, sans-serif;
  /*min-width: 80px;*/
  min-height: 35px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px black solid;
  border-radius: 20px;
  font-weight: bold;
  font-size: 14px;
  cursor: pointer;
}
</style>