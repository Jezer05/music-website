<template>
    <div class="content-wrapper-header" style="margin-top: -10px;">
        <video src="@/static/mp4/B站.mp4" autoplay loop muted style="border-radius: 14px;width: 100%;"></video>
    </div>

    <div class="content-section">
        <JezerPlaylist :pageSize = "20" :is-loading="isLoading" :raw-data="playlistRawData"/>
    </div>
</template>

<script setup lang="ts">
import {HttpManager} from "@/api/request";
//<editor-fold desc="数据获取">
const playlistRawData = ref([])
const isLoading = ref(true)
const getData = async () => {
  playlistRawData.value = [];
  const playlist = await HttpManager.getAllSongList();
  if (!playlist.success)
    ElMessage({
      message: "资源获取失败",
      type: "error",
      duration: 1000,
      grouping: true
    })
  else {
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
</style>