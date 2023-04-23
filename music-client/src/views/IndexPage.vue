<template>
  <div class="app">
    <JezerHeader/>
    <div class="wrapper">
      <JezerAside/>
      <div class="main-container">
        <div class="content-wrapper">
          <router-view  />
        </div>
      </div>
    </div>
    <JezerPlayer/>
    <JezerCurrentPlay/>
    <MusicDetail/>
  </div>
</template>

<script>


import {defineComponent} from "vue";
import JezerCurrentPlay from "@/components/JezerCurrentPlay.vue";
import JezerLyric from "@/views/MusicDetail.vue";
import MusicDetail from "@/views/MusicDetail.vue";

export default defineComponent({
  components: {MusicDetail, JezerLyric, JezerCurrentPlay}
})
</script>

<style lang="scss">
@import "@/static/css/main";
</style>