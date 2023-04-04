module.exports = {
  "env": {
    "browser": true,
    "es2021": true,
    "node": true
  },
  "extends": [
    "eslint:recommended",
    "plugin:vue/vue3-essential",
    "plugin:@typescript-eslint/recommended"
  ],
  "overrides": [
  ],
  "parser": "vue-eslint-parser",
  "parserOptions": {
    "ecmaVersion": "latest",
    "parser": "@typescript-eslint/parser",
    "sourceType": "module"
  },
  "plugins": [
    "vue",
    "@typescript-eslint"
  ],
  "rules": {
    "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",
    // 以下为该项目自定义部分
    "eol-last": 0, //文件以单一的换行符结束 - 关闭
    "no-spaced-func": 1, //函数调用时 函数名与()之间不能有空格 - 开启
    "no-const-assign": 2, //禁止修改const声明的变量 - 开启
    "space-before-function-paren": [0, "always"], //函数定义时括号前面要有空格 - 关闭
    camelcase: 0, //强制驼峰法命名 - 关闭
    "no-undef": 0, //不能有未定义的变量 - 关闭
    "no-alert": 0, //禁止使用alert confirm prompt - 关闭
    "arrow-parens": 0, //箭头函数用小括号括起来 - 关闭
  }
}
