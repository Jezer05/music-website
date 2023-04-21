<template>
    <div class="content-section menuBar-mv">
        <div class="content-section-title">
            <h2>主题</h2>
        </div>
        <div class="theme-preset">
            <div class="content-section">
                <div class="apps-card ">
                    <div class="apps-item mv-text" v-for="(item, index) in preset" :key="index"
                         @click="showTheme(item.gbrUrl)">
                        <div class="app-card m-theme">
                            <img :src="getAssetImg(item.colorUrl)" alt="">
                        </div>
                        <a href="javascript:">{{ item.colorName }}</a>
                    </div>

                </div>
            </div>
        </div>
        <div class="content-section-title">
            <h2>自定义</h2>
        </div>
        <el-color-picker v-model="backgroundColor" show-alpha :predefine="predefineColors" @change="showTheme" />
    </div>
</template>

<script setup lang="ts">
import {useThemeStore} from "@/store/theme";
import {storeToRefs} from "pinia";

const themeStore  = useThemeStore()
const {backgroundColor} = storeToRefs(themeStore)

const preset = reactive([
  {
    colorName: '默认',
    colorUrl: '默认.png',
    gbrUrl: '#dde2eb'
  },
  {
    colorName: '牡丹粉红',
    colorUrl: '牡丹粉红.png',
    gbrUrl: '#eea2a4'
  },
  {
    colorName: '栗紫',
    colorUrl: '栗紫.png',
    gbrUrl: '#5a191b'
  },
  {
    colorName: '香叶红',
    colorUrl: '香叶红.png',
    gbrUrl: '#f07c82'
  },
  {
    colorName: '艳红',
    colorUrl: '艳红.png',
    gbrUrl: '#ed5a65'
  },
  {
    colorName: '茶花红',
    colorUrl: '茶花红.png',
    gbrUrl: '#ee3f4d'
  },

])

const predefineColors = ref([
  '#ff4500',
  '#ff8c00',
  '#ffd700',
  '#90ee90',
  '#00ced1',
  '#1e90ff',
  '#c71585',
  'rgba(255, 69, 0, 0.68)',
  'rgb(255, 120, 0)',
  'hsv(51, 100, 98)',
  'hsva(120, 40, 94, 0.5)',
  'hsl(181, 100%, 37%)',
  'hsla(209, 100%, 56%, 0.73)',
  '#c7158577',
])

const getAssetImg = (imgName:string) => {
  return `http:/api/img/theme/${imgName}`
}

const showTheme = (preset:string) => {
  themeStore.setThemeColor(preset)
  document.querySelector("body")!.setAttribute("style", `background-color:${backgroundColor.value};transition: 0.7s;`);
}
</script>

<style scoped>

</style>