import {defineStore} from "pinia";
interface MusicDetail{
  songId:number,
  songTitle:string,
  songUrl:string,
  songPic:string,
}

interface Music {
  songId:number,
  songTitle:string,
  songUrl:string,
  songPic:string,
  isPlay: boolean,
  // duration: number,
  // playMode: number,
  // curTime: number,
  // changeTime: number;
  curPlayList: Array<MusicDetail>,
  curPlayIndex:number
}
export const useMusicStore = defineStore({
  id: "app-music",
  state: ():Music => ({
    /** 音乐信息 */
    songId: 1, // 音乐 ID
    songTitle: "张杰-仰望星空", // 歌名
    songUrl: "/song/张杰-仰望星空.mp3", // 音乐 URL
    songPic: "/img/songPic/haikuotiankong.jpg", // 歌曲图片
    isPlay: false, // 播放状态
    /** 音乐列表信息 */
    curPlayList: [], // 当前播放列表
    curPlayIndex: -1, // 当前歌曲在歌曲列表的位置
  }),
  actions: {
    pause(){
      this.isPlay = false;
    },
    play(){
      this.isPlay = true;
    },
    // 切换音乐状态
    togglePlay(){
      this.isPlay = !this.isPlay
    },
    setCurrentPlayIndex(index: number) {
      this.curPlayIndex = index;
    },
    setCurrentMusic(musicDetail: MusicDetail){
      this.$patch({...musicDetail});
    },
  },
  persist: {
    paths: ["songId","songTitle","songUrl","songPic","curPlayList","curPlayIndex"]
  }
})
