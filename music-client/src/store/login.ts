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
    playlistLikes: [-1],
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
        songLikes: [-1],
        playlistLikes: [-1],
        isLogin: false
      })
    },
    login(consumerInfo: object){
      this.$patch({...consumerInfo, isLogin: true})
    },
    setSongLikes(songLikes: number[]){
      this.songLikes = songLikes;
    },
    setPlaylistLikes(playlistLikes : number[]){
      this.playlistLikes = playlistLikes;
    },
    addSongLike(songId: number){
      this.songLikes.push(songId)
    },
    deleteSongLike(songId:number){
      const index = this.songLikes.indexOf(songId)
      if (index >= 0)
        this.songLikes.splice(index,1)
    },
    addPlayListLike(playlistId: number){
      this.playlistLikes.push(playlistId)
    },
    deletePlaylistLike(playlistId:number){
      const index = this.playlistLikes.indexOf(playlistId)
      if (index >= 0)
        this.playlistLikes.splice(index,1)
    }
  },
  persist: {
    paths:["id", "username", "avatar", "token", "isLogin"]
  },
})