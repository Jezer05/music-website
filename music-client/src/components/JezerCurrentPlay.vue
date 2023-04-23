<template>
    <transition name="aside-fade">
        <div class="current-play" v-if="showCurList">
            <h2 class="title">当前播放</h2>
            <div class="control">共 {{ (curPlayList && curPlayList.length) || 0 }} 首</div>
            <ul class="menus">
                <li
                        v-for="(item, index) in curPlayList"
                        :class="{ 'is-play': songId === item.songId }"
                        :title="getSongTitle(item.songTitle)"
                        :key="index"
                        @click="musicStore.setCurrentPlayIndex(index);musicStore.setCurrentMusic(item)">
                    {{ getSongTitle(item.songTitle) }}
                </li>
            </ul>
        </div>
    </transition>
</template>

<script setup lang="ts">
import {useMusicStore} from "@/store/music";
import {storeToRefs} from "pinia";
import {getSongTitle} from "@/utils";

const musicStore = useMusicStore();
const {curPlayList,showCurList,songId} = storeToRefs(musicStore)
</script>

<style scoped lang="scss">

.aside-fade-enter-active {
    transition: all 0.3s ease;
}

.aside-fade-leave-active {
    transition: all 0.3s ease;
}

.aside-fade-enter-from,
.aside-fade-leave-to {
    transform: translateX(10px);
    opacity: 0;
}

.current-play {
    font-size: 14px;
    width: 287px;
    position: fixed;
    background: white;
    right: 0;
    top: 58px;
    bottom: 90px;
    padding-bottom: 20px;
    z-index: 99;
    border-radius: 6px;
    box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.4);
    overflow: hidden;
}

.title,
.control,
.menus li {
    padding-left: 20px;
    box-sizing: border-box;
}

.title {
    margin: 10px 0;
}

.control {
    margin: 3px 0;
    color: grey;
}

.menus {
    width: 100%;
    height: calc(100% - 19px);
    cursor: pointer;
    z-index: 100;
    overflow: scroll;
    padding: 0;
    white-space: nowrap;
    li {
        display: block;
        width: 100%;
        height: 40px;
        line-height: 40px;
        border-radius: 10px;
        &:hover {
            background-color: #dff9fb;
        }
    }
}

.is-play {
    color: #74b9ff;
    font-weight: bold;
}
</style>