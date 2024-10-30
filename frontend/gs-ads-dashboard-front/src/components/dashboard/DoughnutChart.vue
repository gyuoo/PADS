<template>
  <div class="bg-white rounded shadow-lg p-4 w-full h-full">
    <div class="justify-between items-center mb-4">
      <h2 class="text-lg font-semibold text-gray-800">실시간 이상치 발생 빈도</h2>
    </div>
    <Doughnut :data="chartData" :options="chartOptions" />
  </div>
</template>

<script setup lang="ts">
import { Doughnut } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, ArcElement)

const chartData = {
  labels: ['이미지이상', '가격이상', '리뷰이상', '카테고리이상'],
  datasets: [
    {
      data: [50, 23, 10, 25],
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'],
    },
  ],
}

const chartOptions = {
  responsive: true,
  plugins: {
    legend: {
      position: 'bottom' as const,
      labels: {
        font: {
          size: 11,
        },
        boxWidth: 10,
      },
    },
  },
}
</script>

<style scoped></style>