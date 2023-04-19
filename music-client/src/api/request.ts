import {LoginReqForm} from "@/api/type";
import {axiosWithJson, axiosWithMul} from "@/api/index";
// 获得不同类型的header

export const HttpManager = {
  // 登录
  login: async (loginReqForm: LoginReqForm) => axiosWithJson.post('/consumers/login',loginReqForm),
  // 注册
  register: async (registerForm: LoginReqForm)=> axiosWithJson.post('/consumers/register',registerForm),
  // 获得所有歌曲
  getAllSong: async () => axiosWithJson.get('/songs'),
  // 获得所有歌单
  getAllSongList: async () =>
      axiosWithJson.get("/songLists"),
  // 获得指定歌单信息
  getSongListById: async (id:number) =>
      axiosWithJson.get(`/songLists/${id}`),
  // 获得对应歌单中的歌曲
  getListSongByListId: async (id: number) =>
      axiosWithJson.get(`/listSongs/songList/${id}`),
  // 获得对应歌单的评论
  getCommentByListId: async (id:number) =>
      axiosWithJson.get(`/comments/songList/detail/${id}`)
}