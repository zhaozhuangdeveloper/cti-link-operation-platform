import { getAxios } from '@/lib/http'

export const namespaceApi = {
  // 获取namespace
  namespaceList: () => {
    return getAxios('/app/namespace/list')
  }
}
