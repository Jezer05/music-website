import {defineStore} from "pinia";


export const useLoginStore = defineStore({
  id: "app-login",
  state: () => ({
    id: -1,
    username: "",
    password: "",
    avatar: "",
    token: "",
    songLikes: [-1],
    isLogin: false
  }),
  actions: {
    logout(){
      this.$patch({
        id: -1,
        username: "",
        password: "",
        avatar: "",
        token: "",
        isLogin: false
      })
    },
    login(consumerInfo: object){
      this.$patch({...consumerInfo, isLogin: true})
    },
    setSongLikes(songLikes: number[]){
      this.songLikes = songLikes;
    },
    addSongLike(songId: number){
      this.songLikes.push(songId)
    },
    deleteSongLike(songId:number){
      const index = this.songLikes.indexOf(songId)
      if (index >= 0)
        this.songLikes.splice(index,1)
    }
  },
  persist: true,
})