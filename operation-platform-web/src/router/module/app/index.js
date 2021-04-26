import { deploy } from '@/router/module/app/deploy'

export const app = [
  {
    path: 'app',
    name: 'App',
    component: () => import('@/views/app/App'),
    children: [
      ...deploy
    ]
  }
]
