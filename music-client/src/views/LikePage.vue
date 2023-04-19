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
import {HttpManager} from "@/api/request";
import {useLoginStore} from "@/store/login";
import {storeToRefs} from "pinia";

//<editor-fold desc="数据获取">
const loginStore = useLoginStore();
const {id} = storeToRefs(loginStore)
const musicRawData = ref([])
const playlistRawData = ref([])
const likeRawDate = ref([])
const isLoading = ref(true)
const getData = async () => {
  musicRawData.value = [];
  playlistRawData.value = [];
  likeRawDate.value = [];
  let ids:number[] = [];
  const like = await HttpManager.getCollectSong(id.value);
  if (!like.success) {
    ElMessage({
      message: "收藏列表获取失败",
      type: "error",
      duration: 1000,
      grouping: true
    })
    return
  }
  for (let item of like.data)
    ids.push(item.songId)
  const music = await HttpManager.getSongs(ids);
  const playlist = await HttpManager.getSongLists(ids);
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