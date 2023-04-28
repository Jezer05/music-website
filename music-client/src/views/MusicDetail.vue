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
            <div>
                <img :src="attachUrl(data.pic)" alt="" class="m-img">
                <div class="m-inner">
                    <div class="m-inner-item">
                        {{getSongTitle(data.name)}}
                    </div>
                    <div class="m-inner-item">
                        {{getSingerName(data.name)}}
                    </div>

                </div>
            </div>
        </div>
        <div class="m-right-content">
            <div class="song-lyric">
                <transition-group name="lyric-fade">
                    <!--有歌词-->
                    <ul :style="{ top: lrcTop }" class="has-lyric" v-if="lyric.length" key="has-lyric">
                        <li v-for="(item, index) in lyric" :key="index">
                            {{ item[1] }}
                        </li>
                    </ul>
                    <!--没歌词-->
                    <div v-else class="no-lyric" key="no-lyric">
                        <span>暂无歌词</span>
                    </div>
                </transition-group>
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
import {attachUrl, getSingerName, getSongTitle, parseLyric} from "@/utils";

const musicStore = useMusicStore();
const {songId, showLyric,curTime,songPic} = storeToRefs(musicStore)
//<editor-fold desc="获取数据">
const data = ref({});
const lyric = ref([]);
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
    // @ts-ignore
    lyric.value = parseLyric(data.value.lyric)
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

//<editor-fold desc="歌词">
const lrcTop = ref("80px");
watch(curTime, () => {
  // 避免未展示时就进行加载，会找不到对应的DOM元素
  if (showLyric.value) {
    if (lyric.value.length !== 0) {
      for (let i = 0; i < lyric.value.length; i++) {
        if (curTime.value >= lyric.value[i][0]) {
          for (let j = 0; j < lyric.value.length; j++) {
            (document.querySelectorAll(".has-lyric li") as NodeListOf<HTMLElement>)[j].style.color = "#000";
            (document.querySelectorAll(".has-lyric li") as NodeListOf<HTMLElement>)[j].style.fontSize = "20px";
          }
          if (i >= 0) {
            lrcTop.value = -i * 30 + 50 + "px";
            (document.querySelectorAll(".has-lyric li") as NodeListOf<HTMLElement>)[i].style.color = "#95d2f6";
            (document.querySelectorAll(".has-lyric li") as NodeListOf<HTMLElement>)[i].style.fontSize = "26px";
          }
        }
      }
    }
  }
});
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
    align-items: center;
    margin:20px;
    height: 80%;
    .m-left-content{
      margin-left: 200px;
      width: 22%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-wrap: wrap;
      .m-img {
        width: 220px;
        height: 220px;
      }
      .m-inner{
        text-align: center;
        font-size: 20px;
        padding-top: 20px;
      }
    }
    .m-right-content {
      display: flex;
      align-items: center;
      width: 50%;
      height: 80%;
      .song-lyric {
        display: flex;
        width: 100%;
        height: 100%;
        justify-content: center;
        position: relative;
        min-height: 300px;
        padding: 30px 0;
        overflow: auto;
        border-radius: 12px;

        .has-lyric {
          position: absolute;
          transition: all 1s;

          li {
            width: 100%;
            height: 40px;
            text-align: center;
            font-size: 20px;
            line-height: 40px;
            list-style: none;
            user-select: none;
          }
        }

        .no-lyric {
          position: absolute;
          margin: 100px 0;

          span {
            font-size: 18px;
            text-align: center;
          }
        }
      }
    }
  }
}
.lyric-fade-enter,
.lyric-fade-leave-to {
  transform: translateX(30px);
  opacity: 0;
}

.lyric-fade-enter-active,
.lyric-fade-leave-active {
  transition: all 0.3s ease;
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