import {ComponentInternalInstance} from "vue";

export function useUpload(){
  const uploadTypes = ref(["jpg", "jpeg", "png", "gif"]);
  function beforeImgUpload(file:any, el?: HTMLInputElement) {
    const ltCode = 2;
    const isLt2M = file.size / 1024 / 1024 < ltCode;
    const isSupportedFileType = uploadTypes.value.includes(file.type.replace(/image\//, ""));

    if (!isSupportedFileType) {
      ElMessage.error(`图片只支持 ${uploadTypes.value.join("、")} 格式!`);
    }
    if (!isLt2M) {
      ElMessage.error(`上传头像图片大小不能超过 ${ltCode}MB!`);
    }
    const res = isSupportedFileType && isLt2M
    if (el != null && !res) el.value = ''
    return res;
  }
  function beforeSongUpload(file : any, el?: HTMLInputElement) {
    const ltCode = 10;
    const isLt10M = file.size / 1024 / 1024 < ltCode;
    const songType = file.name.substring(file.name.lastIndexOf(".") + 1);
    const isSupportedFileType = songType === "mp3";
    if (!isSupportedFileType) {
      ElMessage.error("上传文件只能是mp3格式！");
    }
    if (!isLt10M) {
      ElMessage.error(`上传头像图片大小不能超过 ${ltCode}MB!`);
    }
    const res = isSupportedFileType && isLt10M;
    if (el != null && !res) el.value = ''
    return res;
  }
  return {beforeImgUpload, beforeSongUpload}
}