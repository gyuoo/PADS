<template>
  <div class="bg-white rounded shadow-lg p-4 w-full h-full pb-9">
    <div class="justify-between items-center mb-4">
      <div class="text-xl font-semibold text-gray-800 mb-1">이상치 유형별 발생 횟수</div>
      <div class="flex items-center text-gray-500 text-sm">
        <img src="https://velog.velcdn.com/images/gangintheremark/post/1a66b4c8-e1e8-43ed-b7b5-f5b6994d8ae1/image.png" alt="배치 아이콘" class="w-4 h-4 mr-1"/>
        <span>{{ today }}</span>
      </div>
    </div>

    <DonutChart index="name" :category="'total'" :data="data" :colors="colors" />

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

const today = new Date().toISOString().slice(0, 10) + ' 00:00';
const data = [
  { name: '이미지이상', total: 50 },
  { name: '가격이상', total: 23 },
  { name: '리뷰이상', total: 10 },
  { name: '카테고리이상', total: 25 },
]
const colors = ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0']
</script>

<style scoped></style>
