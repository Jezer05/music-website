import {LoginReqForm, SingerReqForm, SongListReqForm, SongReqForm} from "@/api/type";
import {axiosWithJson, axiosWithMul} from "@/api/index";
// 获得不同类型的header

export const HttpManager = {
  // 登录
  login: async (loginReqForm: LoginReqForm) => axiosWithJson.post('/admins/login',loginReqForm),

  //<editor-fold desc="歌手板块">
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
  updateSingerAvatar: async (id:number, file:any)=> axiosWithMul.put(`/singers/avatar/${id}`, {file}),
  //</editor-fold>

  //<editor-fold desc="歌曲板块">
  // 根据歌手id查找歌曲
  getSongBySingerId: async (id:number) => axiosWithJson.get(`/songs/singer/detail/${id}`),
  // 为歌手添加歌曲
  addSong: async (addSongForm: any) => axiosWithMul.post('/songs', addSongForm),
  // 更新歌手信息
  updateSongMsg: async (id:number, songReqForm: SongReqForm)=> axiosWithJson.put(`/songs/${id}`, songReqForm),
  // 更新资源
  updateSongImg: async (id:number, file:any) => axiosWithMul.put(`/songs/img/${id}`, {file}),
  updateSongUrl: async (id:number, file:any) => axiosWithMul.put(`/songs/url/${id}`, {file}),
  deleteSong: async (id:number) => axiosWithJson.delete(`/songs/${id}`),
  deleteSongs: async (ids: Array<number>)=> axiosWithJson.delete(`/songs`, {
    data: JSON.stringify(ids)
  }),
//</editor-fold>

  //<editor-fold desc="评论板块">
  // 根据歌曲Id查询评论
  getCommentBySongId: async (id: number) =>
      axiosWithJson.get(`/comments/song/detail/${id}`),
  // 根据歌单Id查询评论
  getCommentBySongListId: async (id:number) =>
      axiosWithJson.get(`/comments/songList/detail/${id}`),
  // 删除评论
  deleteComment: async (id:number) =>
      axiosWithJson.delete(`/comments/${id}`),
  deleteComments: async (ids: Array<number>)=>
      axiosWithJson.delete("/comments", {
        data: JSON.stringify(ids)
      }),
  //</editor-fold>

  //<editor-fold desc="用户板块">
  getAllConsumer: async () =>
      axiosWithJson.get('/consumers'),
  deleteConsumer: async (id:number) =>
      axiosWithJson.delete(`/consumers/${id}`),
  deleteConsumers: async (ids: Array<number>)=>
      axiosWithJson.delete('/consumers', {
        data: JSON.stringify(ids)
      }),
  //</editor-fold>

  //<editor-fold desc="收藏板块">
  // 获得用户的收藏歌曲
  getCollectSongByUserId: async (id: number) =>
      axiosWithJson.get(`/collects/song/user/${id}`),
  getCollectListByUserId: async (id:number) =>
      axiosWithJson.get(`/collects/songList/user/${id}`),
  // 删除评论
  deleteCollect: async (id:number) =>
      axiosWithJson.delete((`/collects/${id}`)),
  deleteCollects: async (ids:Array<number>)=>
      axiosWithJson.delete('/collects', {
        data: JSON.stringify(ids)
      }),
  //</editor-fold>

  //<editor-fold desc="歌单板块">
  // 查询所有
  getAllSongList: async ()=>
      axiosWithJson.get("/songLists"),
  // 删除歌单
  deleteSongList: async (id:number) =>
      axiosWithJson.delete(`/songLists/${id}`),
  deleteSongLists: async (ids: Array<number>)=>
      axiosWithJson.delete(("/songLists"), {
        data: JSON.stringify(ids)
      }),
  // 添加歌单
  addSongList: async (songList: SongListReqForm) =>
      axiosWithJson.post(`/songLists`, songList),
  // 更新信息
  updateSongListMsg: async (id: number, songList:SongListReqForm)=>
      axiosWithJson.put(`/songLists/${id}`,songList),
  updateSongListImg: async (id:number, file:any)=>
      axiosWithMul.put(`/songLists/img/${id}`, {file}),
  //</editor-fold>

  //<editor-fold desc="歌单内容">
  getListSongByListId: async (id: number) =>
      axiosWithJson.get(`/listSongs/songList/${id}`),
  // 删除
  deleteListSong: async (id:number) =>
      axiosWithJson.delete(`/listSongs/${id}`),
  deleteListSongs: async (ids: Array<number>)=>
      axiosWithJson.delete("/listSongs", {
        data: JSON.stringify(ids)
      }),
  // 获得所有歌曲，在添加时使用
  getAllSong: async () =>
      axiosWithJson.get("/songs"),
  // 往歌单中添加歌曲.
  addSongsIntoList: async (songIds: Array<number>, songListId: number) =>
      axiosWithJson.post(`/listSongs/${songListId}`, JSON.stringify(songIds))
  //</editor-fold>
}