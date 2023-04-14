<template>
  <el-breadcrumb class="crumbs" separator="/">
    <!--    面包屑导航栏-->
    <el-breadcrumb-item v-for="item in breadcrumbList" :key="item.name" :to="<string>{path: item.path , query: item.query}">
      {{ item.name }}
    </el-breadcrumb-item>
  </el-breadcrumb>

  <div class="container">
      <div class="handle-box">
          <el-button @click="deleteSelected">批量删除</el-button>
          <el-input placeholder="筛选评论" v-model="searchWord"></el-input>
      </div>
      <el-table border size="small" :data="data" @selection-change="handleSelectionChange" stripe>
          <el-table-column type="selection" width="40" align="center"/>
          <el-table-column label="ID" prop="id" width="50" align="center"/>
          <el-table-column prop="username" label="用户" width="80"/>
          <el-table-column prop="content" label="评论内容"/>
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
import {useAdminStore} from "@/store/admin";
import {storeToRefs} from "pinia";
import {HttpManager} from "@/api/request";
import {ComponentInternalInstance} from "vue";



//<editor-fold desc="路由导航">
const adminStore = useAdminStore();
const {breadcrumbList} = storeToRefs(adminStore);
//</editor-fold>

//<editor-fold desc="数据展示">
const {proxy} = getCurrentInstance() as ComponentInternalInstance;
// 保存歌曲/歌单id
const id : any= ref(proxy!.$route.query.id);
// 标记是歌曲还是歌单
const type : any = ref(proxy?.$route.query.type);
// 原始数组
let tableData = ref([]);
// 搜索后数据
let tempData = ref([]);
// 分页
const pageSize = ref(20)
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
    result = await HttpManager.getCommentBySongId(id.value);
  else result = await HttpManager.getCommentBySongListId(id.value);
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
      if (item.content.includes(searchWord.value)) {
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
      `确定要删除ID为${id}的评论？`,
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(async () => {
        const result = await HttpManager.deleteComment(id)
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
    ElMessage.warning("请先选择需要删除的评论")
    return
  }
  ElMessageBox.confirm(
      `确定要删除所选的${count}条评论吗？`,
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
        const result = await HttpManager.deleteComments(ids)
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