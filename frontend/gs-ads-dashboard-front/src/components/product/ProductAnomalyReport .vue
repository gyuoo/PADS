<template>
    <div class="bg-white rounded-3xl shadow-lg p-10 w-full h-full text-center space-y-6">
        <div class="text-3xl font-semibold text-gray-700">이상치 리포트</div>
        <div class="relative pt-1 mt-6">
            <div class="flex mb-2 justify-end">
                <div>
                    <span class="text-3xl font-semibold inline-block" :style="{ color: scoreColor }">
                        {{ score }}
                    </span>
                    <span class="text-gray-800"> 점</span>
                </div>
            </div>
            <div class="overflow-hidden h-4 mb-4 text-xs flex rounded bg-gray-200">
                <div :style="{ width: score + '%', backgroundColor: scoreColor }"
                    class="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center transition-all duration-500 ease-in-out">
                </div>
            </div>
        </div>
        <table class="w-full mt-6 text-left text-sm">
            <thead>
                <tr>
                    <th class="px-4 py-2 border-b text-gray-500">이상치 카테고리</th>
                    <th class="px-4 py-2 border-b text-gray-500">스코어</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(categoryScore, index) in categoryScores" :key="index">
                    <td class="px-4 py-2">{{ categoryScore.category }}</td>
                    <td class="px-4 py-2">{{ categoryScore.score }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

const score = ref(85)
const categoryScores = ref([
    { category: '가격이상', score: 80 },
    { category: '리뷰이상', score: 70 },
    { category: '카테고리이상', score: 90 },
    { category: '아무이상', score: 85 },
])

const scoreColor = computed(() => {
    if (score.value <= 30) return '#4CAF50'
    if (score.value <= 60) return '#FFA500'
    return '#FF0000'
})

</script>
