<script setup lang="ts">
import { onMounted } from 'vue'
import { useStore } from 'vuex'

const store = useStore()

onMounted(() => {
  // 새로고침 시 Vuex 로그인 상태 복원
  store.dispatch('restoreLoginState')
})
</script>

<template>
  <router-view></router-view>
</template>