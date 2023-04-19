<template>
    <div class="content-section " style="margin-top: 0;">
        <div class="apps-card">
            <div class="app-card usercom" v-for="(item, index) in data" :key="index">
                    <span>
                        <img class="useravatar" :src="attachUrl(item.avatar)" alt="" style="width: 32px;height: 30px">
                        {{ item.username }}
                    </span>
                <div class="app-card__subtext">{{ item.content }}</div>
                <div class="com-time">
                    <span>{{ parseTime(item.createTime) }}</span>
                    <span class="usercountlike">{{ item.mark }}赞</span>
                </div>
            </div>
        </div>
    </div>
    <el-pagination
        class="pagination"
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
// 接收父组件传来的数据
import {attachUrl, parseTime} from "@/utils";

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

</script>

<style scoped>

</style>