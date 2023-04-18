import {defineStore} from "pinia";

export const useLoginStore = defineStore({
  id: "app-login",
  state: () => ({
    id: -1,
    username: "",
    password: "",
    avatar: "",
    token: "",
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
    }
  },
  persist: true,
})