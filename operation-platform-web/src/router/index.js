import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'
import Login from '@/views/login/Login'
import cookie from '@/lib/cookie'
import { localRead } from '@/lib/local'
import { app } from './module/app'

Vue.use(VueRouter)

const login = {
  path: '/login',
  name: 'Login',
  meta: {
    title: 'Login - 登录'
  },
  component: Login
}

const main = {
  path: '/',
  name: 'Main',
  component: () => import('@/views/Main'),
  children: [
    {
      path: '',
      component: () => import('@/views/home/Home')
    },
    ...app
  ]
}
const routes = [
  login,
  main
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

// 防止用户刷新丢失登录信息
if (Object.keys(store.state.user.userLoginInfo).length === 0 && localRead('userLoginInfo')
) {
  store.commit('setUserLoginInfo', JSON.parse(localRead('userLoginInfo')))
}

const LOGIN_PAGE_NAME = 'Login'

router.beforeEach((to, from, next) => {
  const token = cookie.getToken()
  if (to.meta.title) {
    document.title = to.meta.title
  } else {
    document.title = '运维开发平台'
  }
  if (!token && to.name !== LOGIN_PAGE_NAME) {
    // 未登录且要跳转的页面不是登录页
    next({
      name: LOGIN_PAGE_NAME // 跳转到登录页
    })
  } else if (!token && to.name === LOGIN_PAGE_NAME) {
    // 未登陆且要跳转的页面是登录页
    next() // 跳转
  } else if (token && to.name === LOGIN_PAGE_NAME) {
    // 已登录且要跳转的页面是登录页
    next({
      // 跳转到home页
      name: 'Main'
    })
  } else {
    next()
  }
})

export default router
