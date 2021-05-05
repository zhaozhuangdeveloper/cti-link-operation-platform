import { getAxios } from '@/lib/http'
export const registryApi = {
  // 获取tags
  tagList: data => {
    return getAxios('/app/registry/tag/list', data)
  }
}
