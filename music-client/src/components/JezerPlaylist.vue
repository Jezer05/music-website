<template>
  <div class="content-section menuBar-mv">
    <div class="apps-card">
        <el-skeleton :rows="5" animated :loading="isLoading" />
        <div class="apps-item" v-for="(item,index) in data" :key="index" @click="goPlaylist(item.id)">
            <div class="app-card">
                <img :src="attachUrl(item.pic)" alt="">
            </div>
            <a href="javascript:">{{item.title}}</a>
        </div>
    </div>
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
  </div>
</template>

<script setup lang="ts">
import {attachUrl} from "@/utils/index.js";
import {HttpManager} from "@/api/request";

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
    required: true,
  },
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

//<editor-fold desc="路由跳转">
const router = useRouter();
const goPlaylist = (id:number) => {
  router.push({ name: "playlistDetail", query: { playlistId: id } })
}
//</editor-fold>
</script>

<style scoped>

</style>