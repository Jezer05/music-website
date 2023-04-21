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
                <div class="content-section menuBar-mv">
                    <div class="apps-card">
                        <el-skeleton :rows="5" animated :loading="isLoading" />
                        <div class="apps-item" v-for="(item,index) in playlistRawData" :key="index" @click="goPlaylist(item.id)">
                            <div class="app-card" style="position: relative" v-if="listLikes.includes(item.id)">
                                <img :src="attachUrl(item.pic)" alt="">
                                <img src="@/static/img/icons/红爱心.svg"
                                     style="width: 32px;height: 32px;position: absolute"
                                     alt="" @click.stop = "delCollectList(item.id)">
                                <a href="javascript:">{{item.title}}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script setup lang="ts">
import {HttpManager} from "@/api/request";
import {useLoginStore} from "@/store/login";
import {storeToRefs} from "pinia";
import {attachUrl} from "@/utils";

//<editor-fold desc="数据获取">
const activeName = ref("music");
const loginStore = useLoginStore();
const {id, playlistLikes, songLikes,isLogin} = storeToRefs(loginStore)
const musicRawData = ref([])
const playlistRawData = ref([])
const listLikes: any = ref([])
const isLoading = ref(true)
const getData = async () => {
  if(!isLogin.value) return
  musicRawData.value = [];
  playlistRawData.value = [];
  const songCollect = await HttpManager.getCollectSong(id.value);
  const listCollect = await HttpManager.getCollectList(id.value);
  if (!songCollect.success || !listCollect.success) {
    ElMessage({
      message: "收藏列表获取失败",
      type: "error",
      duration: 1000,
      grouping: true
    })
    return
  }
  musicRawData.value = songCollect.data;
  playlistRawData.value = listCollect.data;
  for (let item of playlistRawData.value)
      listLikes.value.push(item.id);
  isLoading.value = false
}
onMounted(() => {
  getData();
})

// 要加deep，这是bug
watch(songLikes, ()=> {
  getData();
},{deep:true})
const delCollectList = async (listId: number) => {
    const res = await HttpManager.delCollectList(id.value,listId);
    if (res.success){
        const index = listLikes.value.indexOf(listId)
        if (index >= 0)
            listLikes.value.splice(index,1)
    }
}
//</editor-fold>

//<editor-fold desc="路由跳转">
const router = useRouter();
const goPlaylist = (id:number) => {
    router.push({ name: "playlistDetail", query: { playlistId: id } })
}
//</editor-fold>

</script>

<style lang="scss" >
.el-tabs__nav-wrap::after {
  background-color: transparent;
}
</style>