<template>
  <el-breadcrumb class="crumbs" separator="/">
<!--    面包屑导航栏-->
    <el-breadcrumb-item v-for="item in breadcrumbList" :key="item.name" :to="{ path: item.path, query: item.query }">
      {{ item.name }}
    </el-breadcrumb-item>
  </el-breadcrumb>
  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteSelected">批量删除</el-button>
      <el-input placeholder="筛选歌曲" v-model="searchWord"></el-input>
      <el-button type="primary" @click="editVisible = true;isAdd = true">添加歌曲</el-button>
    </div>
    <el-table border size="small" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"/>
      <el-table-column label="ID" prop="id" width="50" align="center"/>
      <el-table-column label="歌曲图片" width="110" align="center">
        <template v-slot="scope">
          <el-image :src="attachImageUrl(scope.row.pic)" style="width: 100%; height: 100px" fit="cover"/>
<!--          播放按钮遮罩-->
          <div class="play" @click="setSongUrl(scope.row)">
            <el-icon>
              <svg class="icon" aria-hidden="true">
                <use :xlink:href="toggle === scope.row.name ? playIcon : BOFANG"></use>
              </svg>
            </el-icon>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="歌名" prop="name" width="120" align="center"/>
      <el-table-column label="简介" prop="introduction" width="60" align="center"/>
      <el-table-column label="歌词" width="120" align="center">
        <template v-slot="scope">
          <ul style="height: 100px; overflow: scroll">
            <li v-for="(item, index) in parseLyric(scope.row.lyric)" :key="index">
              {{ item }}
            </li>
          </ul>
        </template>
      </el-table-column>
      <el-table-column label="资源更新" width="100" align="center"/>
        <template v-slot = "scope">
            <el-upload :show-file-list="false"  :on-success="handleImgSuccess" :on-error="handleImgError" :before-upload="beforeImgUpload" :http-request="updateImg">
              <el-button @click="handleSongId(scope.row.id)">更新图片</el-button>
            </el-upload>
            <el-upload :show-file-list="false"  :on-success="handleSongSuccess" :on-error="handleSongError" :before-upload="beforeSongUpload" :http-request="updateSong">
              <el-button @click="handleSongId(scope.row.id)">更新歌曲</el-button>
            </el-upload>
        </template>
      <el-table-column label="评论" width="90" align="center">
        <template v-slot="scope">
          <el-button @click="goCommentPage(scope.row.id)">评论</el-button>
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
      layout="total, prev, pager, next"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="tableData.length"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>

	<!-- 添加/修改歌手信息 -->
  <el-dialog title="添加歌手" v-model="editVisible" :before-close="handleEditClose">
    <el-form label-width="80px" :model="editForm" :rules="songRule">
      <el-form-item label="歌曲名">
        <el-input v-model="editForm.name"></el-input>
      </el-form-item>
      <el-form-item label="简介">
        <el-input v-model="editForm.introduction"></el-input>
      </el-form-item>
      <el-form-item label="歌词">
        <el-input v-model="editForm.lyric"></el-input>
      </el-form-item>
      <el-form-item label="歌曲上传">
        <input type="file" name="file"  ref = "fileUpload" @change = 'beforeSongUpload(this)'/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editVisible = false; clearEditForm()">取 消</el-button>
        <el-button v-if="isAdd" type="primary" @click="addSong">确 定</el-button>
        <el-button v-else type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
import {ComponentInternalInstance, defineComponent} from "vue";
import {parseLyric,attachImageUrl} from "@/utils";
import {useRouter} from "@/hooks/useRouter";
import {useUpload} from "@/hooks/useUpload";
import {HttpManager} from "@/api/request";
import {RouterName} from "@/enums";
import {SongReqForm} from "@/api/type";

const {beforeImgUpload,beforeSongUpload} = useUpload();
const {proxy} = getCurrentInstance() as ComponentInternalInstance;
const {routerManager} = useRouter();

