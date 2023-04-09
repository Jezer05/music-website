import {LoginReqForm, SingerReqForm, SongReqForm} from "@/api/type";
import {axiosWithJson, axiosWithMul} from "@/api/index";
// 获得不同类型的header

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
  updateSingerMsg: async (id: number, singerReqForm : SingerReqForm) => axiosWithJson.put(`/singers`,singerReqForm),
  // 批量删除歌手
  deleteSingers: async (ids: Array<number>) => axiosWithJson.delete(`/singers`, {
    data: JSON.stringify(ids)
  }),
  // 根据id删除歌手
  deleteSinger: async (id: number) => axiosWithJson.delete(`/singers/${id}`),
  // 更新歌手图片
  updateSingerAvatar: async (id:number, file:any)=> axiosWithMul.put(`/singers/avatar/${id}`, {"file": file}),
  /**
   * 歌曲模块
   */
  // 根据歌手id查找歌曲
  getSongBySingerId: async (id:number) => axiosWithJson.get(`/songs/${id}`),
  // 为歌手添加歌曲
  addSong: async (songReqForm: SongReqForm, file:any) => axiosWithJson.post('/songs', {
    file,
    songReqForm
  })
}