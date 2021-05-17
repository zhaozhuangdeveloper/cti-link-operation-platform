import Axios from 'axios'
import config from '@/config'
import { Notification, Loading } from 'element-ui'

import cookie from '@/lib/cookie'
import qs from 'qs'

export const baseUrl = config.baseUrl.apiUrl

const axios = Axios.create({
  baseURL: baseUrl,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
})

let loading
axios.interceptors.request.use(
  function (config) {
    if (config.loading) {
      loading = Loading.service({
        fullscreen: true,
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    }
    // 请求头携带token
    if (cookie.getToken()) {
      config.headers['x-access-token'] = cookie.getToken()
    }
    return config
  }
)
// 添加响应拦截器
axios.interceptors.response.use(
  res => {
    if (loading) {
      loading.close()
    }
    return validateResponseCode(res)
  },
  error => {
    if (loading) {
      loading.close()
    }
    Notification({
      title: '网络请求异常，请稍后重试!',
      message: error,
      type: 'error'
    })
    return Promise.reject(error)
  }
)

function validateResponseCode (res) {
  const { data } = res
  if (data && data.code && data.code !== '00000') {
    if (data.code === 'A0301') {
      cookie.clearToken()
      localStorage.clear()
      window.location.href = window.location.pathname + '#/login'
      Notification({
        title: data.code,
        message: data.description,
        type: 'error'
      })
      return Promise.reject(res)
    } else {
      Notification({
        title: data.code,
        message: data.description,
        type: 'error'
      })
      return Promise.reject(res)
    }
  }
  return Promise.resolve(data)
}

export const postAxios = (url, data, loading) => {
  return axios.post(url, data, {
    loading: loading
  })
}

export const putAxios = (url, data, loading) => {
  return axios.put(url, data, {
    loading: loading
  })
}

export const getAxios = (url, data, loading) => {
  return axios.get(url, {
    params: data,
    loading: loading
  })
}

export const getAxiosSerializer = (url, data, loading) => {
  return axios.get(url, {
    params: data,
    paramsSerializer: params => qs.stringify(params, { indices: false }),
    loading: loading
  })
}
