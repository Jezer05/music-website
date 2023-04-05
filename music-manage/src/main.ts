import { createApp } from 'vue'
import App from './App.vue'
import router from "./router";

import '@/assets/css/main.scss'
import '@/assets/font/font.scss'
import '@/assets/js/iconfont.js'
import pinia from "@/store";


createApp(App).use(router).use(pinia).mount('#app')
