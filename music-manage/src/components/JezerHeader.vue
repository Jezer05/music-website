<template>
  <div class="header">
    <div class="collapse-btn" @click="collapseChange">
      <el-icon v-if="!collapse" color="#58bfb7"><expand /></el-icon>
      <el-icon v-else color="#99bcac"><fold /></el-icon>
    </div>
    <div class="logo">{{ platformName }}</div>
    <div class="header-right">
      <div class="header-user-con">
        <el-avatar :size="50" :src="attachImageUrl(avatar)" />
        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
          <span class="el-dropdown-link">
            {{ username }}
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      </div>
  </div>
</template>
<script lang="ts">

import {useRouter} from "@/hooks/useRouter";
import { RouterName, PLATFORM_NAME } from "@/enums";
import {Expand, Fold} from "@element-plus/icons-vue";
import {useAdminStore} from "@/store/admin";
import {storeToRefs} from "pinia";
import emitter from "@/utils/emitter";
import {attachImageUrl} from "@/utils/picSupport";
const adminStore = useAdminStore();
export default defineComponent({
  name: "JezerHeader",
  components: {
    Expand,
    Fold,
  },
  setup() {
    const { routerManager } = useRouter();
    const collapse = ref(false);
    // 解构赋值且为响应式
    const {username, avatar} = storeToRefs(adminStore);
    const platformName = ref(PLATFORM_NAME);

    onMounted(() => {
      if (document.body.clientWidth < 1500) {
          collapseChange();
      }
    });

    // 侧边栏折叠
    function collapseChange() {
      collapse.value = !collapse.value;
      emitter.emit("collapse", collapse.value);
    }
    // 用户名下拉菜单选择事件
    function handleCommand(command: string|null) {
      if (command === "logout") {
        adminStore.logout();
        routerManager(RouterName.SignIn, { path: RouterName.SignIn });
      }
    }
    return {
      platformName,
      username,
      avatar,
      collapse,
      collapseChange,
      handleCommand,
      attachImageUrl: attachImageUrl,
    };
  },
});
</script>
<style scoped>
.header {
  position: absolute;
  z-index: 100;
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  font-family: LXGWWenKai, serif;
  font-size: 20px;
  color: #80A492;
  background: #ffff;
  box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.3);
}

.collapse-btn {
  display: flex;
  padding: 0 21px;
  cursor: pointer;
}

.header .logo {
  width: 250px;
  font-weight: bold;
}

.header-right {
  position: absolute;
  right: 0;
  padding-right: 30px;
}

.header-user-con {
  display: flex;
  align-items: center;
}

.user-name {
  margin-left: 10px;
}

.user-avatar img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.el-dropdown-link {
  cursor: pointer;
}

.el-dropdown-menu__item {
  text-align: center;
}
</style>
