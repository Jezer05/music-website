<template>
    <audio controls="controls" preload="auto"
           v-if="url" ref="player" :src="attachUrl(<string>url)"
           @canplay="startPlay" @ended="ended"/>
</template>

<script setup lang="ts">
import {usePlayerStore} from "@/store/player";
import {attachUrl} from "@/utils";
import {storeToRefs} from "pinia";

const playerStore = usePlayerStore();
const player = ref<HTMLAudioElement>();

const {url,isPlay} = storeToRefs(playerStore);
watch(isPlay, () => togglePlay())
function togglePlay() {
  isPlay.value ? player.value?.play() : player.value?.pause();
}

function startPlay() {
  if (playerStore.isPlay == true)
    player.value?.play();
}
// 音乐播放结束时触发
function ended() {
  playerStore.pause();
}
// playStore.$subscribe((args, state) => {
//   isPlay.value = playStore.isPlay;
// })
</script>

<style scoped>
audio {
  display: none;
}
</style>