<template>
  <div class="bg-white rounded-lg shadow-lg p-6 w-full h-full">
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
                  <tr v-for="job in jobs" :key="job.jobId" class="hover:bg-gray-50 text-center">
                      <td class="px-4 py-2 text-sm text-gray-700 items-center">
                          {{ job.jobId }}
                      </td>
                      <td class="px-4 py-2 text-sm text-gray-700 whitespace-nowrap">
                          {{ job.startTime }}
                      </td>
                      <td class="px-4 py-2 text-sm text-gray-700 flex items-center whitespace-nowrap">
                          <span
                              :class="{
                                  'bg-orange-500': job.status === '진행중',
                                  'bg-green-500': job.status === '완료',
                                  'bg-red-500': job.status === '실패'
                              }"
                              class="w-3 h-3 rounded-full mr-2">
                          </span>
                          {{ job.status }}
                      </td>
                  </tr>
              </tbody>
          </table>
      </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import jobsData from '@/data/jobs.json';

const jobs = ref(jobsData);

const columns = [
  { field: 'jobId', label: 'Id', class: 'w-1/4' },
  { field: 'startTime', label: '시작 시간', class: 'w-1/2' },
  { field: 'status', label: '상태', class: 'w-1/4' },
];
</script>
