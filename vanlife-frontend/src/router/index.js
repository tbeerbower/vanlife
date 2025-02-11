import { createRouter, createWebHistory } from 'vue-router'
import store from '../store'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/',
    redirect: '/locations'
  },
  {
    path: '/locations',
    name: 'Locations',
    component: () => import('../views/LocationsView.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/RegisterView.vue')
  },
  {
    path: '/profile',
    name: 'UserDetail',
    component: () => import('../views/UserDetail.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const isAuthenticated = store.getters['auth/isAuthenticated']
  console.log('Router guard - Path:', to.path, 'Authenticated:', isAuthenticated)
  
  if (to.path === '/login' || to.path === '/register') {
    if (isAuthenticated) {
      console.log('Already authenticated, redirecting to home')
      next('/')
    } else {
      console.log('Proceeding to:', to.path)
      next()
    }
  } else if (!isAuthenticated) {
    console.log('Not authenticated, redirecting to login')
    next('/login')
  } else {
    console.log('Proceeding to:', to.path)
    next()
  }
})

export default router 