import { defineConfig } from 'vite'
import IconsResolver from 'unplugin-icons/resolver'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import vue from '@vitejs/plugin-vue'
import {join} from "path";
import OptimizationPersist from 'vite-plugin-optimize-persist'
import PkgConfig from 'vite-plugin-package-config'


// https://vitejs.dev/config/
export default defineConfig({
  base: './', // 添加这个属性
  resolve: {
    alias: {
      '@': join(__dirname, "src"),
    }
  },
  plugins: [
    // 缓解加载慢的问题
    PkgConfig(),
    OptimizationPersist(),
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
      resolvers: [
        ElementPlusResolver(),
        // Auto import icon components
        // 自动导入图标组件
        IconsResolver({
          prefix: 'Icon',
        }),
      ]
    }),
    //  unplugin-vue-components来实现vue组件库的自动按需导入
    Components({
      dts: true,
      dirs: ['src/components'],
      resolvers: [
        // Auto register icon components
        // 自动注册图标组件
        IconsResolver({
          enabledCollections: ['ep'],
        }),
        // Auto register Element Plus components
        // 自动导入 Element Plus 组件
        ElementPlusResolver()
      ],
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
