<template>
  <div class="flex justify-between items-center">
    <div class="text-4xl font-bold">{{ title }}</div>
    <div class="flex items-center justify-center space-x-5">
      <Bell></Bell>
      <Avatar>
        <AvatarImage src="https://github.com/radix-vue.png" alt="@radix-vue" />
        <AvatarFallback>CN</AvatarFallback>
      </Avatar>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Bell } from 'lucide-vue-next'
import { Avatar, AvatarImage, AvatarFallback } from '@/components/ui/avatar'
defineProps<{
  title: string
}>()
</script>

<style scoped></style>
