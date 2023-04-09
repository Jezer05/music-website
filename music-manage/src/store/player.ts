import {defineStore} from "pinia";
export const usePlayerStore = defineStore({
  id: 'app-player',
  state: () => ({
    url: "",
    isPlay: false
  }),
  actions: {
    // 暂停播放
    pause(){
      this.isPlay = false;
    },
    play() {
      this.isPlay = true;
    },
    // 设置音乐url
    setUrl(url : string){
      this.url = url;
    }
  },
})