import axios from './'

    // 用户登录表单
export interface LoginReqForm {
    username: string;
    password: string;
}


// 用户登录
export const login = async (params: LoginReqForm) => {
    // 返回的数据格式可以和服务端约定
    return axios.post('/admins/login', params);
}