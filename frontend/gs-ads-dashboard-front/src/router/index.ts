import { createRouter, createWebHistory } from 'vue-router'
import Main from '@/views/dashboard/Main.vue'
import ProductMain from '@/views/product/ProductMain.vue'
import ProductDetailMain from '@/views/product/ProductDetailMain.vue'
// 라우터 설정
const routes = [
  {
    path: '/',
    name: '',
    component: Main,
  },
  {
    path: '/product',
    name: 'product',
    component: ProductMain,
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

export default router
