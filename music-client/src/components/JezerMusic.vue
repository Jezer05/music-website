<template>
  <ul class="t-title">
    <li class="adobe-product">
      <span class="products Music-name">歌曲名</span>
      <span class="status">操作</span>
    </li>
  </ul>
  <el-skeleton :rows="5" animated :loading="isLoading" />
  <ul class="s-list" v-if="!isLoading">
    <li class="adobe-product" v-for="(item, index) in data" :key="index" @dblclick="playMusic(item)">
        <div class="products Music-name" :title="item.name">
            <img :src="attachUrl(item.pic)" alt="" />
            {{ item.name }}
        </div>
        <div class="button-wrapper">
            <!-- 播放 -->
          <svg @click="playMusic(item)" t="1648883741930" class="icon" viewBox="0 0 1024 1024" version="1.1"
               xmlns="http://www.w3.org/2000/svg" p-id="2199" width="28" height="28">
              <path
                      d="M512 0C230.4 0 0 230.4 0 512s230.4 512 512 512 512-230.4 512-512S793.6 0 512 0z m0 981.333333C253.866667 981.333333 42.666667 770.133333 42.666667 512S253.866667 42.666667 512 42.666667s469.333333 211.2 469.333333 469.333333-211.2 469.333333-469.333333 469.333333z"
                      p-id="2200" fill="#525B72"></path>
              <path
                      d="M672 441.6l-170.666667-113.066667c-57.6-38.4-106.666667-12.8-106.666666 57.6v256c0 70.4 46.933333 96 106.666666 57.6l170.666667-113.066666c57.6-42.666667 57.6-106.666667 0-145.066667z"
                      p-id="2201" fill="#525B72"></path>
          </svg>
            <!-- 下载 -->
          <svg t="1648883905939" class="icon" viewBox="0 0 1024 1024" version="1.1"
               xmlns="http://www.w3.org/2000/svg" p-id="4298" width="28" height="28">
              <path
                      d="M802 664v146c0 7.7-6.3 14-14 14H236c-7.7 0-14-6.3-14-14V664c0-5.5-4.5-10-10-10h-50c-5.5 0-10 4.5-10 10v170c0 33.1 26.9 60 60 60h600c33.1 0 60-26.9 60-60V664c0-5.5-4.5-10-10-10h-50c-5.5 0-10 4.5-10 10z"
                      p-id="4299" fill="#525B72"></path>
              <path
                      d="M547 163v449.5l173.6-173.6c13.7-13.7 35.8-13.7 49.5 0 13.7 13.7 13.7 35.8 0 49.5L536.8 721.7c-0.4 0.4-0.8 0.8-1.3 1.2-0.2 0.2-0.4 0.4-0.6 0.5-0.2 0.2-0.4 0.4-0.7 0.6-0.3 0.2-0.5 0.4-0.8 0.6-0.2 0.1-0.4 0.3-0.5 0.4l-0.9 0.6c-0.2 0.1-0.3 0.2-0.5 0.3-0.3 0.2-0.6 0.4-1 0.6-0.2 0.1-0.3 0.2-0.5 0.3-0.3 0.2-0.6 0.4-1 0.5-0.2 0.1-0.4 0.2-0.5 0.3-0.3 0.2-0.6 0.3-0.9 0.5l-0.6 0.3c-0.3 0.1-0.6 0.3-0.8 0.4-0.2 0.1-0.5 0.2-0.7 0.3-0.3 0.1-0.5 0.2-0.8 0.3l-0.9 0.3c-0.2 0.1-0.4 0.2-0.7 0.2-0.3 0.1-0.6 0.2-1 0.3-0.2 0.1-0.4 0.1-0.6 0.2-0.4 0.1-0.7 0.2-1.1 0.3-0.2 0-0.4 0.1-0.6 0.1-0.4 0.1-0.7 0.2-1.1 0.2-0.2 0-0.4 0.1-0.6 0.1-0.4 0.1-0.7 0.1-1.1 0.2-0.2 0-0.4 0.1-0.7 0.1-0.3 0-0.7 0.1-1 0.1-0.3 0-0.6 0-0.9 0.1-0.3 0-0.5 0-0.8 0.1-1.2 0.1-2.3 0.1-3.5 0-0.3 0-0.5 0-0.8-0.1-0.3 0-0.6 0-0.9-0.1-0.3 0-0.7-0.1-1-0.1-0.2 0-0.4-0.1-0.7-0.1-0.4-0.1-0.7-0.1-1.1-0.2-0.2 0-0.4-0.1-0.6-0.1-0.4-0.1-0.7-0.2-1.1-0.2-0.2 0-0.4-0.1-0.6-0.1-0.4-0.1-0.7-0.2-1.1-0.3-0.2-0.1-0.4-0.1-0.6-0.2-0.3-0.1-0.6-0.2-1-0.3-0.2-0.1-0.5-0.1-0.7-0.2l-0.9-0.3c-0.3-0.1-0.5-0.2-0.8-0.3-0.2-0.1-0.5-0.2-0.7-0.3-0.3-0.1-0.6-0.3-0.8-0.4l-0.6-0.3c-0.3-0.2-0.6-0.3-0.9-0.5-0.2-0.1-0.4-0.2-0.5-0.3-0.3-0.2-0.6-0.4-1-0.6-0.2-0.1-0.3-0.2-0.5-0.3-0.3-0.2-0.6-0.4-1-0.6-0.2-0.1-0.3-0.2-0.5-0.3-0.3-0.2-0.6-0.4-0.9-0.7-0.2-0.1-0.3-0.3-0.5-0.4-0.3-0.2-0.5-0.4-0.8-0.6-0.2-0.2-0.4-0.4-0.7-0.6-0.2-0.2-0.4-0.4-0.6-0.5l-1.2-1.2-233.1-233.1c-13.7-13.7-13.7-35.8 0-49.5 13.7-13.7 35.8-13.7 49.5 0L477 612.5V163c0-19.3 15.7-35 35-35s35 15.7 35 35z"
                      p-id="4300" fill="#525B72"></path>
          </svg>
        </div>
    </li>
  </ul>
  <el-pagination
          class="pagination"
          v-if="!isLoading"
          background
          hide-on-single-page
          layout="total, prev, pager, next"
          :current-page="currentPage"
          :page-size="props.pageSize"
          :total="props.rawData?.length"
          @current-change="handleCurrentChange"
  >
  </el-pagination>
