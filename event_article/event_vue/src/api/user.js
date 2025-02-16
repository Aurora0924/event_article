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

//获取个人信息
export const userInfoGetService = () => {
    return request.get('/user/userInfo')
}
//修改个人信息
export const userInfoUpdateService = (data) => {
    return request.put('/user/update', data)
}
//修改头像
export const userAvatarUpdateService = (avatarUrl) => {
    let params = new URLSearchParams()
    params.append('avatarUrl', avatarUrl)
    return request.patch('/user/updateAvatar', params)
}
//修改密码
export const userPasswordUpdateService = (data) => {
    return request.patch('/user/updatePwd', data)
}