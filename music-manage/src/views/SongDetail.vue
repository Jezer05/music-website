<template>
  <el-breadcrumb class="crumbs" separator="/">
<!--    面包屑导航栏-->
    <el-breadcrumb-item v-for="(item) in breadcrumbList" :key="item.name" :to="<string>{path: item.path , query: item.query}">
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
          <el-image :src="attachUrl(scope.row.pic)" style="width: 100%; height: 100px" fit="cover"/>
<!--          播放按钮遮罩-->
          <div class="play" @click="setSongUrl(scope.row)">
            <svg class="icon" aria-hidden="true">
              <use :xlink:href="(musicName === scope.row.name && isPlay) ? '#icon-zanting' : `#icon-bofang`"></use>
            </svg>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="歌名" prop="name" width="120" align="center"/>
      <el-table-column label="简介" prop="introduction" width="120" align="center"/>
      <el-table-column label="歌词"  align="center">
        <template v-slot="scope">
          <el-scrollbar max-height="100">
            <ul>
              <li v-for="(item, index) in parseLyric(scope.row.lyric)" :key="index">
                {{ item }}
              </li>
            </ul>
          </el-scrollbar>
        </template>
      </el-table-column>
      <el-table-column label="资源更新" width="100" align="center">
        <template v-slot = "scope">
            <el-upload :show-file-list="false"  :on-success="handleResourceSuccess" :on-error="handleResourceError" :before-upload="beforeImgUpload" :http-request="updateImg">
              <el-button @click="handleSongId(scope.row.id)">更新图片</el-button>
            </el-upload>
            <br />
            <el-upload :show-file-list="false"  :on-success="handleResourceSuccess" :on-error="handleResourceError" :before-upload="beforeSongUpload" :http-request="updateUrl">
              <el-button @click="handleSongId(scope.row.id)">更新歌曲</el-button>
            </el-upload>
        </template>
      </el-table-column>
      <el-table-column label="评论" width="90" align="center">
        <template v-slot="scope">
          <el-button @click="goCommentDetail(scope.row.id)">评论</el-button>
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
	<!-- 添加/修改歌曲信息 -->
  <el-dialog title="添加歌曲" v-model="editVisible" :before-close="handleEditClose">
    <el-form id="add-song" label-width="80px" :model="editForm" :rules="<FormRules>rules">
      <el-form-item label="歌曲名" prop="name">
        <el-input name="name" v-model="editForm.name"/>
      </el-form-item>
      <el-form-item label="简介">
        <el-input name="introduction" v-model="editForm.introduction"/>
      </el-form-item>
      <el-form-item label="歌词">
        <el-input type="textarea" name="lyric" v-model="editForm.lyric" :autosize = "{ minRows: 2, maxRows: 8 }"/>
      </el-form-item>
      <el-form-item v-if="isAdd && editVisible" label="歌曲上传">
        <input type="file" name="file" ref="upload"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button v-if="isAdd" type="primary" @click="addSong">确 定</el-button>
        <el-button v-else type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
import {ComponentInternalInstance} from "vue";
import {parseLyric,attachUrl} from "@/utils";
import {useRouter} from "@/hooks/useRouter";
import {useUpload} from "@/hooks/useUpload";
import {HttpManager} from "@/api/request";
import {RouterName} from "@/enums";
import {SongReqForm} from "@/api/type";
import {storeToRefs} from "pinia";
import {useAdminStore} from "@/store/admin";
import {usePlayerStore} from "@/store/player";
import {FormRules} from "element-plus";


const {beforeImgUpload,beforeSongUpload} = useUpload();

//<editor-fold desc="路由管理">
const adminStore = useAdminStore();
const {routerManager} = useRouter();
// 面包屑导航
const {breadcrumbList} = storeToRefs(adminStore)
function goCommentDetail(id:number) {
  const breadcrumbList = reactive([
    {
      path: RouterName.Singer,
      name: "歌手管理",
    },
    {
      path: RouterName.SongDetail,
      query: {
        singerId: singerId.value,
      },
      name: "歌曲信息",
    },
    {
      path: "",
      name: "评论详情",
    },
  ]);
  adminStore.setBreadcrumbList(breadcrumbList);
  // type用来表示歌曲还是歌单
  routerManager(RouterName.CommentDetail, { path: RouterName.CommentDetail, query: { id, type: 0 } });
}
//</editor-fold>

