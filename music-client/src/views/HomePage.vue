<template>
  <div class="content-wrapper-header" style="margin-top: -10px;">
    <video src="@/static/mp4/B站.mp4" autoplay loop muted style="border-radius: 14px;width: 100%;"></video>
  </div>

  <div class="content-section">
    <el-tabs v-model="activeName" class="demo-tabs">
      <el-tab-pane label="歌曲" name="music">
        <JezerMusic :page-size="20" :is-loading="isLoading" :raw-data="musicRawData"/>
      </el-tab-pane>
      <el-tab-pane label="歌单" name="playlist">
        <JezerPlaylist :pageSize = "20" :is-loading="isLoading" :raw-data="playlistRawData"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import JezerMusic from "@/components/JezerMusic.vue";
import {HttpManager} from "@/api/request";
const activeName = ref("music");
//<editor-fold desc="数据获取">
const musicRawData = ref([])
const playlistRawData = ref([])
const isLoading = ref(true)
const getData = async () => {
  musicRawData.value = [];
  playlistRawData.value = [];
  const music = await HttpManager.getAllSong();
  const playlist = await HttpManager.getAllSongList();
  if (!music.success || !playlist.success)
    ElMessage({
      message: "资源获取失败",
      type: "error",
      duration: 1000,
      grouping: true
    })
  else {
    musicRawData.value = music.data;
    playlistRawData.value = playlist.data;
    isLoading.value = false;
  }
}
onMounted(() => {
  getData();
})
//</editor-fold>

</script>

<style lang="scss" >
.el-tabs__nav-wrap::after {
    background-color: transparent;
}
</style>