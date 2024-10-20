import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/dashboard',
    component: Layout,
    children: [{
      path: '',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }],
    hidden: true
  },
  {
    path: '/',
    component: () => import('@/views/login/index'),
    redirect: '/login'
  },

  // {
  //   path: '/',
  //   component: Layout,
  //   redirect: '/dashboard',
  //   children: [{
  //     path: 'dashboard',
  //     name: 'Dashboard',
  //     component: () => import('@/views/dashboard/index'),
  //     meta: { title: '首页', icon: 'dashboard' }
  //   }]
  // },

  {
    path: '/example',
    component: Layout,
    redirect: '/example',
    name: 'Example',
    meta: { title: '班级学员管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'classes',
        name: 'Classes',
        component: () => import('@/views/classes'),
        meta: { title: '班级管理', icon: 'el-icon-menu' }
      },
      {
        path: 'student',
        name: 'Student',
        component: () => import('@/views/student'),
        meta: { title: '学员管理', icon: 'el-icon-user-solid' }
      }
    ]
  }, 
  {
    path: '/system',
    component: Layout,
    redirect: '/system',
    name: 'System',
    meta: { title: '系统信息管理', icon: 'el-icon-s-tools' },
    children: [
      {
        path: 'dept',
        name: 'Dept',
        component: () => import('@/views/dept'),
        meta: { title: '部门管理', icon: 'el-icon-menu' }
      },
      {
        path: 'emp',
        name: 'Emp',
        component: () => import('@/views/emp'),
        meta: { title: '员工管理', icon: 'el-icon-user-solid' }
      }
    ]
  },

 
  {
    path: '/report',
    component: Layout,
    redirect: '/report',
    name: 'Report',
    meta: { title: '数据统计管理', icon: 'el-icon-s-tools' },
    children: [
      {
        path: 'emp-report',
        name: 'emp-report',
        component: () => import('@/views/emp-report'),
        meta: { title: '员工信息统计', icon: 'el-icon-s-data' }
      },
      {
        path: 'student-report',
        name: 'student-report',
        component: () => import('@/views/student-report'),
        meta: { title: '学员信息统计', icon: 'el-icon-s-data' }
      }
    ]
  },

  {
    path: '/lecture',
    component: Layout,
    redirect: '/lecture',
    name: 'Lecture',
    meta: { title: '课程教务管理', icon: 'el-icon-s-tools' },
    children: [
      {
        path: 'lecture-edit',
        name: 'lecture-edit',
        component: () => import('@/views/lecture-edit'),
        meta: { title: '课程管理', icon: 'el-icon-s-order' }
      },
      {
        path: 'lecture-manage',
        name: 'lecture-manage',
        component: () => import('@/views/lecture-manage'),
        meta: { title: '课程安排', icon: 'el-icon-s-custom' }
      }
    ]
  },
 
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
