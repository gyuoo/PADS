<template>
  <div class="flex justify-between items-center">
    <div class="text-4xl font-bold">{{ title }}</div>
    <div class="flex items-center justify-center space-x-5">
      <template v-if="isLoggedIn">
        <div class="relative flex items-center space-x-3">
          <Bell></Bell>
          <div @click="toggleDropdown">
            <Avatar>
              <AvatarImage src="https://github.com/radix-vue.png" alt="@radix-vue" />
              <AvatarFallback>CN</AvatarFallback>
            </Avatar>
          </div>
          <div
            v-if="dropdownOpen"
            class="absolute right-0 mt-2 w-32 bg-white border border-gray-300 rounded-lg shadow-lg"
          >
            <button
              class="w-full px-4 py-2 text-left text-sm text-gray-700 hover:bg-gray-100"
              @click="handleLogout"
            >
              로그아웃
            </button>
          </div>
        </div>
      </template>
      <template v-else>
        <div
          class="text-gray-600 font-medium cursor-pointer hover:text-blue-500"
          @click="goToLogin"
        >
          로그인
        </div>
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../../store';
import axios from 'axios'
import { Bell } from 'lucide-vue-next'
import { Avatar, AvatarImage, AvatarFallback } from '../../components/ui/avatar'

const store = useUserStore()
const isLoggedIn = computed(() => store.isLoggedIn) // Pinia 상태 가져오기
const router = useRouter()

const dropdownOpen = ref(false)

const goToLogin = () => {
  router.push('/login')
}

const toggleDropdown = () => {
  dropdownOpen.value = !dropdownOpen.value
}

const handleLogout = async () => {
  try {
    await axios.post(
      '/api/v1/members/logout',
      { withCredentials: true }
    );

    console.log('Logout successful');

    // Pinia의 logout action 호출
    store.logout();

    router.push('/login');
  } catch (error: any) {
    console.error('Logout failed:', error.response?.data || error.message);
  }
};
defineProps<{
  title: string
}>()
</script>

<style scoped></style>
