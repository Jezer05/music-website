<template>
  <div class="header">
    <div class="menu-circle"></div>
      <div class="header-menu">
        <a class="menu-link is-active" href="javascript:" @click="back">
          <svg t="1648618382129" class="icon" viewBox="0 0 1024 1024" version="1.1"
            xmlns="http://www.w3.org/2000/svg" p-id="2830" width="32" height="32">
            <path
                d="M576 672c-6.4 0-19.2 0-25.6-6.4l-128-128c-12.8-12.8-12.8-32 0-44.8l128-128c12.8-12.8 32-12.8 44.8 0s12.8 32 0 44.8L492.8 512l102.4 102.4c12.8 12.8 12.8 32 0 44.8C595.2 672 582.4 672 576 672z"
                p-id="2831" fill="#525B72"/>
          </svg>
        </a>
        <a class="menu-link is-active" href="javascript:" @click="forward">
          <svg t="1648618362955" class="icon" viewBox="0 0 1024 1024" version="1.1"
               xmlns="http://www.w3.org/2000/svg" p-id="2626" width="32" height="32">
              <path
                d="M448 672c-6.4 0-19.2 0-25.6-6.4-12.8-12.8-12.8-32 0-44.8L531.2 512 422.4 409.6c-12.8-12.8-12.8-32 0-44.8s32-12.8 44.8 0l128 128c12.8 12.8 12.8 32 0 44.8l-128 128C467.2 672 454.4 672 448 672z"
                p-id="2627" fill="#525B72"/>
          </svg>
        </a>
      </div>

    <div class="search-bar">
      <input type="text" placeholder="Search" @keyup.enter='goSearchDetail' v-model='keyword'>
    </div>

    <div class="header-profile" v-if="isLogin">
      <el-dropdown>
        <img class="profile-img" :src="attachUrl(avatar)" alt="">
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="logout">注销 </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <div class="username">{{ username }}</div>
    </div>

    <div class="header-profile" v-if="!isLogin">
      <div class="username"> <a href="javascript:" @click="loginVisible = true">登录</a> </div>
        <div class="card" v-show="loginVisible">
          <div class="tools">
            <div class="circle">
              <span @click="loginVisible = false" class="red box"></span>
            </div>
            <div class="circle">
              <span class="yellow box"></span>
            </div>
             <div class="circle">
               <span class="green box"></span>
             </div>
          </div>
          <div class="card__content">
            <el-tabs v-model="activeName" class="login-tabs" stretch>
              <el-tab-pane label="登录" name="login">
                  <div class="demo-login">
                      <input type="text" v-model="loginForm.username" class="demo-text" placeholder="请输入用户名">
                      <input type="password" v-model="loginForm.password" class="demo-text" placeholder="请输入密码">
                      <div class="input-text">
                          <button @click="login"> 登录 </button>
                      </div>
                  </div>
              </el-tab-pane>
              <el-tab-pane label="注册" name="register">
                  <div class="demo-login">
                      <input type="text" v-model="loginForm.username" class="demo-text" placeholder="请输入用户名">
                      <input type="password" v-model="loginForm.password" class="demo-text" placeholder="请输入密码">
                      <div class="input-text">
                        <button @click="register"> 注册 </button>
                      </div>
                  </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </div>
    </div>

  </div>
</template>

<script setup lang="ts">
import {storeToRefs} from "pinia";
import {useLoginStore} from "@/store/login";
import {attachUrl} from "@/utils";
import {HttpManager} from "@/api/request";

//<editor-fold desc="路由跳转">
const router = useRouter()
const back = () => {
  router.back()
}
const forward = () => {
  router.forward()
}
const goSearchDetail = () => {
  router.push({ name: 'search', query: { keyword: keyword.value } })
  keyword.value = "";
}
//</editor-fold>

//<editor-fold desc="搜索">
const keyword = ref("");
//</editor-fold>

