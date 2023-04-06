import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/dashboard',
  },
  {
    path: '/login',
    component: () => import('@/views/LoginPage.vue')
  },
  {
    path: '/',
    component: () => import('@/views/HomePage.vue'),
    meta: {
      title: '自述文件',
      requireAuth: true,
    },
    children: [
      {
        path: '/dashboard',
        component: () => import('@/views/InfoPage.vue'),
        meta: {
          title: '仪表盘',
          requireAuth: true,
        }
      },
      // {
      //     path: '/song',
      //     component: () => import('@/views/SongPage.vue'),
      //     meta: { title: 'Song' }
      // },
      {
        path: '/singer',
        component: () => import('@/views/SingerPage.vue'),
        meta: {
          title: 'Singer',
          requireAuth: true,
        }
      },
      // {
      //     path: '/SongList',
      //     component: () => import('@/views/SongListPage.vue'),
      //     meta: { title: 'SongList' }
      // },
      // {
      //     path: '/ListSong',
      //     component: () => import('@/views/ListSongPage.vue'),
      //     meta: { title: 'ListSong' }
      // },
      // {
      //     path: '/Comment',
      //     component: () => import('@/views/CommentPage.vue'),
      //     meta: { title: 'Comment' }
      // },
      // {
      //     path: '/Consumer',
      //     component: () => import('@/views/ConsumerPage.vue'),
      //     meta: { title: 'Consumer' }
      // },
      // {
      //     path: '/Collect',
      //     component: () => import('@/views/CollectPage.vue'),
      //     meta: { title: 'Collect' }
      // }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
router.beforeEach((to,from,next)=>{
  document.title = `${to.meta.title} | 麻薯音乐`;
  const  admin = localStorage.getItem("app-admin")
  let isLogin = 0;
  if (admin !=  null)
    isLogin = JSON.parse(admin).isLogin
  if (to.meta.requireAuth == true && isLogin == 0){
    ElMessage.error("您还未登录，请先登录");
    next('/login');
  }else next();
})
export default router
