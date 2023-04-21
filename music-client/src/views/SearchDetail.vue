<template>
    <div class="content-section menuBar-mv">
        <div class="content-section-title">
            <h1>{{ keyword }}</h1> <span>{{ songCount }}个结果</span>
        </div>
        <div class="content-section">
            <el-tabs v-model="activeName" class="demo-tabs">
                <el-tab-pane label="歌曲" name="music">
                    <JezerMusic :page-size="20" :is-loading="isLoading" :raw-data="musicRawData"/>
                </el-tab-pane>
                <el-tab-pane label="歌单" name="playlist">
                    <JezerPlaylist :pageSize = "20" :is-loading="isLoading" :raw-data="playlistRawData"/>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script setup lang="ts">
import {HttpManager} from "@/api/request";

const router = useRoute();
const activeName = ref("music");
//<editor-fold desc="数据获取">
const keyword:any = ref(router.query.keyword)
const musicRawData = ref([])
const playlistRawData = ref([])
const songCount = ref(0)
const isLoading = ref(true)
const getData = async () => {
    musicRawData.value = [];
    playlistRawData.value = [];
    const music = await HttpManager.getSongByName(keyword.value);
    const playlist = await HttpManager.getListByTitle(keyword.value);
    if (!music.success || !playlist.success)
        ElMessage({
            message: "查询出现问题，请稍后再试",
            type: "error",
            duration: 1000,
            grouping: true
        })
    else {
        musicRawData.value = music.data;
        songCount.value += musicRawData.value.length;
        playlistRawData.value = playlist.data;
        songCount.value += playlistRawData.value.length;
        isLoading.value = false;
    }
}
onMounted(() => {
    getData();
})
//</editor-fold>

//<editor-fold desc="监听keyword">
watch(() => router.query.keyword, (newVal) => {
    keyword.value = newVal;
    songCount.value = 0;
    getData()
},{deep:true})
//</editor-fold>
</script>

<style>
.el-tabs__nav-wrap::after {
    background-color: transparent;
}

</style>