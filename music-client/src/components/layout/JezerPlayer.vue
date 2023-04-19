<template>
    <div class="player-box">
      <audio :src="attachUrl(<string>songUrl)" ref="audio" @timeupdate="timeupdate"
        @canplay="canPlay" @ended="ended" style="display: none"></audio>
      <div class="m-tandc">
        <img :src="attachUrl(<string>songPic)" alt="">
        <div class="titleandsinger">
          <a href="javascript:" style=" color: #000;">{{ songTitle }}</a>
        </div>
      </div>
      <div class="btns">
        <!-- <h1>{{ showname }}</h1> -->
        <div class="aplayer">
          <!-- 歌曲播放控制 -->
          <div class="m-btns">
            <a  href="javascript:" @click="changeMode">
              <img v-if="playMode === 0" src="@/static/img/icons/序列.svg" alt="" class="Musicice" />
              <img v-else-if="playMode === 1" src="@/static/img/icons/随机.svg" alt="" class="Musicice" />
              <img v-else-if="playMode === 2" src="@/static/img/icons/单曲循环.svg" alt="" class="Musicice" />
            </a>
            <a href="javascript:" @click="previousMusic">
              <img src="@/static/img/icons/上一首.svg" alt="" class="Musicice" />
            </a>
            <a href="javascript:" @click="togglePlay">
              <img src="@/static/img/icons/播放.svg" v-if="!isPlay" alt="" />
              <img src="@/static/img/icons/暂停.svg" v-else alt="" />
            </a>
            <a href="javascript:" @click="nextMusic(1)">
              <img src="@/static/img/icons/下一首.svg" alt="" class="Musicice" />
            </a>
          </div>
          <div class="slider-demo-block right-voice">
            <a href="javascript:"><img src="@/static/img/icons/声音.svg" alt="" width="20"></a>
            <!-- 音量条 -->
            <el-slider v-model="volume" @change="changeVolume" style="width: 70px" :show-tooltip="true" />
            <!-- TODO: 播放列表设计-->
            <a href="javascript:" @click="">
              <span class="notification-number">{{ curPlayList.length }}</span>
              <img src="@/static/img/icons/列表.svg" alt="" class="Musicice" />
            </a>
          </div>

        </div>
<!--         进度条-->
        <div class="m-slider">
          <span type="info" style="margin-left: 18px;">{{ startTime }}</span>
          <div class="slider-demo-block">
            <el-slider v-model="progress" :show-tooltip="false" @change="changeTime" style="width: 400px" />
          </div>
          <span type="info" style="margin-left: 18px;">{{ endTime }}</span>
        </div>
      </div>
    </div>
</template>

<script setup lang="ts">
import {attachUrl, formatSeconds} from "@/utils";
import {useMusicStore} from "@/store/music";
import {storeToRefs} from "pinia";

const musicStore = useMusicStore();
const audio = ref<HTMLAudioElement>()
const {songUrl, isPlay, playMode, curPlayIndex, curPlayList,songPic, songTitle} = storeToRefs(musicStore);

//<editor-fold desc="原生音乐播放器">
const duration = ref(0)
const curTime = ref(0)
watch(isPlay, () =>{
  isPlay.value ? audio.value?.play() : audio.value?.pause();
})
const canPlay = () =>{
  duration.value = audio.value!.duration
  if (isPlay.value)
    audio.value?.play()
}
const timeupdate = () => {
  curTime.value = audio.value!.currentTime
}
const ended = () => {
  musicStore.pause();
  curTime.value = 0;
  nextMusic(0);
}

//</editor-fold>

//<editor-fold desc="播放模式">
const changeMode = () => {
  musicStore.changePlayMode();
}
//</editor-fold>

//<editor-fold desc="音量">
const volume = ref(100);
const changeVolume = () => {
  audio.value!.volume = volume.value / 100;
}
onMounted(() => {
  audio.value!.volume = volume.value / 100;
})
//</editor-fold>

//<editor-fold desc="进度条">
const startTime = ref("00:00")
const endTime = ref("00:00")
const progress = ref(0);
watch(curTime, () => {
  startTime.value = formatSeconds(curTime.value);
  endTime.value = formatSeconds(duration.value);
})
const changeTime = () => {
  // 不需要给curTime复制，已经配置了监听
  audio.value!.currentTime = progress.value * duration.value /100
}
watch(curTime, (newVal) => {
  progress.value = newVal / duration.value * 100
})
//</editor-fold>

//<editor-fold desc="音乐控制">
// 用来区别切换下一首的形式，是自动进入下一首还是点击进入，主要用于单曲循环
// 0 - 自动进入； 1 - 点击进入

const nextMusic = (flag: number) => {
  const length = curPlayList.value.length;
  if (length <= 0){
    ElMessage({
      message: "当前歌单列表为空",
      type: "error",
      duration: 1000,
      grouping: true
    })
    musicStore.setCurrentPlayIndex(0)
    return
  }
  if(playMode.value === 0)
    curPlayIndex.value = (curPlayIndex.value + 1) % length;
  if(playMode.value === 1)
    curPlayIndex.value = Math.floor(Math.random() * length);
  if(flag === 1 && playMode.value === 3)
    curPlayIndex.value = (curPlayIndex.value + 1) % length;
  setCurrentMusic();
}
const previousMusic = () => {
  const length = curPlayList.value.length;
    if (length <= 0){
      ElMessage({
        message: "当前歌单列表为空",
        type: "error",
        duration: 1000,
        grouping: true
      })
      musicStore.setCurrentPlayIndex(0)
      return
    }
    if(playMode.value === 0 || playMode.value === 0 )
      curPlayIndex.value = (curPlayIndex.value - 1) % length;
    if(playMode.value === 1)
      curPlayIndex.value = Math.floor(Math.random() * length);
    setCurrentMusic();
}
const setCurrentMusic = () => {
  const musicDetail = curPlayList.value[curPlayIndex.value];
  musicStore.setCurrentMusic(musicDetail);
}
const togglePlay = () => {
  musicStore.togglePlay();
}
//</editor-fold>

</script>

<style  lang="scss" scoped>
@import "@/static/css/player";
.slider-demo-block {
  display: flex;
  align-items: center;
}

.slider-demo-block .el-slider {
  margin-top: 0;
}

.slider-demo-block .demonstration {
  font-size: 14px;
  @click="startPlayOrpause"
  color: var(--el-text-color-secondary);
  line-height: 44px;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 0;
}

.slider-demo-block .demonstration+.el-slider {
  flex: 0 0 70%;
}

.el-slider__button-wrapper {
  display: none;
}
</style>