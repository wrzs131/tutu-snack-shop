import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login.vue'),
    meta: { public: true, title: '登录' }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/orders',
    children: [
      {
        path: 'products',
        name: 'products',
        component: () => import('@/views/Product.vue'),
        meta: { title: '商品管理', roles: ['admin', 'merchant'] }
      },
      {
        path: 'categories',
        name: 'categories',
        component: () => import('@/views/Category.vue'),
        meta: { title: '分类管理', roles: ['admin', 'merchant'] }
      },
      {
        path: 'orders',
        name: 'orders',
        component: () => import('@/views/Order.vue'),
        meta: { title: '订单管理', roles: ['admin', 'merchant', 'rider'] }
      },
      {
        path: 'users',
        name: 'users',
        component: () => import('@/views/User.vue'),
        meta: { title: '用户管理', roles: ['admin'] }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫：未登录跳转到登录页
router.beforeEach((to) => {
  const token = localStorage.getItem('admin_token')
  if (to.meta.public) {
    return true
  }
  if (!token) {
    return '/login'
  }
  return true
})

export default router
