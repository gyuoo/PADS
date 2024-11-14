<template>
    <div class="bg-white rounded-2xl shadow-lg p-6 w-full h-full flex flex-col overflow-hidden">
        <div class="text-3xl font-semibold text-gray-700 px-2 flex items-center justify-between mb-1">
          <div class="mb-1">
            월별 이상치 집계  
            <div class="text-sm text-gray-500 font-normal mt-1"> 각 월별로 발생한 이상 상품 총합 그래프</div>
          </div>
      </div>
        <div class="flex-1 overflow-hidden">
            <Line :data="chartData" :options="chartOptions" />
        </div>
    </div>
</template>

<script setup lang="ts">
import { Line } from 'vue-chartjs';
import {
    Chart as ChartJS,
    Title,
    Tooltip,
    Legend,
    LineElement,
    CategoryScale,
    LinearScale,
    PointElement,
} from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, LineElement, CategoryScale, LinearScale, PointElement);

const chartData = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    datasets: [
        {
            label: 'total',
            data: [165, 159, 180, 181, 156, 155, 140, 130, 140, 130, 123, 174],
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1,
        },
    ],
};

const chartOptions = {
    responsive: true,
    maintainAspectRatio: false, 
    plugins: {
        legend: {
            display: false, 
        },
    },
};
</script>
