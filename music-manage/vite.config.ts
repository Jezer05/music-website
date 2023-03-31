import { defineConfig } from 'vite'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import vue from '@vitejs/plugin-vue'
import {join} from "path";


// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    // unplugin-auto-import来实现vue函数的自动导入
    AutoImport({
      // targets to transform
      // global imports to register
      include: [
        /\.[tj]sx?$/, // .ts, .tsx, .js, .jsx
        /\.vue$/, /\.vue\?vue/, // .vue
        /\.md$/, // .md
      ],
      // global imports to register
      imports: [
        // presets
        'vue',
        'vue-router',
      ],
      dts: true,
      resolvers: [ElementPlusResolver()]
    }),
    //  unplugin-vue-components来实现vue组件库的自动按需导入
    Components({
      dts: true,
      dirs: ['src/components'],
      resolvers: [ElementPlusResolver()],
    }),
    vue(),
  ],
  resolve: {
    alias: {
      '@': join(__dirname, "src"),
    }
  },
  server: {
    proxy: {
      '/api': {
        // http://localhost:5173/api -> http://localhost:8888/api
        target: 'http://localhost:8888',
        // 自动改变请求的源头url，解决跨域请求问题
        changeOrigin: true,
        // http://localhost:8888/api -> http://localhost:8888/
        rewrite: (path) => path.replace(/^\/api/, ''),
      }
    }
  }
})
