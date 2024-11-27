(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7863cdf7"],{"985c":function(t,e,s){"use strict";s("d717")},"9ed6":function(t,e,s){"use strict";s.r(e);var o=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"login-container"},[s("div",{staticStyle:{display:"flex",width:"500px",height:"400px",margin:"auto",position:"absolute",top:"0",left:"0",right:"0",bottom:"0"}},[s("el-form",{ref:"loginForm",staticClass:"login-form",attrs:{model:t.loginForm,rules:t.loginRules,"auto-complete":"on","label-position":"left"}},[s("div",{staticClass:"title-container"},[s("h3",{staticClass:"title"},[t._v("登录图书管理系统")])]),s("el-form-item",{attrs:{prop:"userName"}},[s("span",{staticClass:"svg-container"},[s("i",{staticClass:"el-icon-a-052",staticStyle:{"font-size":"24px"}})]),s("el-input",{ref:"userName",staticClass:"yuan",attrs:{placeholder:"请输入用户名",name:"userName",type:"text",tabindex:"1","auto-complete":"on"},model:{value:t.loginForm.userName,callback:function(e){t.$set(t.loginForm,"userName",e)},expression:"loginForm.userName"}})],1),s("el-form-item",{attrs:{prop:"password"}},[s("span",{staticClass:"svg-container"},[s("i",{staticClass:"el-icon-a-051",staticStyle:{"font-size":"24px"}})]),s("el-input",{key:t.passwordType,ref:"password",staticClass:"yuan",attrs:{type:t.passwordType,placeholder:"请输入密码",name:"password",tabindex:"2","auto-complete":"on"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleLogin(e)}},model:{value:t.loginForm.password,callback:function(e){t.$set(t.loginForm,"password",e)},expression:"loginForm.password"}}),s("span",{staticClass:"show-pwd",on:{click:t.showPwd}},[s("svg-icon",{attrs:{"icon-class":"password"===t.passwordType?"eye":"eye-open"}})],1)],1),s("el-form-item",{attrs:{prop:"authority"}},[s("span",{staticClass:"svg-container"},[s("i",{staticClass:"iconfont icon-r-setting",staticStyle:{"font-size":"24px"}})]),s("el-select",{staticStyle:{width:"300px"},attrs:{placeholder:"请选择"},model:{value:t.loginForm.isAdmin,callback:function(e){t.$set(t.loginForm,"isAdmin",e)},expression:"loginForm.isAdmin"}},[s("el-option",{key:0,attrs:{label:"读者",value:0}}),s("el-option",{key:1,attrs:{label:"管理员",value:1}})],1)],1),s("div",{staticStyle:{height:"40px","margin-bottom":"30px"}},[s("el-button",{staticStyle:{width:"48%",float:"left","font-size":"22px"},attrs:{disabled:t.loading,type:"primary"},nativeOn:{click:function(e){return e.preventDefault(),t.handleLogin(e)}}},[t._v(" 登录 ")]),s("el-button",{staticStyle:{width:"48%",float:"right","font-size":"22px"},attrs:{disabled:t.loading,type:"success"},nativeOn:{click:function(e){return e.preventDefault(),t.handleRegister(e)}}},[t._v(" 注册 ")])],1)],1)],1)])},n=[],a={name:"Login",data:function(){var t=function(t,e,s){s()},e=function(t,e,s){s()};return{loginForm:{userName:"",password:"",isAdmin:0},loginRules:{userName:[{required:!0,trigger:"blur",validator:t}],password:[{required:!0,trigger:"blur",validator:e}]},loading:!1,passwordType:"password",redirect:void 0}},updated:function(){},methods:{showPwd:function(){var t=this;"password"===this.passwordType?this.passwordType="":this.passwordType="password",this.$nextTick((function(){t.$refs.password.focus()}))},handleLogin:function(){var t=this;this.$refs.loginForm.validate((function(e){if(!e)return console.log("不允许提交!"),!1;t.loading=!0,t.$store.dispatch("user/login",t.loginForm).then((function(){t.$router.push({path:"/"}),t.loading=!1})).catch((function(e){console.log(e),t.loading=!1,void 0==e.response||void 0==e.response.data?t.$message({showClose:!0,message:e,type:"error",duration:5e3}):t.$message({showClose:!0,message:e.response.data,type:"error",duration:5e3})}))}))},handleRegister:function(){console.log("注册按钮"),this.$router.push({path:"/register"})}},mounted:function(){}},i=a,r=(s("985c"),s("fd3a"),s("2877")),l=Object(r["a"])(i,o,n,!1,null,"149c454f",null);e["default"]=l.exports},bb52:function(t,e,s){},d717:function(t,e,s){},fd3a:function(t,e,s){"use strict";s("bb52")}}]);