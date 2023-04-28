import axios, {AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse} from "axios";
import {useLoginStore} from "@/store/login";
import router from "@/router";
// 数据返回的接口
interface Result{
    code: string;
    success: boolean;
    message: string;
    type: "error" | "warning" | "success" | "info";
    data: any;
}
// axios基础url，这里使用vite的proxy，所以要设置为空
const URL = '';
enum RequestEnums {
    TIMEOUT = 20000,
    // token失效
    EXPIRED = 6000,

}

const config: AxiosRequestConfig = {
  // 默认地址
  baseURL: URL as string,
  // 设置超时时间
  timeout: RequestEnums.TIMEOUT as number,
  // 跨域时候允许携带凭证
  withCredentials: true,
}

export class RequestHttp {
    // 定义成员变量并指定类型
    service: AxiosInstance;
    public constructor(config: AxiosRequestConfig, flag: "json" | "mul") {
      // 实例化axios,加载app时配置且为一次性的，不支持后期修改
      this.service = axios.create(config);
      let type = 'application/json';
      if (flag === "mul"){
        type = 'multipart/form-data'
      }
      // 通过拦截器进行配置，会造成后期自定义配置被覆盖
      /**
       * 请求拦截器
       * 客户端发送请求 -> [请求拦截器] -> 服务器
       * token校验(JWT) : 接受服务器返回的token,存储到vuex/pinia/本地储存当中
       */
      this.service.interceptors.request.use(
        // 这里有axios的bug，按理应该是AxiosRequestConfig
        (config:any)  => {
          const token = useLoginStore().token;
          return {
            ...config,
            headers: {
              'Content-Type': type,
              'x-access-token': token, // 请求头中携带token信息
            }
          }
        },
        (error: AxiosError) => {
          // 请求报错
          Promise.reject(error).catch()
        }
      )

        /**
         * 响应拦截器
        * 服务器换返回信息 -> [拦截统一处理] -> 客户端JS获取到信息
        */
      this.service.interceptors.response.use(
        (response: AxiosResponse) => {
        const {data} = response; // 解构
        if (data.code === RequestEnums.EXPIRED) {
          // 登录信息失效，应跳转到登录页面，并清空pinia和localStorage的数据
          useLoginStore().logout();
          ElMessage.error("身份过期，请重新登录")
          return Promise.reject(data);
        }
        return data;
      },
        (error: AxiosError) => {
          const {response} = error;
          if (response) {
            this.handleCode(response.status)
          }
          if (!window.navigator.onLine) {
            ElMessage.error('网络连接失败');
            // 可以跳转到错误页面，也可以不做操作
          }
          return Promise.reject(response);
      }
      )
    }
    handleCode(code: number):void {
      switch(code) {
        case 401:
          ElMessage.error('登录失败，请重新登录');
          break;
        case 404:
          ElMessage.error("请求不存在");
          break;
        default:
          ElMessage.error('请求失败');
          break;
      }
    }

    // 常用方法封装
    get(url: string, config:AxiosRequestConfig = {} ): Promise<Result> {
      return this.service.get("api" + url, config)
    }
    post(url: string, data:any = {}, config:AxiosRequestConfig = {}): Promise<Result> {
      return this.service.post("api" + url, data, config)
    }
    delete(url: string, config:AxiosRequestConfig = {}): Promise<Result> {
      return this.service.delete("api" + url, config)
    }
    put(url: string, data:any = {}, config:AxiosRequestConfig = {}): Promise<Result> {
      return this.service.put("api" + url, data, config)
    }

}
export const axiosWithJson = new RequestHttp(config, "json")
export const axiosWithMul = new RequestHttp(config, "mul");

