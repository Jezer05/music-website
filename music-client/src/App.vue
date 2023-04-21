<script setup lang="ts">
import IndexPage from '@/views/IndexPage.vue'
import {useLoginStore} from "@/store/login";
import {storeToRefs} from "pinia";
import {HttpManager} from "@/api/request";
import emitter from "@/utils/emitter";
import {useThemeStore} from "@/store/theme";


//<editor-fold desc="收藏歌曲">
const loginStore = useLoginStore();
const {isLogin, id} = storeToRefs(loginStore);

const getCollect = async () =>{
  const songLikes:number[] = [];
  const playlistLikes: number[] = [];
  if(!isLogin.value) return
  const songCollect =await HttpManager.getCollectSong(id.value);
  const listCollect = await HttpManager.getCollectList(id.value);
  if(!songCollect.success || !listCollect.success) return;
  for (let item of songCollect.data)
    songLikes.push(item.id);
  for (let item of listCollect.data)
    playlistLikes.push(item.id);
  loginStore.setSongLikes(songLikes);
  loginStore.setPlaylistLikes(playlistLikes);
}
onMounted(() => {
  getCollect();
})

// 监听pinia中id的变化
watch(id, (newValue, oldValue) => {
  getCollect()
})

// 歌曲收藏
emitter.on("addSongLike",  async (songId) => {
  const result = await HttpManager.addCollectSong(id.value, songId as number);
  if (!result.success){
    ElMessage.error("歌曲收藏失败");
    return
  }
  loginStore.addSongLike(songId as number);
})
// 取消收藏
emitter.on("deleteSongLike", async (songId) => {
  const res = await HttpManager.delCollectSong(id.value, songId as number)
  if (!res.success){
    ElMessage.error("取消收藏失败");
    return
  }
  loginStore.deleteSongLike(songId as number);
})
onBeforeUnmount(()=>{
  emitter.all.clear()
})
//</editor-fold>

//<editor-fold desc="设置主题">
const themeStore = useThemeStore()
const {backgroundColor} = storeToRefs(themeStore)
const setThemeColor = () => {
  document.querySelector("body")!.setAttribute("style", `background-color:${backgroundColor.value};transition: 0.7s;`);
}
onMounted(()=>{
  setThemeColor()
})
//</editor-fold>

</script>

<template>
  <IndexPage/>
</template>
