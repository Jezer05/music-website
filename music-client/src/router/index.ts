import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomePage.vue')
  },
  {
    path: '/playlist',
    name: 'playlist',
    component: () => import('@/views/PlaylistPage.vue'),
  },
  {
    path: '/playlistDetail',
    name: 'playlistDetail',
    component: () => import('@/views/PlaylistDetail.vue'),
  },
  {
    path: '/musicLibrary',
    name: 'musicLibrary',
    component: () => import('@/views/MusicPage.vue'),
  },
  {
    path: '/theme',
    name: 'theme',
    component: () => import('@/views/ThemePage.vue'),
  },
  {
    path: '/like',
    name: 'like',
    component: () => import('@/views/LikePage.vue'),
  },
  {
    path: '/search',
    name: 'search',
    component: () => import('@/views/SearchDetail.vue'),
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