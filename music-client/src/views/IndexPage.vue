<template>
  <div class="app">
    <JezerHeader/>
    <div class="wrapper">
      <JezerAside/>
      <div class="main-container">
        <div class="content-wrapper">
          <router-view  />
        </div>
        <JezerPlayer/>
      </div>
    </div>
      <JezerCurrentPlay/>
  </div>
</template>

<script>


import {defineComponent} from "vue";
import JezerCurrentPlay from "@/components/JezerCurrentPlay.vue";

export default defineComponent({
  components: {JezerCurrentPlay}
})
</script>

<style lang="scss">
@import "@/static/css/main";
</style>