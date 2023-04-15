import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import {ElementPlusResolver} from "unplugin-vue-components/resolvers";
import Components from 'unplugin-vue-components/vite'
import {join} from "path";

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      '@': join(__dirname, "src"),
    }
  },
  plugins: [
    AutoImport({
      include: [
        /\.[tj]sx?$/, // .ts, .tsx, .js, .jsx
        /\.vue$/, /\.vue\?vue/, // .vue
        /\.md$/, // .md
      ],
      imports: [
        // presets
        'vue',
        'vue-router',
      ],
      dts: true,
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      dirs: ['src/components'],
      resolvers: [ElementPlusResolver()],
    }),
    vue(),
  ],
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
