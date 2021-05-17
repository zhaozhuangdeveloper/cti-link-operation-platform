import { getAxios } from '@/lib/http'

export const configMapAPI = {
  // 获取configMap列表
  configMapList: () => {
    return getAxios('/configure/configMap/list')
  }
}
