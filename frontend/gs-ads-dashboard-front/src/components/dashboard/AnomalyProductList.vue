<template>
  <div class="bg-white rounded-2xl shadow-lg p-6 w-full h-full">
    <div class="text-3xl font-semibold text-gray-700 px-2 flex items-center justify-between mb-1">
      <div>이상 상품 현황</div>
      <Badge class="bg-blue-100 text-blue-800 text-base">총 {{ anormalProducts.length }} 건</Badge>
    </div>
    <div class="text-sm text-gray-500 mb-5 px-2">{{ today }}</div>
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
          <tr v-for="product in sortedProducts" :key="product.prdId" class="hover:bg-gray-50">
            <td class="px-4 py-2 text-sm text-gray-700">{{ product.viewName }}</td>
            <td class="px-4 py-2 text-sm text-gray-700 whitespace-nowrap">
              <div class="flex space-x-2 items-center">
                <template v-if="product.anomalyCodes.length > 3">
                  <Badge v-for="(anomaly, index) in product.anomalyCodes.slice(0, 3)" :key="index" class="mt-1">
                    {{ getAnomalyDescription(anomaly) }}
                  </Badge>
                  <span class="text-red-700">+{{ product.anomalyCodes.length - 3 }}</span>
                </template>
                <template v-else>
                  <Badge v-for="(anomaly, index) in product.anomalyCodes" :key="index">
                    {{ getAnomalyDescription(anomaly) }}
                  </Badge>
                </template>
              </div>
            </td>
            <td class="px-4 py-2 text-sm text-gray-700">
              {{ new Date(product.createdAt).toLocaleDateString() }}
              {{ new Date(product.createdAt).toLocaleTimeString([], {
                hour: '2-digit', minute: '2-digit', hour12: false
              }) }}
            </td>
            <td class="px-4 py-2 text-sm text-gray-700">
              <div class="flex items-center">
                <span class="w-12 text-sm font-semibold text-gray-700">{{ product.totalScore }} 점</span>
                <div class="w-2/3 bg-gray-200 rounded-full h-3">
                  <div class="h-3 rounded-full" :class="{
                    'bg-orange-600': product.totalScore >= 50 && product.totalScore < 70,
                    'bg-red-600': product.totalScore >= 70 && product.totalScore < 90,
                    'bg-red-700': product.totalScore >= 90
                  }" :style="{ width: product.totalScore + '%' }"></div>
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="mt-4 overflow-x-auto">
      <Pagination v-slot="{ page }" :total="totalItems" :page-size="itemsPerPage" @page-change="changePage">
        <PaginationList v-slot="{ items }" class="flex items-center gap-1 justify-center whitespace-nowrap">
          <PaginationFirst />
          <PaginationPrev />
          <template v-for="(item, index) in items">
            <PaginationListItem v-if="item.type === 'page'" :key="index" :value="item.value" as-child>
              <Button class="w-10 h-10 p-0" :variant="item.value === currentPage ? 'default' : 'outline'">
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
import { getAnomalyProducts } from '@/api/dashboard';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Pagination, PaginationEllipsis, PaginationFirst, PaginationLast, PaginationList, PaginationListItem, PaginationNext, PaginationPrev } from '@/components/ui/pagination';

interface AnomalyProduct {
  prdId: number;
  viewName: string;
  createdAt: Date;
  anomalyCodes: string[];
  totalScore: number;
}

const anormalProducts = ref<AnomalyProduct[]>([]);
const totalItems = ref<number>(0);
const today = ref<string>('');
const sortColumn = ref<string>('viewName');
const sortOrder = ref<'asc' | 'desc'>('asc');
const currentPage = ref<number>(1);
const itemsPerPage = 10;

const columns = [
  { field: 'viewName', label: '상품명', class: 'w-40' },
  { field: 'anomalyCodes', label: '이상치', class: 'w-40' },
  { field: 'updatedAt', label: '업데이트 시간', class: 'w-36' },
  { field: 'totalScore', label: '스코어', class: 'w-32' },
];

const anomalyDescriptions: Record<string, string> = {
  A: '카테고리 이상',
  B: '할인 이상',
  C: '이미지 이상',
  D: '가격 이상',
  E: '리뷰 이상'
};

function getAnomalyDescription(code: string): string {
  return anomalyDescriptions[code] || code;
}

onMounted(() => {
  const date = new Date();
  today.value = date.toLocaleString('en-US', {
    timeZone: 'Asia/Seoul',
    year: 'numeric',
    month: 'short',
    day: '2-digit',
  });
  fetchAnomalyProducts();
});

async function fetchAnomalyProducts() {
  try {
    anormalProducts.value = await getAnomalyProducts(null, [], 50, currentPage.value - 1);
  } catch (error) {
    console.error("이상 상품 데이터를 불러오는 중 오류가 발생했습니다.", error);
  }
}

const sortedProducts = computed(() => {
  return [...anormalProducts.value].sort((a, b) => {
    const fieldA = a[sortColumn.value as keyof typeof a];
    const fieldB = b[sortColumn.value as keyof typeof b];
    if (fieldA < fieldB) return sortOrder.value === 'asc' ? -1 : 1;
    if (fieldA > fieldB) return sortOrder.value === 'asc' ? 1 : -1;
    return 0;
  });
});

function sortBy(column: string) {
  if (sortColumn.value === column) {
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc';
  } else {
    sortColumn.value = column;
    sortOrder.value = 'asc';
  }
  fetchAnomalyProducts();
}

function getSortIcon(column: string) {
  if (sortColumn.value === column) {
    return sortOrder.value === 'asc'
      ? 'https://velog.velcdn.com/images/gangintheremark/post/b481b151-bfee-406f-9d33-9de897efed9b/image.png'
      : 'https://velog.velcdn.com/images/gangintheremark/post/09a348f3-b576-404a-803c-dc25b31a9be0/image.png';
  }
  return 'https://velog.velcdn.com/images/gangintheremark/post/b481b151-bfee-406f-9d33-9de897efed9b/image.png';
}

function changePage(page: number) {
  currentPage.value = page;
  fetchAnomalyProducts();
}
</script>
