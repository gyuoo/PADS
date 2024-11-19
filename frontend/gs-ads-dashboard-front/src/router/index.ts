import { createRouter, createWebHistory } from 'vue-router'
import Main from '@/views/dashboard/Main.vue'
import ProductDetailMain from '@/views/product/ProductDetailMain.vue'
import ProductMain from '@/views/product/ProductMain.vue'
import RegisterForm from '@/views/authentication/RegisterForm.vue'
import LoginForm from '@/views/authentication/LoginForm.vue'
import BatchMain from '@/views/batch/BatchMain.vue'
import { defineStore } from 'pinia';
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
    meta: { requiresAuth: true },
  },
  {
    path: '/batch',
    name: 'batch',
    component: BatchMain,
    meta: { requiresAuth: true },
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
    meta: { requiresAuth: true },
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, _from, next) => {
  const isLoggedIn = localStorage.getItem('sessionId');
  if (to.meta.requiresAuth && !isLoggedIn) {
    next('/login');
  } else {
    next();
  }
});

export default router

export const useUserStore = defineStore('user', {
  state: () => ({
    isLoggedIn: false,
    sessionId: '',
  }),
  actions: {
    login(sessionId: string) {
      this.isLoggedIn = true;
      this.sessionId = sessionId;
      localStorage.setItem('sessionId', sessionId);
    },
    logout() {
      this.isLoggedIn = false;
      this.sessionId = '';
      localStorage.removeItem('sessionId');
    },
    restoreLoginState() {
      const sessionId = localStorage.getItem('sessionId');
      if (sessionId) {
        this.login(sessionId);
      }
    },
  },
});
