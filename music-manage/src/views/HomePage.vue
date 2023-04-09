<template>
  <JezerAside/>
  <JezerHeader/>
  <JezerAudio/>
  <div class="content-box" :class="{ 'content-collapse': collapse }">
    <router-view/>
  </div>
</template>

<script lang="ts">
import JezerAside from "@/components/JezerAside.vue";
import JezerHeader from "@/components/JezerHeader.vue";
import emitter from "@/utils/emitter.js";
import JezerAudio from "@/components/JezerAudio.vue";
export default defineComponent({
  name: "HomePage",
  components: {JezerAudio, JezerAside, JezerHeader},
  setup(){
    const collapse = ref(false);
    emitter.on("collapse", (msg) => {
      collapse.value = msg as boolean;
    });
    return{
      collapse
    }
  }


})
</script>

<style scoped>
.content-box {
  position: absolute;
  left: 150px;
  right: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;
  transition: left 0.3s ease-in-out;
  padding: 20px;
}

.content-collapse {
  left: 65px;
}
</style>