import { getAxios, putAxios } from '@/lib/http'
export const deployApi = {
  // 获取deploy列表
  deployList: () => {
    return getAxios('/app/deploy/list')
  },
  // 重启deploy
  deployRestart: data => {
    return putAxios('/app/deploy/restart', data, true)
  },
  // 获取deploy详情
  deployDetail: data => {
    return getAxios('/app/deploy/detail', data, true)
  },
  // 获取deploy信息
  getDeployUpgrade: data => {
    return getAxios('/app/deploy/upgrade', data, true)
  },
  // 更新deploy
  deployUpgrade: data => {
    return putAxios('/app/deploy/upgrade', data, true)
  },
  deployUpgradeYaml: data => {
    return putAxios('/app/deploy/upgrade/yaml', data, true)
  }
}
