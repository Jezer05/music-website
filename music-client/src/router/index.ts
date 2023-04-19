import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomePage.vue')
  },
]


const router = createRouter({
  history: createWebHistory(),
  routes
})
// router.beforeEach((to,from,next)=>{
//   document.title = `${to.meta.title} | 麻薯音乐`;
//   // let isLogin = 0;
//   // const  admin = localStorage.getItem("app-admin")
//   // if (admin !=  null)
//   //   isLogin = JSON.parse(admin).isLogin
//   const isLogin = useAdminStore().isLogin;
//   if (to.meta.requireAuth == true && isLogin == 0){
//     ElMessage.error("您还未登录，请先登录");
//     next('/login');
//   }else next();
// })
export default router