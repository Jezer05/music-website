<template>
    <div class="container">
        <div class="handle-box">
            <el-button @click="deleteSelected">批量删除</el-button>
            <el-input placeholder="筛选歌曲" v-model="searchWord"></el-input>
            <el-button type="primary" @click="editVisible = true;isAdd = true">添加歌单</el-button>
        </div>
        <el-table border size="small" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40" align="center"/>
            <el-table-column label="ID" prop="id" width="50" align="center"/>
            <el-table-column label="歌单图片" width="110" align="center">
                <template v-slot="scope">
                    <el-image :src="attachUrl(scope.row.pic)" style="width: 100%; height: 100px" fit="cover"/>
                    <el-upload :show-file-list="false" :on-success="handleImgSuccess" :on-error="handleImgError" :before-upload="beforeImgUpload" :http-request="updateImg">
                        <el-button @click="handleSongListId(scope.row.id)">更新图片</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="title" label="标题" width="200"/>
            <el-table-column label="简介">
                <template v-slot="scope">
                    <el-scrollbar max-height="100px">
                        <p>{{ scope.row.introduction }}</p>
                    </el-scrollbar>
                </template>
            </el-table-column>
            <el-table-column label="风格" prop="style" width="100"  align="center"/>
            <el-table-column label="内容" width="90" align="center">
                <template v-slot="scope">
                    <el-button @click="goListSong(scope.row.id)">内容</el-button>
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
  <el-dialog title="添加/编辑" v-model="editVisible" :before-close="handleEditClose">
      <el-form label-width="80px" :model="editForm" :rules="<FormRules>rules">
          <el-form-item label="歌单名" prop="name">
              <el-input v-model="editForm.title"/>
          </el-form-item>
          <el-form-item label="歌单介绍">
              <el-input v-model="editForm.introduction" type="textarea" :autosize = "{ minRows: 2, maxRows: 8 }"/>
          </el-form-item>
          <!--        这里会爆警告 scroll-blocking 'touchstart' event-->
          <el-form-item label="风格">
              <el-input v-model="editForm.style"/>
          </el-form-item>
      </el-form>
      <template #footer>
    <span class="dialog-footer">
      <el-button @click="editVisible = false">取 消</el-button>
      <el-button v-if="isAdd" type="primary" @click="addSongList">确 定</el-button>
      <el-button v-else type="primary" @click="saveEdit">确 定</el-button>
    </span>
      </template>
  </el-dialog>
</template>

<script setup lang="ts">

import {attachUrl} from "@/utils";
import {useRouter} from "@/hooks/useRouter"
import {FormRules} from "element-plus";
import {HttpManager} from "@/api/request";
import {useAdminStore} from "@/store/admin";
import {RouterName} from "@/enums";
import {SongListReqForm} from "@/api/type";
import {useUpload} from "@/hooks/useUpload";

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
    const result = await HttpManager.getAllSongList();
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
            if (item.title.includes(searchWord.value)) {
                tempData.value.push(item);
            }
        }
    }
});
//</editor-fold>

//<editor-fold desc="路由跳转">
const adminStore = useAdminStore();
const {routerManager} = useRouter();
function goListSong(id:number) {
    const breadcrumbList = reactive([
        {
            path: RouterName.SongList,
            name: "歌单管理",
        },
        {
            path: "",
            name: "歌单内容",
        },
    ]);
    adminStore.setBreadcrumbList(breadcrumbList);
    routerManager(RouterName.ListSong, {
        path: RouterName.ListSong,
        query: {songListId: id},
    });
}

function goCommentDetail(id:number) {
    const breadcrumbList = reactive([
        {
            path: RouterName.SongList,
            name: "歌单管理",
        },
        {
            path: "",
            name: "评论详情",
        },
    ]);
    adminStore.setBreadcrumbList(breadcrumbList);
    routerManager(RouterName.CommentDetail, {
        path: RouterName.CommentDetail,
        query: { id, type: 1},
    });
}
//</editor-fold>

//<editor-fold desc="添加/编辑表单">
const editVisible = ref(false);
// 标记现在是添加歌手还是更新歌手
const isAdd = ref(true);
const editForm : SongListReqForm= reactive({
    title: "",
    introduction: "",
    style: "",
});
const rules = reactive({
    title: [{ required: true, trigger: "blur", message: "歌单名称不能为空"}],
});
const clearForm = () => {
    editForm.title = ''
    editForm.introduction = ''
    editForm.style = ''
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
async function addSongList() {
    if (editForm.title.trim() == '') {
        ElMessage.error("歌单名称不能为空")
        return
    }
    const result = await HttpManager.addSongList(editForm);
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
const songListId = ref(-1);
function editRow(row:any) {
    editVisible.value = true;
    songListId.value = row.id
    editForm.title = row.title;
    editForm.introduction = row.introduction;
    editForm.style = row.style;
}
async function saveEdit() {
    if (editForm.title.trim() == '') {
        ElMessage.error("歌单名称不能为空")
        return
    }
    try {
        const result = await HttpManager.updateSongListMsg(songListId.value, editForm);
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

//<editor-fold desc="图片更新">
const {beforeImgUpload} = useUpload();
function handleSongListId(id: number){
    songListId.value = id
}
function updateImg(options : any) {
    return HttpManager.updateSongListImg(songListId.value, options.file);
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
        `确定要删除ID为${id}的歌单？`,
        '警告',
        {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
        }
    )
        .then(async () => {
            const result = await HttpManager.deleteSongList(id)
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
        `确定要删除所选的${count}个歌单吗？`,
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
            const result = await HttpManager.deleteSongLists(ids)
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