//<editor-fold desc="数据展示">
const {proxy} = getCurrentInstance() as ComponentInternalInstance;
const singerId : any = ref(proxy!.$route.query.singerId);
// 原始数组
let tableData = ref([]);
// 搜索后数据
let tempData = ref([]);
const pageSize = ref(6);
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
//</editor-fold>

//<editor-fold desc="音乐播放">
// 正在播放的歌曲
const playerStore = usePlayerStore();
const musicName = ref('');
const {isPlay} = storeToRefs(playerStore)
function setSongUrl(row:any) {
  if (row.url ===  playerStore.url)
    playerStore.changeState();
  else {
    playerStore.setUrl(row.url);
    playerStore.play();
    musicName.value = row.name;
  }
}
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

//<editor-fold desc="编辑/添加表单">
const editVisible = ref(false);
// 标记现在是添加还是更新
const isAdd = ref(true);
const editForm : SongReqForm= reactive({
  name: "",
  introduction: "",
  lyric: "",
});
const rules = reactive({
  name: [{ required: true, message: "请输入歌曲名字", trigger: "blur" }],
});
const handleEditClose = (done: () => void) => {
  ElMessageBox.confirm('你确定要关闭窗口吗，数据将不会保存！')
    .then(() => {
      done();
    })
    .catch(() => {
      // catch error
    })
}
const clearForm = () => {
  editForm.name = '';
  editForm.introduction = '';
  editForm.lyric = '';
}
watch(editVisible, async (newVal) => {
  if (newVal == false)
    clearForm();
})
//</editor-fold>

//<editor-fold desc="添加歌曲">
// 获得input file实例
const upload = ref();
async function addSong() {
  if (!upload.value.files[0]) {
    ElMessage.error("歌曲资源不能为空")
    return
  }
  if (!beforeSongUpload(upload.value.files[0]))
    return
  if (editForm.name.trim() == '') {
    ElMessage.error("歌曲名称不能为空")
    return
  }
  const addSongForm = new FormData(document.getElementById("add-song") as HTMLFormElement);
  addSongForm.append("singerId", singerId.value)
  const result = await HttpManager.addSong(addSongForm);
  ElMessage({
    message: result.message,
    type: result.type,
  });
  if (result.success)
    editVisible.value = false;
  await getData();
}
//</editor-fold>

//<editor-fold desc="编辑">
const songId = ref(-1);
function editRow(row:any) {
  editVisible.value = true;
  songId.value = row.id
  editForm.name = row.name;
  editForm.introduction = row.introduction;
  editForm.lyric = row.lyric;
}

async function saveEdit() {
  if (editForm.name.trim() == '') {
    ElMessage.error("歌手姓名不能为空")
    return
  }
  try {
    const result = await HttpManager.updateSongMsg(songId.value, editForm);
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
// 更新资源
function handleSongId(id: number){
  songId.value = id
}
function updateUrl(options : any) {
  return HttpManager.updateSongUrl(songId.value, options.file);
}
function updateImg(options : any) {
  return HttpManager.updateSongImg(songId.value, options.file);
}
function handleResourceSuccess(response:any) {
  ElMessage({
    message: response.message,
    type: response.type,
  });
  if (response.success) getData();
}
function handleResourceError(response:any) {
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
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(async () => {
        const result = await HttpManager.deleteSong(id)
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
      `确定要删除所选的${count}首歌曲吗？`,
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
        const result = await HttpManager.deleteSongs(ids)
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
.play {
    position: absolute;
    z-index: 100;
    width: 80px;
    height: 80px;
    top: 18px;
    opacity: 0.5;
    left: 15px;
    display: flex;
    align-items: center;
    background-size: cover;
    justify-content: center;
    cursor: pointer;
}
.icon {
    width: 80px;
    height: 80px;
}
</style>