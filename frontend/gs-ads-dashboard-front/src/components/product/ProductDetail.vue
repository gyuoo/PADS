<template>
    <div class="flex flex-col md:flex-row">
        <div class="flex flex-col gap-3 w-full md:w-1/2 h-full p-2">
            <ProductInfo />
        </div>
        <div class="flex flex-col gap-3 w-full md:w-1/2 h-full p-2">
            <ProductAnomalyReport />
        </div>
    </div>
</template>

<script setup lang="ts">
import ProductAnomalyReport from './ProductAnomalyReport .vue';
import ProductInfo from './ProductInfo.vue';


</script>

<style scoped></style>