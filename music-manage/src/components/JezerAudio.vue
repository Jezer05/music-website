<template>
    <audio controls="controls" preload="auto"
           v-if="url" :ref="player" :src="attachUrl(<string>url)"
           @canplay="startPlay" @ended="ended"/>
</template>

<script setup lang="ts">
import {usePlayerStore} from "@/store/player";
import {attachUrl} from "@/utils";

const playStore = usePlayerStore();
const divRef = ref<HTMLAudioElement>();
const player = (el: any) => divRef.value = el;
const url = ref(playStore.url);
const isPlay = ref(playStore.isPlay);
watch(isPlay, () => togglePlay())
function togglePlay() {
  isPlay.value ? divRef.value!.play() : divRef.value!.pause();
}

function startPlay() {
  divRef.value!.play();
}
// 音乐播放结束时触发
function ended() {
  playStore.pause();
}
</script>

<style scoped>
audio {
  display: none;
}
</style>