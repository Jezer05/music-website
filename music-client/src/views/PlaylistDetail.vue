<template>
  <div class="content-section menuBar-mv">
      <div class="m-artist">
          <el-skeleton :rows="5" animated :loading="isLoading" />
          <div class="artist-avatar">
              <img :src="attachUrl(playlistDetail.pic)" alt="" class="pllist" v-if="!isLoading">
          </div>
          <div class="artist-name">
              <div class="playlist-title" v-if="!isLoading">
                  <h2>{{ playlistDetail.title }}</h2>
                  <el-button color="#729bed" round class="m-lib-btn" @click="setPlaylist">立即播放</el-button>
              </div>
              <div class="tags" v-if="!isLoading">
                  <ul>
                      <span>风格：</span>
                      <li >{{ playlistDetail.style }}</li>
                  </ul>
              </div>
              <p> {{ playlistDetail.introduction }}</p>
          </div>
      </div>
      <JezerMusic :isLoading="isLoading" :rawData="songListRawData" :pageSize="15"/>
  </div>
</template>

<script setup lang="ts">

//<editor-fold desc="数据展示">
import {PlaylistDetail} from "@/api/type";
import {HttpManager} from "@/api/request";
import {attachUrl} from "@/utils";
import {MusicDetail, useMusicStore} from "@/store/music";
import {storeToRefs} from "pinia";

const {proxy} = getCurrentInstance()!;
const playlistId:any = ref(proxy?.$route.query.playlistId)
let playlistDetail = reactive<PlaylistDetail>(
    {
      id: -1,
      title:"",
      pic:"",
      introduction:"",
      style: ""
    }
);
const songListRawData = ref([]);
const commentRawData = ref([]);
const isLoading = ref(true)
const getData = async () => {
  const detail = await HttpManager.getSongListById(playlistId.value);
  const songList = await HttpManager.getListSongByListId(playlistId.value);
  const comment = await HttpManager.getCommentByListId(playlistId.value);
  if(!detail.success || !songList.success || !comment.success)
    ElMessage({
      message: "资源获取失败",
      type: "error",
      duration: 1000,
      grouping: true
    })
  else {
    playlistDetail = detail.data
    songListRawData.value = songList.data
    commentRawData.value = comment.data
    isLoading.value = false;
  }
}
onMounted(() => {
  getData();
})
//</editor-fold>

//<editor-fold desc="歌单替换">
const musicStore = useMusicStore();
const {playMode} = storeToRefs(musicStore);
const setPlaylist = () => {
  let list : Array<MusicDetail> = [];
  let index = 0;
  for (let item of songListRawData.value){
    const {id:songId, name:songTitle, url:songUrl, pic:songPic} = item;
    list.push({songId, songTitle, songUrl, songPic})
    if (playMode.value === 1)
      index = Math.floor(Math.random() * songListRawData.value.length)
    musicStore.setCurPlayList(list as MusicDetail[], index);
    musicStore.setCurrentMusic(list[index] as MusicDetail);
  }
}
//</editor-fold>
</script>

<style lang="scss" scoped>
.playlist-title {
  display: flex;
  align-items: center;
}
</style>