</template>

<script setup lang="ts">
import {HttpManager} from "@/api/request";
import {attachUrl} from "@/utils";
import {useMusicStore} from "@/store/music";
import {MusicEntity} from "@/api/type";
import {storeToRefs} from "pinia";

const props = defineProps({
  pageSize: {
    type: Number,
    required: true
  },
  rawData: {
    type: Array,
    required: true
  },
  isLoading: {
    type: Boolean,
    required: true
  }
})

//<editor-fold desc="分页">
const currentPage = ref(1);
function handleCurrentChange(val:number) {
  currentPage.value = val;
}
const data = computed(() => {
  return props.rawData.slice((currentPage.value - 1) * props.pageSize, currentPage.value * props.pageSize);
})
//</editor-fold>

//<editor-fold desc="播放">
const musicStore = useMusicStore();
const {curPlayList,curPlayIndex} = storeToRefs(musicStore)
const playMusic = (item : MusicEntity) => {
  const {id: songId, name: songTitle, url: songUrl, pic: songPic} = item
  // 存放已经在歌单中的歌曲id
  let ids: number[] =[];
  for (let tmp of curPlayList.value)
    ids.push(tmp.songId);
  const index = ids.indexOf(item.id)
  if (index < 0) {
    musicStore.addMusicToList({
      songId, songTitle, songPic, songUrl
    })
  }
  else {
    musicStore.setCurrentMusic({
      songId, songTitle, songPic, songUrl
    })
    musicStore.setCurrentPlayIndex(index)
  }
}
//</editor-fold>

</script>

<style lang="scss" scoped>

.t-title {
  li {
    justify-content: space-between;
  }
  .status {
    text-align: center;
  }
}
.s-list {
  li {
    justify-content: space-between;
  }
  .button-wrapper{
    justify-content: center;
  }
}
.pagination {
  margin: 20px 0;
}

</style>