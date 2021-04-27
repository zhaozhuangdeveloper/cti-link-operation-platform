import { getAxios } from '@/lib/http'
export const podApi = {
  // 获取pod列表
  podList: data => {
    return getAxios('/app/pod/list', data)
  }
}
