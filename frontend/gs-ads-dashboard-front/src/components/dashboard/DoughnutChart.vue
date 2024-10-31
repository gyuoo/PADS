<template>
  <div class="bg-white rounded shadow-lg p-4 w-full h-full overflow-y-hidden">
    <div class="justify-between items-center mb-4">
      <h2 class="text-xl font-semibold text-gray-800">이상치 발생 빈도</h2>
    </div>
    <DonutChart index="name" :category="'total'" :data="data" :colors="colors" class="h-[150px]"  />

    <div class="flex flex-wrap mt-4">
      <div v-for="(item, index) in data" :key="item.name" class="flex items-center mr-4 mb-2">
        <span :style="{ backgroundColor: colors[index] }" class="w-3 h-3 rounded-full inline-block mr-2"></span>
        <span class="text-sm text-gray-700">{{ item.name }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { DonutChart } from '../ui/chart-donut'


const data = [
  { name: '이미지이상', total: 50 },
  { name: '가격이상', total: 23 },
  { name: '리뷰이상', total: 10 },
  { name: '카테고리이상', total: 25 },
]
const colors = ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0']
</script>

<style scoped></style>
