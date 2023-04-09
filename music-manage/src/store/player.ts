import {defineStore} from "pinia";
export const usePlayerStore = defineStore({
  id: 'app-player',
  state: () => ({
    url: "",
    isPlay: false
  }),
  actions: {
    // 暂停
    pause(){
      this.isPlay = false;
    },
    // 播放
    play() {
      this.isPlay = true;
    },
    // 切换音乐状态
    changeState(){
      this.isPlay = !this.isPlay
    },
    // 设置音乐url
    setUrl(url : string){
      this.url = url;
    }
  },
})