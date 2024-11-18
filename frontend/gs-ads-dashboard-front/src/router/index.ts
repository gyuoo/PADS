import { createRouter, createWebHistory } from 'vue-router'
import Main from '@/views/dashboard/Main.vue'
import ProductDetailMain from '@/views/product/ProductDetailMain.vue'
import ProductMain from '@/views/product/ProductMain.vue'
import RegisterForm from '@/views/authentication/RegisterForm.vue'
import LoginForm from '@/views/authentication/LoginForm.vue'
import BatchMain from '@/views/batch/BatchMain.vue'
// 라우터 설정
const routes = [
  {
    path: '/',
    name: '',
    component: Main,
    meta: { requiresAuth: true },
  },
  {
    path: '/product',
    name: 'product',
    component: ProductMain,
  },
  {
    path: '/batch',
    name: 'batch',
    component: BatchMain,
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterForm,
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginForm,
  },
  {
    path: '/product/:id',
    name: 'ProductDetail',
    component: ProductDetailMain,
  }
]

// 라우터 생성
const router = createRouter({
  history: createWebHistory(), // HTML5 히스토리 모드를 사용
  routes,
})

router.beforeEach((to, from, next) => {
  const isLoggedIn = localStorage.getItem('sessionId');
  if (to.matched.some((record) => record.meta.requiresAuth) && !isLoggedIn) {
    next('/login');
  } else {
    next();
  }
});

export default router
