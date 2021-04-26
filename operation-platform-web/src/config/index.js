console.log('api url : ', process.env.VUE_APP_URL)
export default {
  /**
   * @description token在Cookie中存储的天数，默认1天
   */
  cookieExpires: 3,
  /**
   * @description api请求基础路径
   */
  baseUrl: {
    apiUrl: process.env.VUE_APP_URL
  }
}
