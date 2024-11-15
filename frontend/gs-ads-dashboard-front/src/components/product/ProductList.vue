<template>
  <div class="bg-white rounded-2xl shadow-lg p-6 w-full h-full">
    <div class="mb-10 flex space-x-4">
      <input v-model="filters.viewName" type="text" placeholder="상품명으로 검색"
        class="border rounded-lg p-2 w-1/4 text-sm focus:outline-none focus:ring-2 focus:ring-blue-200" />
      <select v-model="filters.code"
        class="border rounded-lg p-2 w-1/4 text-sm focus:outline-none focus:ring-2 focus:ring-blue-200">
        <option value="">이상치 코드 선택</option>
        <option v-for="(desc, code) in anomalyDescriptions" :key="code" :value="code">
          {{ desc }}
        </option>
      </select>
      <input v-model.number="filters.totalScore" type="number" placeholder="최소 스코어"
        class="border rounded-lg p-2 w-1/4 text-sm focus:outline-none focus:ring-2 focus:ring-blue-200" />
      <button @click="applyFilters"
        class="bg-blue-500 text-white px-4 py-2 rounded-lg text-sm hover:bg-blue-600 focus:outline-none">
        필터 적용
      </button>
      <button @click="resetFilters"
        class="bg-gray-300 text-gray-700 px-4 py-2 rounded-lg text-sm hover:bg-gray-400 focus:outline-none">
        필터 초기화
      </button>
    </div>

    <!-- 로딩 스피너 -->
    <div v-if="isLoading" class="flex justify-center items-center h-[550px]">
      <svg class="animate-spin h-8 w-8 text-blue-600" xmlns="http://www.w3.org/2000/svg" fill="none"
        viewBox="0 0 24 24">
        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z"></path>
      </svg>
    </div>

    <!-- 데이터 테이블 -->
    <div v-else class="overflow-x-auto h-[550px] overflow-y-auto">
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
                    'bg-green-600': product.totalScore >= 0 && product.totalScore < 40,
                    'bg-orange-600': product.totalScore >= 40 && product.totalScore < 60,
                    'bg-red-700': product.totalScore >= 60
                  }" :style="{ width: product.totalScore + '%' }"></div>
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { getAnomalyProducts } from '@/api/dashboard';
import { Badge } from '@/components/ui/badge';
import { useRouter } from 'vue-router'

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
const isLoading = ref<boolean>(false);
const filters = ref({
  viewName: '',
  code: '',
  totalScore: null
});

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

const goToDetail = (prdId: number) => {
  router.push({ name: 'ProductDetail', params: { id: prdId } })
}

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
    anormalProducts.value = await getAnomalyProducts(
      filters.value.viewName || null,
      filters.value.code || null,
      filters.value.totalScore || null,
    );
  } catch (error) {
    console.error("이상 상품 데이터를 불러오는 중 오류가 발생했습니다.", error);
  } finally {
    isLoading.value = false;
  }
}

function applyFilters() {
  fetchAnomalyProducts();
}

function resetFilters() {
  filters.value.viewName = '';
  filters.value.code = '';
  filters.value.totalScore = null;
  applyFilters();
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
