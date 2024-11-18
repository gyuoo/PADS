<template>
  <div class="bg-white rounded-2xl shadow-lg p-6 w-full h-full">
    <div class="text-3xl font-semibold text-gray-700 px-2 flex items-center justify-between mb-1">
      <div>이상 상품 현황</div>
      <Badge class="bg-blue-100 text-blue-800 text-lg">총 {{ totalElements }} 건</Badge>
    </div>
    <div class="text-sm text-gray-500 mb-5 px-2">{{ today }}</div>
    <div v-if="isLoading" class="flex justify-center items-center h-[500px]">
    <svg class="animate-spin h-8 w-8 text-blue-600" xmlns="http://www.w3.org/2000/svg" fill="none"
      viewBox="0 0 24 24">
      <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
      <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z"></path>
    </svg>
  </div>
    <div v-else class="overflow-x-auto h-6/7">
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
          <tr v-for="product in sortedProducts" :key="product.prdId" class="hover:bg-gray-50 cursor-pointer"
          @click="goToDetail(product.prdId)">
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
    <div class="flex justify-center items-center mt-2 space-x-5">
  <button 
    class="px-5 py-1 rounded-md bg-gray-100 hover:bg-gray-200 text-gray-700 disabled:opacity-50"
    @click="changePage(currentPage - 1)"
    :disabled="currentPage === 1"
  >
    이전
  </button>
  <span class="text-sm text-gray-700">페이지 {{ currentPage }} / {{ totalPages }}</span>
  <button 
    class="px-4 py-2 rounded-md bg-gray-100 hover:bg-gray-200 text-gray-700 disabled:opacity-50"
    @click="changePage(currentPage + 1)"
    :disabled="currentPage === totalPages"
  >
    다음
  </button>
</div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router'
import { getAnomalyProducts } from '../../api/dashboard';
import { Badge } from '../../components/ui/badge';

interface AnomalyProduct {
  prdId: number;
  viewName: string;
  createdAt: Date;
  anomalyCodes: string[];
  totalScore: number;
}

const router = useRouter()
const anormalProducts = ref<AnomalyProduct[]>([]);
const today = ref<string>('');
const sortColumn = ref<string>('viewName');
const sortOrder = ref<'asc' | 'desc'>('asc');
const currentPage = ref<number>(1);
const totalPages = ref<number>(0);
const totalElements = ref<number>(0);
const isLoading = ref<boolean>(false);

const goToDetail = (prdId: number) => {
  router.push({ name: 'ProductDetail', params: { id: prdId } })
}

const columns = [
  { field: 'viewName', label: '상품명', class: 'w-40' },
  { field: 'anomalyCodes', label: '이상치', class: 'w-40' },
  { field: 'createdAt', label: '업데이트 시간', class: 'w-36' },
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
    isLoading.value = true;
    const response = await getAnomalyProducts(null, [], 70, currentPage.value - 1, 8);
    anormalProducts.value = response.content;
    totalPages.value = response.totalPages;
    totalElements.value = response.totalElements;
    console.log(totalElements.value);
  } catch (error) {
    console.error("이상 상품 데이터를 불러오는 중 오류가 발생했습니다.", error);
  } finally {
    isLoading.value = false;
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
  if (page > 0 && page <= totalPages.value) {
    currentPage.value = page;
    fetchAnomalyProducts(); 
  }
}
</script>
