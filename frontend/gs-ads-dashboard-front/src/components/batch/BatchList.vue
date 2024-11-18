<template>
  <div class="p-4">
    <!-- 필터 버튼 -->
    <div class="flex space-x-4 mb-4">
      <button
        v-for="filter in filters"
        :key="filter.value"
        @click="selectedStatus = filter.value"
        :class="[
          'px-4 py-2 rounded-lg transition',
          selectedStatus === filter.value
            ? 'bg-blue-500 text-white hover:bg-blue-600'
            : 'bg-gray-300 text-gray-700 hover:bg-gray-400',
        ]"
      >
        {{ filter.label }}
      </button>
    </div>

    <!-- 테이블 -->
    <table class="w-full bg-white">
      <thead>
        <tr class="bg-white border-b border-gray-300">
          <th class="px-4 py-2 text-center bg-white">ID</th>
          <th class="px-4 py-2 text-center bg-white">상품명</th>
          <th class="px-4 py-2 text-center bg-white">시작 시간</th>
          <th class="px-4 py-2 text-center bg-white">상태</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(batch, index) in filteredBatches" :key="index">
          <td class="px-4 py-2 text-center bg-white">{{ batch.productId }}</td>
          <td class="px-4 py-2 text-center bg-white">{{ batch.productName }}</td>
          <td class="px-4 py-2 text-center bg-white">{{ batch.startDateTime }}</td>
          <td class="px-4 py-2 text-center bg-white">
            <span
              class="px-2 py-1 rounded-full text-white"
              :class="{
                'bg-gray-500': batch.status === 'SCHEDULED' || batch.status === 'WAIT',
                'bg-orange-500': batch.status === 'PROGRESS',
                'bg-green-500': batch.status === 'COMPLETED',
                'bg-red-500': batch.status === 'FAILED',
              }"
            >
              {{ batch.description }}
            </span>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="mt-4 flex justify-center space-x-4">
      <button
        class="px-4 py-2 bg-gray-300 rounded-lg transition hover:bg-gray-400 disabled:opacity-50"
        :disabled="currentPage === 0"
        @click="loadPage(currentPage - 1)"
      >
        이전
      </button>
      <span>페이지 {{ currentPage + 1 }} / {{ totalPages }}</span>
      <button
        class="px-4 py-2 bg-gray-300 rounded-lg transition hover:bg-gray-400 disabled:opacity-50"
        :disabled="currentPage === totalPages - 1"
        @click="loadPage(currentPage + 1)"
      >
        다음
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import axios from "axios";

const batches = ref([]);
const currentPage = ref(0);
const totalPages = ref(0);
const selectedStatus = ref("");

const filters = [
  { label: "전체", value: "" },
  { label: "예정", value: "SCHEDULED" },
  { label: "대기", value: "WAIT" },
  { label: "진행중", value: "PROGRESS" },
  { label: "완료", value: "COMPLETED" },
  { label: "실패", value: "FAILED" },
];

const filteredBatches = computed(() => {
  if (!selectedStatus.value) return batches.value;
  return batches.value.filter((batch: any) => batch.status === selectedStatus.value);
});

const loadPage = async (page: number) => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/jobs?page=${page}&size=10`, {withCredentials: true});
    const data = response.data;

    batches.value = data.content.map((batch: any) => ({
      productId: batch.productId,
      productName: batch.productName,
      startDateTime: new Date(batch.startDateTime).toLocaleString(),
      status: batch.status,
      description: getStatusDescription(batch.status),
    }));

    currentPage.value = data.number;
    totalPages.value = data.totalPages;
  } catch (error) {
    console.error("Error fetching batch data:", error);
  }
};

const getStatusDescription = (status: string): string => {
  const descriptions: Record<string, string> = {
    SCHEDULED: "예정",
    WAIT: "대기",
    PROGRESS: "진행중",
    COMPLETED: "완료",
    FAILED: "실패",
  };
  return descriptions[status] || "알 수 없음";
};

onMounted(() => {
  loadPage(0);
});
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
}
th,
td {
  text-align: center;
  padding: 8px;
}
thead tr {
  border-bottom: 1px solid #d1d5db;
}
</style>

