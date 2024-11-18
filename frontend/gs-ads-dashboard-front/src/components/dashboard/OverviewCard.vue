<template>
  <div class="bg-white rounded-2xl shadow-lg p-4 w-full">
    <div class="text-sm text-gray-500 mb-2">{{ label }}</div>
    <div class="text-3xl font-bold text-right break-words">{{ animatedCount.toLocaleString() }}</div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, watch } from 'vue';

const props = defineProps<{
  label: string;
  count: number;
}>();

const animatedCount = ref(0);

const animateCount = (targetValue: number) => {
  if (targetValue <= 0) {
    animatedCount.value = targetValue;
    return;
  }

  const duration = 500;
  const startTime = performance.now();
  const initialCount = animatedCount.value;

  const updateCount = (currentTime: number) => {
    const elapsed = currentTime - startTime;
    const progress = Math.min(elapsed / duration, 1);
    animatedCount.value = Math.floor(initialCount + progress * (targetValue - initialCount));

    if (progress < 1) {
      requestAnimationFrame(updateCount);
    }
  };

  requestAnimationFrame(updateCount);
};

watch(
  () => props.count,
  (newCount) => {
    animateCount(newCount);
  },
  { immediate: true } 
);
</script>

<style scoped></style>
