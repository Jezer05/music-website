<template>
  <div class="content-section menuBar-mv">
    <div class="apps-card">
        <el-skeleton :rows="5" animated :loading="isLoading" />
        <div class="apps-item" v-for="(item,index) in data" :key="index" @click="goPlaylist(item.id)">
            <div class="app-card" style="position: relative">
                <img :src="attachUrl(item.pic)" alt="">
                <img src="@/static/img/icons/爱心.svg" v-if = "!likes.includes(item.id)"
                     style="width: 32px;height: 32px;position: absolute"
                     alt="" @click.stop = "collectList(item.id)">
                <img src="@/static/img/icons/红爱心.svg" v-else
                     style="width: 32px;height: 32px;position: absolute"
                     alt="" @click.stop = "delCollectList(item.id)">
            </div>
            <a href="javascript:" :title="item.title">{{item.title}}</a>
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
import {useLoginStore} from "@/store/login";
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

//<editor-fold desc="获得收藏歌单Id">
const likes:any = ref([]);
const loginStore = useLoginStore();
const {id, isLogin} = storeToRefs(loginStore);
const getLikes = async () => {
  if (!isLogin.value) return
  const res = await HttpManager.getCollectList(id.value);
  if (!res.success)
    ElMessage({
      message: "资源获取失败",
      type: "error",
      duration: 1000,
      grouping: true
    })
  else {
    for (let item of res.data)
      likes.value.push(item.id)
  }
}
onMounted(() => {
  getLikes();
})
//</editor-fold>

//<editor-fold desc="歌单收藏">
const collectList = async (listId: number) => {
  const res = await HttpManager.addCollectList(id.value,listId);
  if (res.success)
    likes.value.push(listId)
}
const delCollectList = async (listId: number) => {
  const res = await HttpManager.delCollectList(id.value,listId);
  if (res.success){
    const index = likes.value.indexOf(listId)
    if (index >= 0)
      likes.value.splice(index,1)
  }
}
//</editor-fold>
</script>

<style scoped>

</style>