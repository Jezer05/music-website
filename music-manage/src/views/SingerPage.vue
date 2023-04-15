<template>
  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteSelected">批量删除</el-button>
      <el-input placeholder="筛选歌手" v-model="searchWord"></el-input>
      <el-button type="primary" @click="editVisible = true;isAdd = true">添加歌手</el-button>
    </div>
    <el-table border size="small" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"/>
      <el-table-column label="ID" prop="id" width="50" align="center"/>
      <el-table-column label="歌手图片" width="110" align="center">
        <template v-slot="scope">
          <el-image :src="attachUrl(scope.row.pic)" style="width: 100%; height: 100px" fit="cover"/>
          <el-upload :show-file-list="false"  :on-success="handleImgSuccess" :on-error="handleImgError" :before-upload="beforeImgUpload" :http-request="updateAvatar">
            <el-button @click="handleSingerId(scope.row.id)">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column label="歌手" prop="name" width="120" align="center"/>
      <el-table-column label="性别" width="60" align="center">
        <template v-slot="scope">
          <div>{{ parseSex(scope.row.sex) }}</div>
        </template>
      </el-table-column>
      <el-table-column label="出生" width="120" align="center">
        <template v-slot="scope">
          <div>{{ parseBirth(scope.row.birth) }}</div>
        </template>
      </el-table-column>
      <el-table-column label="地区" prop="location" width="100" align="center"/>
      <el-table-column label="简介" align="center">
        <template v-slot="scope">
          <el-scrollbar max-height="140px">
            <p >{{ scope.row.introduction }}</p>
          </el-scrollbar>
        </template>
      </el-table-column>
      <el-table-column label="歌曲管理" width="120" align="center">
        <template v-slot="scope">
          <el-button @click="goSongPage(scope.row.id)">歌曲管理</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template v-slot="scope">
          <el-button @click="editRow(scope.row);isAdd=false">编辑</el-button>
          <el-button type="danger" @click="deleteRow(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
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
  </div>

	<!-- 添加/修改歌手信息 -->
  <el-dialog title="添加歌手" v-model="editVisible" :before-close="handleEditClose">
    <el-form label-width="80px" :model="editForm" :rules="<FormRules>rules">
      <el-form-item label="歌手名" prop="name">
        <el-input v-model="editForm.name"/>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="editForm.sex">
          <el-radio :label="0">女</el-radio>
          <el-radio :label="1">男</el-radio>
          <el-radio :label="2">保密</el-radio>
          <el-radio :label="3">不明</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="故乡">
        <el-input v-model="editForm.location"/>
      </el-form-item>
<!--        这里有时会爆警告 scroll-blocking 'touchstart' event-->
      <el-form-item label="出生日期">
        <el-date-picker type="date" v-model="editForm.birth"/>
      </el-form-item>
      <el-form-item label="歌手介绍">
        <el-input type="textarea" v-model="editForm.introduction" :autosize = "{ minRows: 2, maxRows: 8 }"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button v-if="isAdd" type="primary" @click="addSinger">确 定</el-button>
        <el-button v-else type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {useRouter} from "@/hooks/useRouter";
import {useUpload} from "@/hooks/useUpload";
import {HttpManager} from "@/api/request";
import {SingerReqForm} from "@/api/type";
import {RouterName} from "@/enums";
import {useAdminStore} from "@/store/admin";
import {attachUrl, parseBirth, parseSex} from "@/utils";
import {FormRules} from "element-plus";


//<editor-fold desc="数据展示">
// 所有歌手数据
let tableData = ref([]);
// 页面展示数据
let tempData = ref([]);
const pageSize = ref(6);
const currentPage = ref(1);
const data = computed(() => {
  return tempData.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
})
// 处理当前页数
function handleCurrentChange(val:number) {
  currentPage.value = val;
}
onMounted(() => {
  getData()
})
async function getData() {
  tableData.value = [];
  tempData.value = [];
  const result = await HttpManager.getAllSinger();
  tableData.value = result.data;
  tempData.value = result.data;
  currentPage.value = 1;
}
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
      if (item.name.includes(searchWord.value)) {
        tempData.value.push(item);
      }
    }
  }
});
//</editor-fold>

