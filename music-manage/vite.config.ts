import { defineConfig } from 'vite'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    // element-plus自动导入
    AutoImport({
      resolvers: [ElementPlusResolver()]
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
    vue(),
  ],
  resolve: {
    alias: {
      '@': '/src',
      'apis': '/src/apis',
      'assets': '/src/assets',
      'components': '/src/components',
      'plugins': '/src/plugins',
      'utils': '/src/utils',
      'style': '/src/style',
    }
  },
})
