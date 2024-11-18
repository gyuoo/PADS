<template>
  <div
    class="bg-white rounded-2xl shadow-lg p-6 w-full h-full flex flex-col overflow-hidden"
  >
    <div
      class="text-3xl font-semibold text-gray-700 px-2 flex items-center justify-between mb-1"
    >
      <div class="mb-1">
        월별 이상치 집계
        <div class="text-sm text-gray-500 font-normal mt-1">
          각 월별로 발생한 이상 상품 총합 그래프
        </div>
      </div>
    </div>
    <div class="flex-1 overflow-hidden">
      <Line v-if="chartDataReady" :data="chartData" :options="chartOptions" />
      <div v-else class="text-center text-gray-500">
        데이터를 불러오는 중...
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Line } from 'vue-chartjs'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
} from 'chart.js'
import { getMonthlyAnomalyCount } from '../../api/dashboard'

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
)

const chartData = ref({
  labels: [
    'Jan',
    'Feb',
    'Mar',
    'Apr',
    'May',
    'Jun',
    'Jul',
    'Aug',
    'Sep',
    'Oct',
    'Nov',
    'Dec',
  ],
  datasets: [
    {
      label: 'total',
      data: [] as (number | null)[],
      fill: false,
      borderColor: 'rgb(75, 192, 192)',
      tension: 0.1,
    },
  ],
})
const chartDataReady = ref(false)
const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      display: false,
    },
  },
}

const getCurrentMonth = () => {
  const date = new Date()
  return date.getMonth() + 1
}

const fetchChartData = async () => {
  try {
    const data = await getMonthlyAnomalyCount()
    const monthlyData = Array(12).fill(null)

    const currentMonth = getCurrentMonth()

    Object.entries(data).forEach(([month, count]) => {
      const index = parseInt(month, 10) - 1
      if (index + 1 <= currentMonth) {
        monthlyData[index] = count
      } else {
        monthlyData[index] = null
      }
    })

    chartData.value.datasets[0].data = monthlyData
    chartDataReady.value = true
  } catch (error) {
    console.error('차트 데이터를 가져오는 데 실패했습니다.', error)
  }
}

onMounted(fetchChartData)
</script>
