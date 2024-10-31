<template>
  <div class="flex flex-col md:flex-row h-full">
    <div class="flex flex-col gap-3 w-full md:w-1/5 h-full p-2">
      <OverviewCount label="전체 상품 수" :count="16534672" />
      <OverviewCount label="대기 중인 상품 수" :count="1324" />
      <DoughnutChart />
    </div>
    <div class="w-full md:w-4/5 h-full p-2">
      <AnomalyProductList class="h-full" />
    </div>
  </div>
</template>

<script setup lang="ts">
import AnomalyProductList from './AnomalyProductList.vue';
import OverviewCount from './OverviewCard.vue';
import DoughnutChart from './DoughnutChart.vue';
</script>
