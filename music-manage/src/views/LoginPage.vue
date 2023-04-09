<template>
  <div class="login-container">
    <div class="title">{{ platformName }}</div>
    <div class="login">
        <el-form :model="loginForm" :rules="<FormRules>rules" label-width="22%" hide-required-asterisk>
        <el-form-item prop="username" label="用户名">
          <el-input v-model="loginForm.username" placeholder="username"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input type="password" placeholder="password" v-model="loginForm.password" @keyup.enter="submitForm"></el-input>
        </el-form-item>
        <el-button type="primary" @click="submitForm" class="login-btn">登录</el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import {RouterName, PLATFORM_NAME} from "@/enums";
import {useRouter} from "@/hooks/useRouter";
import {HttpManager} from "@/api/request";
import {useAdminStore} from "@/store/admin";
import {LoginReqForm} from "@/api/type";
import {FormRules} from "element-plus";
const adminStore = useAdminStore();
// 需要放在setup内，这样routerManager中才能获取到proxy
const { routerManager } = useRouter();


const platformName = ref(PLATFORM_NAME)
const loginForm : LoginReqForm = reactive({
  username: "",
  password: "",
});
const rules = reactive({
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
});
const submitForm = async () =>{
  if (loginForm.username.trim() == '' || loginForm.password == ''){
    ElMessage.error("用户名和密码不能为空")
    return
  }
  const result = (await HttpManager.login(loginForm));
  if (result.data != null){
      adminStore.login(result.data);
  }
  ElMessage({
      message: result.message,
      type: result.type
  });
  if (result.success) routerManager(RouterName.Info, { path: RouterName.Info });
}
</script>

<style lang="scss" scoped>
@import "@/assets/css/login.scss";
</style>
