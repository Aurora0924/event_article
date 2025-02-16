import { createRouter, createWebHistory } from 'vue-router'


const routes = [
    {
        path: '/login',
        component: () => import('../views/Login.vue'),
    },

    {
        path: '/',
        component: () => import('../views/Layout.vue'),
        redirect: '/article/manage',
        children: [
            {
                path: '/article/category',
                component: () => import('../views/article/ArticleCategory.vue'),

            },
            {
                path: '/article/manage',
                component: () => import('../views/article/ArticleManage.vue'),
            },
            {
                path: '/user/info',
                component: () => import('../views/user/UserInfo.vue'),
            },
            {
                path: '/user/avatar',
                component: () => import('../views/user/UserAvatar.vue'),
            },
            {
                path: '/user/resetPassword',
                component: () => import('../views/user/UserResetPassword.vue'),
            },

        ]

    },
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

export default router