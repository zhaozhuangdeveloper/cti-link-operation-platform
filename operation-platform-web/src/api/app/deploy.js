import { getAxiosSerializer } from '@/lib/http'
export const deployApi = {
  // 获取deploy列表
  deployList: data => {
    return getAxiosSerializer('/app/deploy/list', data)
  }
}
