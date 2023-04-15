<template>
  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteSelected">批量删除</el-button>
      <el-input placeholder="筛选用户" v-model="searchWord"></el-input>
    </div>
      <el-table border size="small" :data="data" @selection-change="handleSelectionChange" stripe>
        <el-table-column type="selection" width="40" align="center"/>
        <el-table-column label="ID" prop="id" width="50" align="center"/>
        <el-table-column label="用户头像" width="110" align="center">
          <template v-slot="scope">
            <el-image :src="attachUrl(scope.row.avatar)" style="width: 100%; height: 100px" fit="cover" />
          </template>
        </el-table-column>
        <el-table-column label="用户名" prop="username" width="80" align="center"/>
        <el-table-column label="性别" width="50" align="center">
          <template v-slot="scope">
            <div>{{ parseSex(scope.row.sex) }}</div>
          </template>
        </el-table-column>
        <el-table-column label="手机号码" prop="phoneNum" width="120" align="center"/>
        <el-table-column label="邮箱" prop="email" width="120" align="center"/>
        <el-table-column label="生日" width="120" align="center">
          <template v-slot="scope">
            <div>{{ parseBirth(scope.row.birth) }}</div>
          </template>
        </el-table-column>
        <el-table-column label="签名" prop="introduction"/>
        <el-table-column label="地区" prop="location" width="70" align="center"/>
        <el-table-column label="收藏" width="120" align="center">
          <template v-slot="scope">
            <el-button @click="goCollectPage(scope.row.id)">收藏列表</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="90" align="center">
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
import {attachUrl, parseBirth, parseSex} from "@/utils";
import {useRouter} from "@/hooks/useRouter";
import {HttpManager} from "@/api/request";
import {useAdminStore} from "@/store/admin";
import {RouterName} from "@/enums";

//<editor-fold desc="数据展示">
// 原始数组
let tableData = ref([]);
// 搜索后数据
let tempData = ref([]);
// 分页
const pageSize = ref(8)
const currentPage = ref(1);
// 分页后数据
const data = computed(() => {
  return tempData.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
})

async function getData() {
  tableData.value = [];
  tempData.value = [];
  const result = await HttpManager.getAllConsumer();
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

//<editor-fold desc="搜索">
const searchWord = ref("");
watch(searchWord, () => {
  if (searchWord.value === "") {
    tempData.value = tableData.value;
  } else {
    tempData.value = [];
    for (let item of tableData.value) {
      // @ts-ignore
      if (item.username.includes(searchWord.value)) {
        tempData.value.push(item);
      }
    }
  }
});
//</editor-fold>

//<editor-fold desc="路由跳转">
const adminStore = useAdminStore();
const {routerManager} = useRouter();
function goCollectPage(id:number) {
  const breadcrumbList = reactive([
      // TODO: 面包屑导航
    {
      path: RouterName.Consumer,
      name: "用户管理",
    },
    {
      path: "",
      name: "收藏歌曲",
    },
  ]);
  adminStore.setBreadcrumbList(breadcrumbList);
  routerManager(RouterName.SongDetail, {
    path: RouterName.CollectDetail,
    query: {userId: id, type: 0},
  });
}
//</editor-fold>

//<editor-fold desc="删除">
let mulDelSelection = ref([]); // 记录当前要删除的列表
function deleteRow(id:number) {
  ElMessageBox.confirm(
      `确定要删除ID为${id}的用户吗？`,
      '警告',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(async () => {
        const result = await HttpManager.deleteConsumer(id)
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
  if (count <= 0){
    ElMessage.warning("请先选择需要删除的用户")
    return
  }
  ElMessageBox.confirm(
      `确定要删除所选的${count}位用户吗？`,
      '警告',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(async () => {
        let ids: Array<number> = [];
        for (let item of mulDelSelection.value){
          // @ts-ignore
          ids.push(item.id);
        }
        const result = await HttpManager.deleteConsumers(ids)
        await getData();
        if (result.success) {
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