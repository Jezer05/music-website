import {defineStore} from "pinia";
export const useAdminStore = defineStore({
  id: 'app-admin',
  state: ()=> ({
    id: -1,
    username: "",
    password:"",
    token: "",
    avatar: "/img/avatarImages/user.jpg",
    isLogin: false,
    breadcrumbList: [],
  }),
  // getters: {
  //     getToken: (state) => state.token,
  //     getAdminInfo: (state) => state.adminInfo,
  //     getIsLogin: (state) => state.isLogin
  // },
  actions: {
    // 登录
    login(adminInfo: object){
      this.$patch({...adminInfo, isLogin: true, breadcrumbList: []})
    },
    // 注销
    logout(){
      this.$patch({
        id: -1,
        username: "",
        password: "",
        token: "",
        isLogin: false,
        breadcrumbList: [],
      })
    },
  },
  persist: true,
})
