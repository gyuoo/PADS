<template>
  <div class="bg-white rounded-lg shadow-lg p-6 w-full h-full overflow-y-hidden">
    <div class="text-3xl font-semibold text-gray-700 mb-1 px-2">
      이상 상품 현황
      <span class="inline-block bg-blue-100 text-blue-800 rounded-full px-3 py-1 ml-2 mb-1 text-sm font-semibold">
        총 {{ anormalProducts.length }}건
      </span>
    </div>
    <div class="text-sm text-gray-500 mb-4 px-2">{{ today }}</div>
    <table class="min-w-full rounded-lg">
      <thead>
        <tr>
          <th v-for="column in columns" :key="column.field" @click="sortBy(column.field)" :class="column.class"
            class="px-4 pb-6 text-left text-sm font-semibold text-gray-600 cursor-pointer">
            {{ column.label }}
            <span>
              <img :src="getSortIcon(column.field)" alt="정렬 아이콘" class="inline-block w-4 h-4 ml-3" />
            </span>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in sortedProducts" :key="product.id" class="hover:bg-gray-50 cursor-pointer">
          <td class="px-4 py-2 text-sm text-gray-700">{{ product.name }}</td>
          <td class="px-4 py-2 text-sm text-gray-700">{{ product.category }}</td>
          <td class="px-4 py-2 text-sm text-gray-700">{{ product.updatedAt }}</td>
          <td class="px-4 py-2 text-sm text-gray-700">
            <div class="flex items-center">
              <div class="w-2/3 bg-gray-200 rounded-full h-3">
                <div class="h-3 rounded-full" :class="{
                  'bg-orange-600': product.anomalyScore >= 50 && product.anomalyScore < 70,
                  'bg-red-600': product.anomalyScore >= 70 && product.anomalyScore < 90,
                  'bg-red-700': product.anomalyScore >= 90
                }" :style="{ width: product.anomalyScore + '%' }"></div>
              </div>
              <span class="ml-2 text-sm font-semibold text-gray-700">{{ product.anomalyScore }}%</span>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import anormalyProductsData from '../../data/anormalyProducts.json';

const today = ref<string>('');
const sortColumn = ref<string>("name");
const sortOrder = ref<'asc' | 'desc'>('asc');

const anormalProducts = ref(anormalyProductsData);

const columns = [
  { field: 'name', label: '상품명', class: 'w-48' },
  { field: 'category', label: '카테고리', class: 'w-36' },
  { field: 'updatedAt', label: '업데이트 날짜', class: 'w-32' },
  { field: 'anomalyScore', label: '이상치', class: 'w-32' }
];

onMounted(() => {
  const date = new Date();
  today.value = date.toLocaleString('en-US', {
    timeZone: 'Asia/Seoul',  
    year: 'numeric',
    month: 'short',      
    day: '2-digit'         
  });
});

const sortedProducts = computed(() => {
  return [...anormalProducts.value].sort((a, b) => {
    const fieldA = a[sortColumn.value as keyof typeof a];
    const fieldB = b[sortColumn.value as keyof typeof b];

    if (fieldA < fieldB) return sortOrder.value === 'asc' ? -1 : 1;
    if (fieldA > fieldB) return sortOrder.value === 'asc' ? 1 : -1;
    return 0;
  });
});

function getSortIcon(column: string) {
  if (sortColumn.value === column) {
    return sortOrder.value === 'asc'
      ? 'https://velog.velcdn.com/images/gangintheremark/post/b481b151-bfee-406f-9d33-9de897efed9b/image.png'
      : 'https://velog.velcdn.com/images/gangintheremark/post/09a348f3-b576-404a-803c-dc25b31a9be0/image.png';
  }
  return 'https://velog.velcdn.com/images/gangintheremark/post/b481b151-bfee-406f-9d33-9de897efed9b/image.png';
}

function sortBy(column: string) {
  if (sortColumn.value === column) {
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc';
  } else {
    sortColumn.value = column;
    sortOrder.value = 'desc';
  }
}
</script>