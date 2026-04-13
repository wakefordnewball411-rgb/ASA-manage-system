import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: () => import('@/views/Login.vue')
    },
    {
      path: '/user/home',
      component: () => import('@/views/admin/AdminHome.vue')
    },
    {
      path: '/admin/user',
      component: () => import('@/views/admin/AdminManage.vue')
    },
    {
      path: '/admin/employee',
      component: () => import('@/views/admin/EmployeeManage.vue')
    },
    {
      path: '/employee/home',
      component: () => import('@/views/employee/EmployeeHome.vue')
    },
    
  ]
})

router.beforeEach((to, from, next) => {
  // 登录页直接放行
  if (to.path === '/login') {
    next()
    return
  }

  const token = localStorage.getItem('Authorization')

  // 未登录，跳登录
  if (!token) {
    next('/login')
    return
  }

  // 判断用户角色（根据路由的 meta.role）
  const isAdminRoute = to.path.startsWith('/user') || to.path.startsWith('/admin')
  const isEmployeeRoute = to.path.startsWith('/employee')

  // 如果是管理员路由，正常放行
  if (isAdminRoute) {
    next()
    return
  }

  // 如果是员工路由，正常放行
  if (isEmployeeRoute) {
    next()
    return
  }

  next()
})

export default router