//<editor-fold desc="右上角显示内容">
const loginStore = useLoginStore()
const {username,avatar,isLogin} = storeToRefs(loginStore)
const logout = () => {
  router.push({name: "home"})
  loginStore.logout()
}
//</editor-fold>

//<editor-fold desc="登录/注册">
const loginVisible = ref(false);
const activeName = ref('login')
const loginForm = reactive({
  username: "",
  password: ""
})
const login = async () => {
  if (loginForm.username.trim() === "" || loginForm.password.trim() === "") {
    ElMessage({
      message: "用户名和密码不能为空",
      type: "error",
      duration: 1000,
      grouping: true
    })
    return
  }
  const result = await HttpManager.login(loginForm)
  if (result.success) {
    loginStore.login(result.data);
    loginVisible.value = false;
  }
  ElMessage({
    message: result.message,
    type: result.type,
    duration: 1000,
    grouping: true
  })
}
const register = async () => {
  if (loginForm.username.trim() === "" || loginForm.password.trim() === "") {
    ElMessage({
      message: "用户名和密码不能为空",
      type: "error",
      duration: 1000,
      grouping: true
    })
    return
  }
  const result = await HttpManager.register(loginForm)
  if (result.success) {
    const res = await HttpManager.login(loginForm);
    loginStore.login(res.data);
    loginVisible.value = false;
  }
  ElMessage({
    message: result.message,
    type: result.type,
    duration: 1000,
    grouping: true
  })
}
//</editor-fold>


</script>

<style lang='scss' scoped>
.demo-login {
    padding: 50px 0px;
    display: flex;
    flex-direction: column;

    .demo-text {
        height: 35px;
        margin: 10px 0px;
        border: 1px solid var(--border-color);
        border-radius: 10px;
        padding-left: 10px;

        & :active {
            box-shadow: 0 5px 16px rgb(0 0 0 / 10%);

        }

        &::placeholder {
            font-family: var(--body-font);
            color: var(--inactive-color);
            font-size: 15px;
            font-weight: 500;
        }
    }
}

.login-tabs {
    margin-top: 20px;

}

.input-text button {
    font-size: 16px;
    padding: 1em 3.3em;
    transform: perspective(200px) rotateX(15deg);
    color: white;
    font-weight: 900;
    border: none;
    border-radius: 5px;
    background: linear-gradient(0deg, rgba(63, 94, 251, 1) 0%, rgba(70, 135, 252, 1) 100%);
    box-shadow: rgba(63, 94, 251, 0.2) 0px 30px 10px 0px;
    margin-left: 30px;
    margin-top: 20px;
    will-change: transform;
    transition: all 0.3s;
    border-bottom: 2px solid rgba(70, 135, 252, 1);
}

.input-text button:hover {
    transform: perspective(180px) rotateX(30deg) translateY(2px);
}

.input-text button:active {
    transform: perspective(170px) rotateX(36deg) translateY(5px);
}


.card {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 550px;
    height: 500px;
    transform: translateX(-50%) translatey(-50%);
    background-color: #fff;
    border-radius: 30px;
    box-shadow: 0 5px 16px rgb(0 0 0 / 10%);
    z-index: 3;
    background-image: url(@/static/img/login.png);
    background-size: 150px;
    background-repeat: no-repeat;
    background-position: bottom left;
}

.tools {
    display: flex;
    align-items: center;
    padding: 15px 20px;
    border-bottom: 1px solid var(--border-color);
}

.circle {
    padding: 0 4px;
}

.box {
    display: inline-block;
    align-items: center;
    width: 10px;
    height: 10px;
    padding: 1px;
    border-radius: 50%;
}

.red {
    background-color: #ff605c;
    width: 15px;
    height: 15px;
}

.yellow {
    background-color: #ffbd44;
    width: 15px;
    height: 15px;
}

.green {
    background-color: #00ca4e;
    width: 15px;
    height: 15px;
}

.card__content {
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>