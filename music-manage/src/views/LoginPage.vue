<template>
  <div class="login-container">
    <div class="title">{{ platformName }}</div>
    <div class="login">
      <el-form :model="loginForm" :rules="rules" label-width="22%" hide-required-asterisk>
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

<script lang="ts">
import {RouterName, PLATFORM_NAME} from "@/enums";
import {useRouter} from "@/hooks/useRouter";
import {login} from "@/api/login";
import {useAdminStore} from "@/store/admin";
const adminStore = useAdminStore();
export default defineComponent({
  name: "LoginPage",
  setup() {
    // 需要放在setup内，这样routerManager中才能获取到proxy
    const { routerManager } = useRouter();
    const platformName = ref(PLATFORM_NAME)
    const loginForm = reactive({
      username: "",
      password: "",
    });
    const rules = reactive({
      username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
      password: [{ required: true, message: "请输入密码", trigger: "blur" }],
    });
    const submitForm = async () =>{
        const result = (await login(loginForm));
        console.log(result);
        if (result.data != null){
            adminStore.login(result.data);
        }
        ElMessage({
            message: result.message,
            type: result.type
        });
        if (result.success) routerManager(RouterName.Info, { path: RouterName.Info });
    }
    return {
      platformName,
      loginForm,
      rules,
      submitForm
    }
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/login.scss";
</style>
