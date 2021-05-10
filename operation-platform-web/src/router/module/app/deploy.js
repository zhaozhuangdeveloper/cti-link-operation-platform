export const deploy = [
  {
    path: 'deploy/list',
    name: 'DeployList',
    component: () => import('@/views/app/deploy/DeployList')
  },
  {
    path: 'deploy/namespace/:namespace/name/:name',
    name: 'DeployDetail',
    component: () => import('@/views/app/deploy/DeployDetail')
  }
]
