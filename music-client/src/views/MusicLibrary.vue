<template>
    <div class="content-wrapper-header" style="margin-top: -10px;">
      <video src="@/static/mp4/B站.mp4" autoplay loop muted style="border-radius: 14px;width: 100%;"></video>
    </div>

    <div class="content-section">
      <JezerMusic :page-size="20" :is-loading="isLoading" :raw-data="musicRawData"/>
    </div>
</template>

<script setup lang="ts">
import {HttpManager} from "@/api/request";

//<editor-fold desc="数据获取">
const musicRawData = ref([])
const isLoading = ref(true)
const getData = async () => {
  musicRawData.value = [];
  const music = await HttpManager.getAllSong();
  if (!music.success)
    ElMessage({
      message: "资源获取失败",
      type: "error",
      duration: 1000,
      grouping: true
    })
  else {
    musicRawData.value = music.data;
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