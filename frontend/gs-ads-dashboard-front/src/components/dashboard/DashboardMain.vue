<template>
  <div class="flex flex-col md:flex-row">
    <div class="flex flex-col gap-3 w-full md:w-1/5 h-full p-2">
      <OverviewCount label="전체 상품 수" :count="allCount" />
      <OverviewCount label="대기 중인 상품 수" :count="scheduledProductCount" />
      <DoughnutChart />
    </div>
    <div class="w-full md:w-4/5 p-2">
      <AnomalyProductList class="h-full" />
    </div>
  </div>
  <div class="flex flex-col md:flex-row h-2/5">
    <div class="flex flex-col gap-3 w-full md:w-2/5 h-full p-2 overflow-y-auto">
      <RecentJobList class="h-full" />
    </div>
    <div class="w-full md:w-3/5 h-full p-2 overflow-y-auto">
      <LineChart />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import AnomalyProductList from './AnomalyProductList.vue'
import OverviewCount from './OverviewCard.vue'
import DoughnutChart from './DoughnutChart.vue'
import RecentJobList from './RecentJobList.vue'
import LineChart from './LineChart.vue'
import { getScheduleCount, getCount } from '../../api/dashboard.ts'

const allCount = ref(0)
const scheduledProductCount = ref(0)

onMounted(async () => {
  try {
    allCount.value = await getCount()
    scheduledProductCount.value = await getScheduleCount()
  } catch (error) {
    console.error('대기 중인 상품 수를 불러오는 중 오류가 발생했습니다.', error)
  }
})
</script>
