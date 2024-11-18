<template>
  <div class="flex min-h-screen bg-[#F9F9F9]">
    <aside
      class="flex flex-col shadow-xl rounded-r-lg bg-white items-center px-5 py-10"
    >
      <Sidebar />
    </aside>
    <main class="flex flex-col p-10 flex-1 space-y-5">
      <MainContentHeader :title="mainTitle"></MainContentHeader>
      <Separator class="my-4" />
      <div class="flex-1">
        <slot name="content"></slot>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import Sidebar from './Sidebar.vue'
import MainContentHeader from './MainContentHeader.vue'
import { Separator } from '../../components/ui/separator'
defineProps<{
  mainTitle: string
}>()
</script>

<style scoped></style>
