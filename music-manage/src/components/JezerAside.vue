<template>
  <div class="sidebar">
    <el-menu
      class="sidebar-el-menu"
      background-color=""
      active-text-color="#a4e5d9"
      default-active="2"
      router
      :collapse="collapse"
    >
      <el-menu-item index="dashboard">
        <el-icon>
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-yibiaopan"></use>
          </svg>
        </el-icon>
        <span>系统首页</span>
      </el-menu-item>
      <el-menu-item index="consumer">
        <el-icon>
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-yonghu"></use>
          </svg>
        </el-icon>
        <span>用户管理</span>
      </el-menu-item>
      <el-menu-item index="singer">
        <el-icon>
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-maikefeng"></use>
          </svg>
        </el-icon>
        <span>歌手管理</span>
      </el-menu-item>
      <el-menu-item index="songList">
        <el-icon>
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-yinlegedan-"></use>
          </svg>
        </el-icon>
        <span>歌单管理</span>
      </el-menu-item>
      <el-menu-item index="song">
        <el-icon>
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-gequ"></use>
          </svg>
        </el-icon>
        <span>歌曲管理</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import emitter from "@/utils/emitter";

const collapse = ref(false);
emitter.on("collapse", (msg) => {
  collapse.value = msg as boolean;
});
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 60px;
    bottom: 0;
    overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
    width: 0;
}

.sidebar > ul {
    height: 100%;
}

.sidebar-el-menu:not(.el-menu--collapse) {
    width: 150px;
}

.icon {
    width: 1em; height: 1em;
    vertical-align: -0.15em;
    fill: currentColor;
    overflow: hidden;
}



</style>
