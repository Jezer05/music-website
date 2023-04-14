import { RouterName } from "@/enums";
import {LocationQueryRaw} from "vue-router";
import {ComponentInternalInstance, getCurrentInstance} from "vue";


interface routerOptions {
  path: string;
  query?: LocationQueryRaw;
}
export  function useRouter(){
  const { proxy } = getCurrentInstance() as ComponentInternalInstance;
  // 路由管理
  function routerManager(routerName: string | number, options: routerOptions) {
    switch (routerName) {
      case RouterName.SongDetail:
      case RouterName.ListSong:
      case RouterName.CommentDetail:
      case RouterName.Consumer:
      case RouterName.CollectDetail:
        proxy?.$router.push({ path: options.path, query: options.query });
        break;
      case RouterName.Song:
      case RouterName.Home:
      case RouterName.SignIn:
      case RouterName.SignOut:
      case RouterName.Info:
      case RouterName.Singer:
      case RouterName.Comment:
      case RouterName.Collect:
      case RouterName.SongList:
      case RouterName.Error:
      default:
        proxy?.$router.push({ path: options.path});
        break;
    }
  }

  return{
    routerManager,
  }
}