<script setup lang="ts">
import { onMounted } from 'vue';
import { useUserStore } from '@/store';

// Pinia store 사용
const store = useUserStore();

onMounted(() => {
  // 새로고침 시 로그인 상태 복원
  store.restoreLoginState?.();
});
</script>

<template>
  <router-view />
</template>
