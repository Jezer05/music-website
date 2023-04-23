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
      axiosWithJson.get(`/comments/songList/detail/${id}`),
  // 查找用户收藏的歌曲
  getCollectSong: async (id:number) =>
      axiosWithJson.get(`/collects/song/user/${id}`),
  // 查找用户收藏的歌单
  getCollectList: async (id:number) =>
      axiosWithJson.get(`/collects/list/user/${id}`),
  // 添加收藏歌曲
  addCollectSong: async (id:number, songId:number)=>
      axiosWithJson.post(`/collects/song/${id}`, {songId}),
  // 取消收藏歌曲
  delCollectSong: async (id:number, songId:number) =>
      axiosWithJson.delete(`/collects/song/${id}?songId=${songId}`),
  // 添加收藏List
  addCollectList: async (id:number, songListId:number)=>
      axiosWithJson.post(`/collects/list/${id}`, {songListId}),
  // 取消收藏歌曲
  delCollectList: async (id:number, listId:number) =>
      axiosWithJson.delete(`/collects/list/${id}?listId=${listId}`),
  // 根据名称查询音乐
  getSongByName: async (name:string) =>
      axiosWithJson.get(`/songs/name?name=${name}`),
  // 根据名称查询歌单
  getListByTitle: async (title:string) =>
      axiosWithJson.get(`/songLists/title?title=${title}`),
  // 根据歌曲id查询信息
  getMusicById: async (id: number) =>
      axiosWithJson.get(`/songs/${id}`),
}