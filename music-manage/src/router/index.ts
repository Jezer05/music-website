import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import {useAdminStore} from "@/store/admin";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/dashboard',
  },
  {
    path: '/login',
    component: () => import('@/views/LoginPage.vue'),
    meta: {
      title: '登录',
    },
  },
  {
    path: '/',
    component: () => import('@/views/HomePage.vue'),
    meta: {
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
      {
        path: '/singer',
        component: () => import('@/views/SingerPage.vue'),
        meta: {
          title: '歌手',
          requireAuth: true,
        }
      },
      {
        path: '/songDetail',
        component: () => import('@/views/SongDetail.vue'),
        meta: {
          title: '歌曲详情',
          requireAuth: true,
        }
      },
      {
        path: '/commentDetail',
        component: () => import('@/views/CommentDetail.vue'),
        meta: {
          title: '评论详情',
          requireAuth: true,
        }
      },
      {
        path: '/consumer',
        component: () => import('@/views/ConsumerPage.vue'),
        meta: {
          title: '用户管理',
          requireAuth: true,
        }
      },
      {
        path: '/collectDetail',
        component: () => import('@/views/CollectDetail.vue'),
        meta: {
          title: '收藏歌曲',
          requireAuth: true,
        }
      },
      {
          path: '/songList',
          component: () => import('@/views/SongListPage.vue'),
          meta: {
            title: '歌单信息',
            requireAuth: true
          }
      },
      {
          path: '/ListSong',
          component: () => import('@/views/ListSong.vue'),
          meta: {
            title: 'ListSong' ,
            requireAuth: true,
          }
      },



    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
router.beforeEach((to,from,next)=>{
  document.title = `${to.meta.title} | 麻薯音乐`;
  // let isLogin = 0;
  // const  admin = localStorage.getItem("app-admin")
  // if (admin !=  null)
  //   isLogin = JSON.parse(admin).isLogin
  const isLogin = useAdminStore().isLogin;
  if (to.meta.requireAuth == true && isLogin == 0){
    ElMessage.error("您还未登录，请先登录");
    next('/login');
  }else next();
})
export default router
