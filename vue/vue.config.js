module.exports = {
  //presets: [    '@vue/cli-plugin-babel/preset'  ]  // default code
  outputDir: "../src/main/resources/static",
  indexPath: "index.html",
  devServer: {
      port : 9091   // 접속 포트 변경
    , proxy: "http://localhost:9191"     // devServer :  Back-End , 즉 Spring Boot의 내장 was의 주소를 작성하게 되면 된다.
  },
  chainWebpack: config => {
    const svgRule = config.module.rule("svg");
    svgRule.uses.clear();
    svgRule.use("vue-svg-loader").loader("vue-svg-loader");
  }
};