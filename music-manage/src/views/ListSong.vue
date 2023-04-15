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
            <el-input placeholder="筛选内容" v-model="searchWord"></el-input>
            <el-button type="primary" @click="showSelect">添加歌曲</el-button>
        </div>
        <el-table border size="small" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40" align="center"/>
            <el-table-column label="ID" prop="songId" width="50" align="center"/>
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
            <el-table-column label="歌名" width="120" prop="name"  align="center"/>
            <el-table-column label="简介" prop="introduction" align="center"/>
            <el-table-column label="操作" width="160" align="center">
                <template v-slot="scope">
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
    <el-dialog title="添加歌曲" v-model="dialogVisible" center width="25%" align-center>
        <el-form>
            <el-form-item label="歌曲选择">
                <el-select v-model="selectRes"
                           placeholder="请选择歌曲"
                           filterable
                           clearable
                           multiple
                           collapse-tags
                           collapse-tags-tooltip
                           style="width: 500px"
                >
                    <el-option
                        v-for="item in selectedList"
                        :key="item.songId"
                        :label="item.songName"
                        :value="item.songId"
                        :disabled="item.disabled"
                    />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addSong">确 定</el-button>
          </span>
        </template>
    </el-dialog>
</template>

<script setup lang="ts">
import {attachUrl} from "@/utils";
import {ComponentInternalInstance} from "vue";
import {useAdminStore} from "@/store/admin";
import {storeToRefs} from "pinia";
import {HttpManager} from "@/api/request";
import {usePlayerStore} from "@/store/player";

//<editor-fold desc="路由导航">
const adminStore = useAdminStore();
const {breadcrumbList} = storeToRefs(adminStore);
//</editor-fold>

//<editor-fold desc="数据展示">
const {proxy} = getCurrentInstance() as ComponentInternalInstance;
// 保存歌单id
const songListId : any= ref(proxy!.$route.query.songListId);
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
  const result = await HttpManager.getListSongByListId(songListId.value);
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

//<editor-fold desc="音乐播放">
const playerStore = usePlayerStore();
// 正在播放的歌曲
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

//<editor-fold desc="删除">
let mulDelSelection = ref([]); // 记录当前要删除的列表
function deleteRow(id:number) {
  ElMessageBox.confirm(
      `确定要从歌单中删除这首歌曲吗？`,
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(async () => {
        const result = await HttpManager.deleteListSong(id)
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
    ElMessage.warning("请先选择需要删除的歌曲")
    return
  }
  ElMessageBox.confirm(
      `确定要在歌单中删除所选的${count}首歌曲吗？`,
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
        const result = await HttpManager.deleteListSongs(ids)
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

//<editor-fold desc="添加内容">
const dialogVisible = ref(false);
const selectedList = ref([]);
const selectRes = ref([]);
const showSelect = async () => {
    dialogVisible.value = true;
    // 获得已经在歌单中的歌曲
    const existedSongIds = ref([]);
    selectedList.value = [];
    for (let item of tableData.value){
        // @ts-ignore
        existedSongIds.value.push(item.songId)
    }
    const res = await HttpManager.getAllSong();
    for (let item of res.data){
        // 分开处理已在和不在列表中的歌曲
        // @ts-ignore
        const index = existedSongIds.value.indexOf(item.id);
        const tmp:object = {
            songId: item.id,
            songName: item.name,
        }
        if (index <= -1){
            // @ts-ignore
            selectedList.value.push(tmp);
            // @ts-ignore
        }else selectedList.value.push(Object.assign(tmp, {disabled: true}));
    }
}
const addSong = async () => {
    console.log(selectRes.value);
    const result = await HttpManager.addSongsIntoList(selectRes.value,songListId.value);
    ElMessage({
        message: result.message,
        type: result.type,
    });
    if (result.success)
        dialogVisible.value = false;
    await getData();
}

watch(dialogVisible, (value) =>{
    if (!value) selectRes.value = []
})
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
ul, li {
    display: block;
}
</style>