import cookie from '@/lib/cookie'
import { localSave } from '@/lib/local'
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
      localSave('userLoginInfo', JSON.stringify(userLoginInfo))
    }
  },
  actions: {
    handleLogin ({ commit }, user) {
      return new Promise((resolve, reject) => {
        loginApi.login(user).then(res => {
          localStorage.clear()
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
