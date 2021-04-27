import { getAxios, getAxiosSerializer, putAxios } from '@/lib/http'
export const deployApi = {
  // 获取deploy列表
  deployList: data => {
    return getAxiosSerializer('/app/deploy/list', data)
  },
  // 重启deploy
  deployRestart: data => {
    return putAxios('/app/deploy/restart', data, true)
  },
  // 获取deploy详情
  deployDetail: data => {
    return getAxios('/app/deploy/detail', data, true)
  }
}
