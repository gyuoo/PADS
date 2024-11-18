<template>
  <div class="bg-white rounded-2xl shadow-lg p-6 w-full h-full">
    <div class="text-3xl font-semibold text-gray-700 px-2 flex items-center justify-between mb-1">
      <div class="mb-1">
        최근 검사 내역
        <div class="text-sm text-gray-500 font-normal mt-1">최근 수행된 검사 작업 내역과 상태</div>
      </div>
    </div>
    <div class="overflow-x-auto">
      <table class="min-w-full rounded-lg">
        <thead>
          <tr>
            <th v-for="column in columns" :key="column.field" :class="column.class"
              class="px-4 pb-4 text-center text-sm font-semibold text-gray-600 cursor-pointer whitespace-nowrap">
              {{ column.label }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="job in jobs" :key="job.productId" class="hover:bg-gray-50 text-center">
            <td class="px-4 py-2 text-sm text-gray-700 items-center">
              {{ job.productId }}
            </td>
            <td class="px-4 py-2 text-sm text-gray-700 whitespace-nowrap">
              {{ job.startDateTime }}
            </td>
            <td class="px-4 py-2 text-sm text-gray-700 flex items-center whitespace-nowrap">
              <span :class="[getStatusClass(job.status)]" class="w-3 h-3 rounded-full mr-2"></span>
              {{ job.status }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';

interface Job {
  productId: number;
  startDateTime: string;
  status: string;
}

const jobs = ref<Job[]>([]);

const columns = [
  { field: 'productId', label: '상품 ID', class: 'w-1/5' },
  { field: 'startDateTime', label: '시작 시간', class: 'w-1/5' },
  { field: 'status', label: '상태', class: 'w-1/5' },
];

const mapStatusToKorean = (status: string): string => {
  const statusMap: Record<string, string> = {
    SCHEDULED: "예정",
    WAIT: "대기",
    PROGRESS: "진행중",
    COMPLETED: "완료",
    FAILED: "실패",
  };
  return statusMap[status] || "알 수 없음";
};

const getStatusClass = (status: string): string => {
  const classMap: Record<string, string> = {
    "진행중": "bg-orange-500",
    "완료": "bg-green-500",
    "실패": "bg-red-500",
  };
  return classMap[status] || "bg-gray-500";
};

const formatDate = (dateString: string): string => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};

const fetchJobs = async (page = 0, size = 7) => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/jobs', 
    {
      params: { page, size },
    },);

    jobs.value = response.data.content.map((job: any) => ({
      productId: job.productId,
      startDateTime: formatDate(job.startDateTime),
      status: mapStatusToKorean(job.status),
    }));
  } catch (error) {
    console.error('Failed to fetch jobs:', error);
  }
};

onMounted(() => fetchJobs());
</script>