import {LoginReqForm} from "@/api/type";
import {axiosWithJson, axiosWithMul} from "@/api/index";
// 获得不同类型的header

export const HttpManager = {
  // 登录
  login: async (loginReqForm: LoginReqForm) => axiosWithJson.post('/consumers/login',loginReqForm),
  // 注册
  register: async (registerForm: LoginReqForm)=> axiosWithJson.post('/consumers/register',registerForm),
}