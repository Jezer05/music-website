import {defineStore} from "pinia";
export interface MusicDetail{
  songId:number,
  songTitle:string,
  songUrl:string,
  songPic:string,
  lyric?:string
}

interface Music {
  songId:number,
  songTitle:string,
  songUrl:string,
  songPic:string,
  isPlay: boolean,
  // duration: number,
  playMode: number,
  curTime: number,
  // changeTime: number;
  showCurList: boolean,
  showLyric: boolean,
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
    curTime: 0,
    playMode: 0, // 播放模式
    /** 音乐列表信息 */
    showCurList: false,
    showLyric: false,
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
    // 更换音乐播放模式
    changePlayMode(){
      this.playMode = (this.playMode + 1) % 3
    },
    setCurrentPlayIndex(index: number) {
      this.curPlayIndex = index;
    },
    setCurrentMusic(musicDetail: MusicDetail){
      this.$patch({...musicDetail});
      this.isPlay = true;
    },
    addMusicToList(musicDetail: MusicDetail){
      this.curPlayList.splice(this.curPlayIndex, 0, musicDetail);
      this.curPlayIndex = (this.curPlayIndex + 1) % this.curPlayList.length
      this.$patch({...musicDetail})
      this.isPlay = true
    },
    setCurPlayList(list:MusicDetail[], index:number){
      this.curPlayList = list;
      this.$patch({...list[index]});
      this.isPlay = true;
    },
    toggleShowCurList(){
      this.showCurList =  !this.showCurList;
    },
    toggleShowLyric(){
      this.showLyric = !this.showLyric;
    }
  },
  persist: {
    paths: ["songId","songTitle","songUrl","songPic", "playMode", "curPlayList","curPlayIndex"]
  }
})
