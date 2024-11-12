<template>
  <div class="flex-col justify-center content-center sticky top-0 z-10">
    <div class="flex flex-col space-y-5">
      <Button variant="ghost" class="text-base" @click="move('/')">
        <House class="mr-5" /> 대시보드
      </Button>

      <Button variant="ghost" class="text-base" @click="move('/product')">
        <ShoppingCart class="mr-5" /> 상품 현황
      </Button>

      <Button variant="ghost" class="text-base" @click="move('/batch')">
        <ClipboardCheck class="mr-5" /> 배치 현황
      </Button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import Button from '../ui/button/Button.vue'
import { House, ShoppingCart, ClipboardCheck } from 'lucide-vue-next'
const router = useRouter()
const move = (to: string) => {
  router.push(to)
}
</script>
