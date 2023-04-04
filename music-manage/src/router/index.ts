import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/info',
  },
  {
    path: '/login',
    component: () => import('@/views/LoginPage.vue')
  },
  {
    path: '/',
    component: () => import('@/views/HomePage.vue'),
    meta: { title: '自述文件' },
    children: [
      {
        path: '/Info',
        component: () => import('@/views/InfoPage.vue'),
        meta: { title: 'Info' }
      },
      // {
      //     path: '/song',
      //     component: () => import('@/views/SongPage.vue'),
      //     meta: { title: 'Song' }
      // },
      // {
      //     path: '/singer',
      //     component: () => import('@/views/SingerPage.vue'),
      //     meta: { title: 'Singer' }
      // },
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

export default router
