<template>
  <div class="count-card bg-white rounded-lg shadow-lg p-4 w-full my-2">
    <div class="text-sm text-gray-500 mb-2">{{ label }}</div>
    <div class="text-3xl font-bold text-right break-words">{{ count.toLocaleString() }}</div>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';

const props = defineProps<{
  label: string;
  count: number;
}>();
</script>

<style scoped></style>
