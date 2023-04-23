<template>
  <transition name="music-detail">
    <div class="music-detail" v-if="showLyric">
      <header class = "m-heard">
        <img src="../static/img/icons/收起.svg" alt=""
             @click="musicStore.toggleShowLyric()"
             style="position: absolute;width: 25px; margin: 20px;"/>
      </header>
      <el-skeleton :rows="5" animated :loading="isLoading"/>
      <div class="m-wrapper">
        <div class="m-left-content">
            <img :src="attachUrl(data.pic)" alt="" class="m-img">
            <div class="m-inner-name">
                {{data.name}}
            </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup lang="ts">
import {useMusicStore} from "@/store/music";
import {storeToRefs} from "pinia";
import {HttpManager} from "@/api/request";
import {attachUrl} from "@/utils";

const musicStore = useMusicStore();
const {songId, showLyric} = storeToRefs(musicStore)
//<editor-fold desc="获取数据">
const data = ref({});
const isLoading = ref(true);
const getData = async ()=>{
  const res = await HttpManager.getMusicById(songId.value);
  if (!res.success)
    ElMessage({
      message: "资源获取失败",
      type: "error",
      duration: 1000,
      grouping: true
    })
  else {
    data.value = res.data;
    isLoading.value = false;
  }
}
onMounted(() => {
  getData();
})
watch(songId, ()=>{
  getData()
})
//</editor-fold>
</script>

<style lang="scss" scoped>
.music-detail {
  background: #c7ecee;
  z-index: 100;
  position: absolute;
  width: 100%;
  height: 100%;
  .m-heard{
    height: 80px;
    display: flex;
    justify-content: left;
  }
  .m-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    margin:20px;
    height: 80%;
    width: 40%;
    .m-left-content{
      .m-img {
        width: 220px;
        height: 220px;
      }
      .m-inner-name{
        text-align: center;
        font-size: 20px;
        padding-top: 20px;
      }
    }

  }
}

.music-detail-enter-active {
  transition: all 0.3s ease;
}

.music-detail-leave-active {
  transition: all 0.3s ease;
}
.music-detail-enter-from,
.music-detail-leave-to {
  transform: translateY(20px);
  opacity: 0;
}

</style>