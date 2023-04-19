<script setup lang="ts">
import IndexPage from '@/views/IndexPage.vue'
import {useLoginStore} from "@/store/login";
import {storeToRefs} from "pinia";
import {HttpManager} from "@/api/request";
import emitter from "@/utils/emitter";
// TODO: 主题变换监听器

//<editor-fold desc="收藏歌曲">
const loginStore = useLoginStore();
const {isLogin, id} = storeToRefs(loginStore);
let songLikes:number[] = [];
const getCollectSong = async () =>{
  if(!isLogin) return
  const result =await HttpManager.getCollectSong(id.value);
  if(!result.success) return;

  for (let item of result.data)
    songLikes.push(item.id);
  loginStore.setSongLikes(songLikes);
}
onMounted(() => {
  getCollectSong();
})
watch(id, () => {
  getCollectSong();
})
emitter.on("addSongLike",  async (songId) => {
  const result = await HttpManager.addCollectSong(id.value, songId as number);
  if (!result.success){
    ElMessage.error("歌曲收藏失败");
    return
  }
  loginStore.addSongLike(songId as number);
})

emitter.on("deleteSongLike", async (songId) => {
  console.log(songId)
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
</script>

<template>
  <IndexPage/>
</template>
