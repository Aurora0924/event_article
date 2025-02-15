import request from '../utils/request'

// 用户注册请求
export const userRegisterService = (data) => {
    const params = new URLSearchParams()
    for(let key in data) {
        params.append(key, data[key])
    }
  return request.post('/user/register', params)
}

// 用户登录请求
export const userLoginService = (data) => {
    const params = new URLSearchParams()
    for(let key in data) {
        params.append(key, data[key])
    }
  return request.post('/user/login', params)
}