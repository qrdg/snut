const path = require("path");
module.exports = {
  // outputDir: "../src/main/resources/static",
  // indexPath: "index.html",
  outputDir: path.resolve(__dirname, "../src/main/resources/static"),
  indexPath: path.resolve(__dirname, "./index.html"),
  devServer: {
      port : 9091   // 접속 포트 변경
    , proxy: "http://localhost:9091"     // devServer :  Back-End , 즉 Spring Boot의 내장 was의 주소를 작성하게 되면 된다.
  },
  chainWebpack: config => {
    const svgRule = config.module.rule("svg");
    svgRule.uses.clear();
    svgRule.use("vue-svg-loader").loader("vue-svg-loader");
  }
};