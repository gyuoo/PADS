<template>
  <div class="bg-white rounded-lg shadow-lg p-6 w-full h-full">
    <div class="text-3xl font-semibold text-gray-700 px-2 flex items-center justify-between mb-1">
      <div>
        이상 상품 현황</div>
      <Badge class="bg-blue-100 text-blue-800 text-base">총 {{ anormalProducts.length }}건</Badge>
    </div>
    <div class="text-sm text-gray-500 mb-5 px-2">
      {{ today }}</div>
    <div class="overflow-x-auto">
      <table class="min-w-full rounded-lg">
        <thead>
          <tr>
            <th v-for="column in columns" :key="column.field" @click="sortBy(column.field)" :class="column.class"
              class="px-4 pb-4 text-left text-sm font-semibold text-gray-600 cursor-pointer whitespace-nowrap">
              {{ column.label }}
              <span>
                <img :src="getSortIcon(column.field)" alt="정렬 아이콘" class="inline-block w-4 h-4 ml-3" />
              </span>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in sortedProducts" :key="product.id" class="hover:bg-gray-50">
            <td class="px-4 py-2 text-sm text-gray-700">
              {{ product.name }}
            </td>
            <td class="px-4 py-2 text-sm text-gray-700 whitespace-nowrap">
              <div class="flex space-x-2 items-center">
                <template v-if="product.anomaly.length > 3">
                  <Badge v-for="(anomaly, index) in product.anomaly.slice(0, 3)" :key="index" class="mt-1">
                    {{ anomaly }}
                  </Badge>
                  <span>+{{ product.anomaly.length - 3 }}</span>
                </template>
                <template v-else>
                  <Badge v-for="(anomaly, index) in product.anomaly" :key="index">
                    {{ anomaly }}
                  </Badge>
                </template>
              </div>
            </td>
            <td class="px-4 py-2 text-sm text-gray-700">
              {{ product.regDate }}
            </td>
            <td class="px-4 py-2 text-sm text-gray-700">
              <div class="flex items-center">
                <span class="w-1/3 text-sm font-semibold text-gray-700">{{ product.score.toFixed(1)
                  }}</span>
                <div class="w-2/3 bg-gray-200 rounded-full h-3">
                  <div class="h-3 rounded-full" :class="{
                    'bg-orange-600': product.score >= 50 && product.score < 70,
                    'bg-red-600': product.score >= 70 && product.score < 90,
                    'bg-red-700': product.score >= 90
                  }" :style="{ width: product.score + '%' }"></div>
                </div>
              </div>
            </td>

          </tr>
        </tbody>
      </table>
    </div>
    <div class="mt-4 overflow-x-auto">
      <Pagination v-slot="{ page }" :total="100" :sibling-count="1" show-edges :default-page="2">
        <PaginationList v-slot="{ items }" class="flex items-center gap-1 justify-center whitespace-nowrap">
          <PaginationFirst />
          <PaginationPrev />
          <template v-for="(item, index) in items">
            <PaginationListItem v-if="item.type === 'page'" :key="index" :value="item.value" as-child>
              <Button class="w-10 h-10 p-0" :variant="item.value === page ? 'default' : 'outline'">
                {{ item.value }}
              </Button>
            </PaginationListItem>
            <PaginationEllipsis v-else :key="item.type" :index="index" />
          </template>
          <PaginationNext />
          <PaginationLast />
        </PaginationList>
      </Pagination>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import anormalyProductsData from '@/data/anormalyProducts.json';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Pagination, PaginationEllipsis, PaginationFirst, PaginationLast, PaginationList, PaginationListItem, PaginationNext, PaginationPrev, } from '@/components/ui/pagination';

const today = ref<string>('');
const sortColumn = ref<string>('name');
const sortOrder = ref<'asc' | 'desc'>('asc');
const anormalProducts = ref(anormalyProductsData);

const columns = [
  { field: 'name', label: '상품명', class: 'w-40' },
  { field: 'anomaly', label: '이상치', class: 'w-40' },
  { field: 'regDate', label: '최근 검사일', class: 'w-36' },
  { field: 'score', label: '스코어', class: 'w-32' },
];

onMounted(() => {
  const date = new Date();
  today.value = date.toLocaleString('en-US', {
    timeZone: 'Asia/Seoul',
    year: 'numeric',
    month: 'short',
    day: '2-digit',
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
