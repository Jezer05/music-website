import axios from '@/api'
import {LoginReqForm, SingerReqForm} from "@/api/type";

export const HttpManager = {
  // 登录
  login: async (params: LoginReqForm) => axios.post('/admins/login', params),
  // 查找所有歌手
  getAllSinger: async () => axios.get('/singers'),
  // 添加歌手
  addSinger: async (singerReqForm : SingerReqForm) => axios.post('/singers/add',singerReqForm),
  // 更新歌手信息
  updateSingerMsg: async (id: number, singerReqForm : SingerReqForm) => axios.put(`/singers/${id}`,singerReqForm),
  // 批量删除歌手
  deleteSinger: async (ids: Array<number>) => axios.delete(`/singers`, ids),
  // 根据id删除歌手
  deleteSingerById: async (id: number) => axios.delete(`/singers/${id}`),
  // 更新歌手图片
  updateSingerAvatar: async (id:number, file:any)=> axios.put(`/singers/update/avatar/${id}`, file)
}