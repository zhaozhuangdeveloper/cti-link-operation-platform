import cookie from '@/lib/cookie'
import { loginApi } from '@/api/login'

export default {
  state: {
    token: cookie.getToken(),
    userLoginInfo: {}
  },
  mutations: {
    // 设置token
    setToken (state, token) {
      state.token = token
      cookie.setToken(token)
    },
    // 保存用户登录信息
    setUserLoginInfo (state, userLoginInfo) {
      state.userLoginInfo = userLoginInfo
    }
  },
  actions: {
    handleLogin ({ commit }, user) {
      return new Promise((resolve, reject) => {
        loginApi.login(user).then(res => {
          const data = res.data
          commit('setToken', data.token)
          // 保存用户登录
          commit('setUserLoginInfo', data.userInfo)
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
    }
  },
  modules: {
  }
}