//<editor-fold desc="路由跳转">
const adminStore = useAdminStore();
const {routerManager} = useRouter();
function goSongPage(id:number) {
  const breadcrumbList = reactive([
    {
      path: RouterName.Singer,
      name: "歌手管理",
    },
    {
      path: "",
      name: "歌曲信息",
    },
  ]);
  adminStore.setBreadcrumbList(breadcrumbList);
  routerManager(RouterName.SongDetail, {
    path: RouterName.SongDetail,
    query: {singerId: id},
  });
}
//</editor-fold>

//<editor-fold desc="添加/编辑表单">
const editVisible = ref(false);
// 标记现在是添加歌手还是更新歌手
const isAdd = ref(true);
const editForm : SingerReqForm= reactive({
  name: "",
  sex: 3,
  birth: new Date(),
  location: "",
  introduction: "",
});
const rules = reactive({
  name: [{ required: true, trigger: "blur", message: "姓名不能为空"}],
});
const clearForm = () => {
  editForm.name = ''
  editForm.sex = 3
  editForm.birth = new Date()
  editForm.location = ''
  editForm.introduction = ''
}
watch(editVisible, async (newVal) =>{
  if (newVal == false)
    clearForm();
})
const handleEditClose = (done: () => void) => {
  ElMessageBox.confirm('你确定要关闭此窗口吗？数据将不会被保存')
      .then(() => {
        done();
      })
      .catch(() => {
        // catch error
      })
}
//</editor-fold>

//<editor-fold desc="添加">
async function addSinger() {
  if (editForm.name.trim() == '') {
    ElMessage.error("歌手姓名不能为空")
    return
  }
  const result = await HttpManager.addSinger(editForm);
  ElMessage({
    message: result.message,
    type: result.type,
  });
  if (result.success) {
    editVisible.value = false;
  }
  await getData();
}
//</editor-fold>

//<editor-fold desc="编辑">
const singerId = ref(-1);
function editRow(row:any) {
  editVisible.value = true;
  singerId.value = row.id
  editForm.name = row.name;
  editForm.sex = row.sex;
  editForm.birth = row.birth;
  editForm.location = row.location;
  editForm.introduction = row.introduction;
}
async function saveEdit() {
  if (editForm.name.trim() == '') {
    ElMessage.error("歌手姓名不能为空")
    return
  }
  try {
    const result = await HttpManager.updateSingerMsg(singerId.value, editForm);
    ElMessage({
      message: result.message,
      type: result.type,
    });
    if (result.success) {
      await getData();
    }
    editVisible.value = false;
  } catch (error) {
    console.error(error);
  }
}
//</editor-fold>

//<editor-fold desc="头像更新">
const {beforeImgUpload} = useUpload();
function handleSingerId(id: number){
  singerId.value = id
}
function updateAvatar(options : any) {
  return HttpManager.updateSingerAvatar(singerId.value, options.file);
}
function handleImgSuccess(response:any) {
  ElMessage({
    message: response.message,
    type: response.type,
  });
  if (response.success) getData();
}
function handleImgError(response:any) {
  ElMessage({
    message: response.message,
    type: response.type,
  });
  if (response.success) getData();
}
//</editor-fold>

//<editor-fold desc="删除">
let mulDelSelection = ref([]); // 记录当前要删除的列表
function deleteRow(id:number) {
  ElMessageBox.confirm(
    `确定要删除ID为${id}的歌手？`,
    '警告',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
    }
  )
    .then(async () => {
      const result = await HttpManager.deleteSinger(id)
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
    ElMessage.warning("请先选择需要删除的歌手")
    return
  }
  ElMessageBox.confirm(
      `确定要删除所选的${count}位歌手吗？`,
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
        const result = await HttpManager.deleteSingers(ids)
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