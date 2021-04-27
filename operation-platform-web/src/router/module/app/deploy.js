export const deploy = [
  {
    path: 'deploy/list',
    name: 'DeployList',
    component: () => import('@/views/app/deploy/DeployList')
  },
  {
    path: 'deploy/detail',
    name: 'DeployDetail',
    component: () => import('@/views/app/deploy/DeployDetail')
  }
]