// 跳转的歌手id
const singerId = ref(proxy?.$route.query.id );
// 所有歌手数据
let tableData = ref([]);
// 页面展示数据
let tempData = ref([]);
// 页面数据容量
const pageSize = ref(6);
// 当前页
const currentPage = ref(1);
// 分页后数据
const data = computed(() => {
  return tempData.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
})
// 挂载时读取数据
onMounted(() => {
  getData()
})
async function getData() {
  tableData.value = [];
  tempData.value = [];
  const result = await HttpManager.getSongBySingerId(singerId.value);
  tableData.value = result.data;
  tempData.value = result.data;
  currentPage.value = 1;
}
// 处理当前页数
function handleCurrentChange(val:number) {
  currentPage.value = val;
}
// 监听搜索框
const searchWord = ref("");
watch(searchWord, () => {
  if (searchWord.value === "") {
    tempData.value = tableData.value;
  } else {
    tempData.value = [];
    for (let item of tableData.value) {
      if (item.name.includes(searchWord.value)) {
        tempData.value.push(item);
      }
    }
  }
});
const editVisible = ref(false);
// 标记现在是添加还是更新
const isAdd = ref(true);
/**
 * 添加
 */
const editForm : SongReqForm= reactive({
  name: "",
  singerId: singerId.value,
  introduction: "",
  lyric: "",
});
const songRule = reactive({
  name: [{ required: true, trigger: "change" }],
});
const clearEditForm = () => {
  editForm.name = "";
  editForm.introduction = "";
  editForm.lyric = "";
}
async function addSong() {
  const file = proxy?.$refs.file
  if (file == null) {
    ElMessage.error("歌曲资源不能为空")
    return
  }
  if (editForm.name.trim() == '') {
    ElMessage.error("歌曲名称不能为空")
    return
  }
  const result = await HttpManager.addSong(editForm, file);
  ElMessage({
    message: result.message,
    type: result.type,
  });
  if (result.success) {
    clearEditForm();
    editVisible.value = false;
  }
  getData();
}
/**
 * 编辑
 */
function editRow(row:any) {
  editVisible.value = true;
  editForm.name = row.name;
  editForm.introduction = row.introduction;
  editForm.lyric = row.lyric;
}
const handleEditClose = (done: () => void) => {
  ElMessageBox.confirm('Are you sure to close this dialog?')
      .then(() => {
        clearEditForm();
        done();
      })
      .catch(() => {
        // catch error
      })
}
async function saveEdit(row:any) {
  if (editForm.name.trim() == '') {
    ElMessage.error("歌手姓名不能为空")
    return
  }
  try {
    const result = await HttpManager.updateSingerMsg(row.id, editForm);
    ElMessage({
      message: result.message,
      type: result.type,
    });
    if (result.success) {
      getData();
      clearEditForm();
    }
    editVisible.value = false;
  } catch (error) {
    console.error(error);
  }
}
// 更新头像
let avatarId = ref(-1);
function handleSongId(id: number){
  avatarId.value = id
}
function updateAvatar(options : any) {
  return HttpManager.updateSingerAvatar(avatarId.value, options.file);
}
function handleImgSuccess(response:any, file:any) {
  ElMessage({
    message: response.message,
    type: response.type,
  });
  if (response.success) getData();
}
function handleImgError(response:any, file:any) {
  ElMessage({
    message: response.message,
    type: response.type,
  });
  if (response.success) getData();
}
/**
 * 删除
 */
let mulDelSelection = ref([]); // 记录当前要删除的列表
function deleteRow(id:number) {
  ElMessageBox.confirm(
      `确定要删除ID为${id}的歌手？`,
      'Warning',
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
      `确定要删除所选的${count}歌手吗？`,
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
          ids.push(item.id);
        }
        const result = await HttpManager.deleteSingers(ids)
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
</script>

<style scoped>
.play {
    position: absolute;
    z-index: 100;
    width: 80px;
    height: 80px;
    top: 18px;
    left: 15px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}
</style>