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

<script setup lang="ts">

import MusicDetail from "@/views/MusicDetail.vue";

</script>

<style lang="scss">
@import "@/static/css/main";
</style>