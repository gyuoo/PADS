<template>
  <div class="bg-white rounded-2xl shadow-lg p-4 w-full h-full pb-9">
    <div class="justify-between items-center mb-4">
      <div class="text-xl font-semibold text-gray-800 mb-1">이상치 유형별 발생 횟수</div>
      <div class="flex items-center text-gray-500 text-sm">
        <span>이상치 점수가 20 이상</span>
      </div>
    </div>
    <DonutChart :category="'total'" :data="chartData" :colors="colors" />
    <div class="flex flex-wrap mt-4">
      <div v-for="(item, index) in chartData" :key="item.name" class="flex items-center mr-4 mb-2">
        <span :style="{ backgroundColor: colors[index] }" class="w-3 h-3 rounded-full inline-block mr-2"></span>
        <span class="text-sm text-gray-700">{{ item.name }}</span>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { DonutChart } from '../ui/chart-donut';
import { getCountsByCodes } from '@/api/dashboard'

const today = new Date().toISOString().slice(0, 10) + ' 00:00';

const chartData = ref([
  { name: '카테고리 이상', total: 0 },
  { name: '할인 이상', total: 0 },
  { name: '이미지 이상', total: 0 },
  { name: '가격 이상', total: 0 },
  { name: '리뷰 이상', total: 0 }
]);

const colors = ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#FF9F40'];

const loadChartData = async () => {
  try {
    const response = await getCountsByCodes();
    const counts = response;

    chartData.value = [
      { name: '카테고리 이상', total: counts.A },
      { name: '할인 이상', total: counts.B },
      { name: '이미지 이상', total: counts.C },
      { name: '가격 이상', total: counts.D },
      { name: '리뷰 이상', total: counts.E },
    ];
  } catch (error) {
    console.error("데이터를 불러오는 데 오류가 발생했습니다.", error);
  }
};

onMounted(() => {
  loadChartData();
});
</script>
<style></style>