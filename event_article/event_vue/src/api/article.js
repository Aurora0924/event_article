import request from '../utils/request'

// 文章列表请求
export const articleListService = () => {
    return request.get('/category')
}