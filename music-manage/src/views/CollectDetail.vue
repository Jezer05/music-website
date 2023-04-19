<template>
  <el-breadcrumb class="crumbs" separator="/">
    <el-breadcrumb-item v-for="item in breadcrumbList" :key="item.name" :to="<string>{ path: item.path, query: item.query }">
      {{ item.name }}
    </el-breadcrumb-item>
  </el-breadcrumb>
  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteSelected">批量删除</el-button>
      <el-input placeholder="筛选内容" v-model="searchWord"></el-input>
    </div>
    <el-table border size="small" :data="data" @selection-change="handleSelectionChange" stripe>
        <el-table-column type="selection" width="40" align="center"/>
        <el-table-column label="ID" prop="collectId" width="50" align="center"/>
        <el-table-column label="封面" width="110" align="center">
          <template v-slot="scope">
            <el-image :src="attachUrl(scope.row.pic)" style="width: 100%; height: 100px" fit="cover"/>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="歌曲/歌单"/>
        <el-table-column label="类型" width="80" align="center">
            <p v-if="type === 1">歌单</p>
            <p v-else>歌曲</p>
        </el-table-column>
        <el-table-column label="操作" width="100" align="center">
          <template v-slot="scope">
            <el-button type="danger" @click="deleteRow(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
    </el-table>
  </div>
  <!--    页脚导航-->
  <el-pagination
    class="pagination"
    background
    hide-on-single-page
    layout="total, prev, pager, next"
    :current-page="currentPage"
    :page-size="pageSize"
    :total="tempData.length"
    @current-change="handleCurrentChange"
  >
  </el-pagination>
</template>

<script setup lang="ts">
import {attachUrl} from "@/utils";
import {useAdminStore} from "@/store/admin";
import {storeToRefs} from "pinia";
import {ComponentInternalInstance} from "vue";
import {HttpManager} from "@/api/request";

//<editor-fold desc="路由导航">
const adminStore = useAdminStore();
const {breadcrumbList} = storeToRefs(adminStore);
//</editor-fold>

//<editor-fold desc="数据展示">
const {proxy} = getCurrentInstance() as ComponentInternalInstance;
// 保存用户id
const userId : any= ref(proxy!.$route.query.userId);
// 标记收藏的是歌曲还是歌单
const type : any = ref(proxy?.$route.query.type);
// 原始数组
let tableData = ref([]);
// 搜索后数据
let tempData = ref([]);
// 分页
const pageSize = ref(6)
const currentPage = ref(1);
// 分页后数据
const data = computed(() => {
  return tempData.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
})

async function getData() {
  tableData.value = [];
  tempData.value = [];
  let result;
  if (type.value == 0)
    result = await HttpManager.getCollectSongByUserId(userId.value);
  else result = await HttpManager.getCollectListByUserId(userId.value);
  tableData.value = result.data;
  tempData.value = result.data;
  currentPage.value = 1;
}
// 处理当前页数
function handleCurrentChange(val:number) {
  currentPage.value = val;
}

// 挂载时读取数据和增加页面监听
onMounted(() => {
  getData()
})
//</editor-fold>

//<editor-fold desc="搜索框">
const searchWord = ref("");
watch(searchWord, () => {
  if (searchWord.value === "") {
    tempData.value = tableData.value;
  } else {
    tempData.value = [];
    for (let item of tableData.value) {
      //@ts-ignore
      if (item.name.includes(searchWord.value)) {
        tempData.value.push(item);
      }
    }
  }
});
//</editor-fold>

//<editor-fold desc="删除">
let mulDelSelection = ref([]); // 记录当前要删除的列表
function deleteRow(id:number) {
  ElMessageBox.confirm(
      `确定要删除ID为${id}的收藏吗？`,
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(async () => {
        const result = await HttpManager.deleteCollect(id)
        await getData();
        ElMessage({
          type: result.type,
          message: result.message,
        })
      })
      .catch(() => {
        ElMessage({
          type: 'warning',
          message: '删除操作已经取消',
        })
      })
}
function handleSelectionChange(val: any) {
  mulDelSelection.value = val;
}
function deleteSelected() {
  const count = mulDelSelection.value.length;
  if (count <= 0) {
    ElMessage.warning("请先选择需要删除的内容")
    return
  }
  ElMessageBox.confirm(
      `确定要删除所选的${count}条内容吗？`,
      '警告',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(async () => {
        let ids: Array<number> = [];
        for (let item of mulDelSelection.value) {
          // @ts-ignore
          ids.push(item.id);
        }
        const result = await HttpManager.deleteCollects(ids)
        if (result.success) {
          await getData();
          mulDelSelection.value = [];
        }
        ElMessage({
          type: result.type,
          message: result.message,
        })
      })
      .catch(() => {
        ElMessage({
          type: 'warning',
          message: '删除操作已经取消',
        })
      })
}
//</editor-fold>

</script>

<style scoped>

</style>