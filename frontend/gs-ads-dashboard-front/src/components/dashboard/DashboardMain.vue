<template>
  <div class="flex h-[560px]">
    <div class="flex flex-col gap-3 w-1/5 h-full p-2">
      <OverviewCount label="전체 상품 수" :count="16534672" />
      <OverviewCount label="대기 중인 상품 수" :count="1324" />
      <DoughnutChart />
    </div>
    <div class="w-4/5 h-full overflow-y-auto p-2">
      <AnomalyProductList />
    </div>
  </div>
</template>

<script setup lang="ts">
import AnomalyProductList from './AnomalyProductList.vue';
import OverviewCount from './OverviewCard.vue';
import DoughnutChart from './DoughnutChart.vue';
</script>

<style scoped></style>