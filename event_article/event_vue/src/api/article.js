import request from '../utils/request'

// 文章列表请求
export const articleAllService = () => {
    return request.get('/category/list')
}
// 文章添加请求
export const articleAddService = (data) => {
    return request.post('/category/add', data)
}

// 文章修改请求
export const articleUpdateService = (data) => {
    return request.put('/category/update', data)
}

// 文章删除请求
export const articleDeleteService = (id) => {
    return request.delete('/category/'+id)
}
// 文章详情请求
export const articleDetailService = (data) => {
    return request.get('/article/list',{params:data})
}
//添加文章请求
export const articleAddArticleService = (data) => {
    return request.post('/article/add', data)
}
//修改文章请求
export const articleUpdateArticleService = (data) => {
    return request.put('/article/update', data)
}
//删除文章请求
export const articleDeleteArticleService = (id) => {
    return request.delete('/article/'+id)
}