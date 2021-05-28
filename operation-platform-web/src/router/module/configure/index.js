import { configMap } from '@/router/module/configure/configMap'

export const configure = [
  {
    path: 'configure',
    name: 'Configure',
    component: () => import('@/views/configure/Configure'),
    children: [
      ...configMap
    ]
  }
]
