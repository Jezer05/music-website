import {LoginReqForm, SingerReqForm} from "@/api/type";
import {useAdminStore} from "@/store/admin";
import {axiosWithJson} from "@/api/index";
const adminStore = useAdminStore();
let token = adminStore.token;
adminStore.$subscribe((mutation, state) =>
    token = adminStore.token, {detached: true})
// 获得不同类型的header
function setHeaders(flag: number){
  if (flag == 1) {
    return {
      'x-access-token': token,
      'Content-Type': 'application/json'
    }
  }else if (flag == 2){
    return {
      'x-access-token': token,
      'Content-Type': 'multipart/form-data'
    }
  }
}

export const HttpManager = {
  // 登录
  login: async (loginReqForm: LoginReqForm) => axiosWithJson.post('/admins/login',loginReqForm),
  /**
   * 歌手模块
   */
  // 查找所有歌手
  getAllSinger: async () => axiosWithJson.get('/singers'),
  // 添加歌手
  addSinger: async (singerReqForm : SingerReqForm) => axiosWithJson.post('/singers',singerReqForm),
  // 更新歌手信息
  updateSingerMsg: async (id: number, singerReqForm : SingerReqForm) => axiosWithJson.put(`/singers/${id}`,singerReqForm),
  // 批量删除歌手
  deleteSingers: async (ids: Array<number>) => axiosWithJson.delete(`/singers`, {
    data: JSON.stringify(ids)
  }),
  // 根据id删除歌手
  deleteSinger: async (id: number) => axiosWithJson.delete(`/singers/${id}`),
  // 更新歌手图片
  updateSingerAvatar: async (id:number, file:any)=> axiosWithJson.put(`/singers/avatar/${id}`, {"file": file})
}