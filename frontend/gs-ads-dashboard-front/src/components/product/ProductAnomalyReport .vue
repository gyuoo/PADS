<template>
    <div class="bg-white rounded-3xl shadow-lg p-10 w-full h-full space-y-8">
        <div class="text-2xl font-bold text-gray-700 mb-2">📑 이상치 분석 리포트</div>
        <div class="relative">
            <div class="text-gray-700 mb-2 text-left">이상치 총 스코어</div>
            <div class="w-full bg-gray-200 rounded-full h-5 overflow-hidden">
                <div :style="{ width: score + '%', backgroundColor: scoreColor }"
                    class="h-full flex items-center justify-center text-white text-xs font-medium transition-all duration-500 ease-in-out">
                    {{ score }}%
                </div>
            </div>
        </div>
        <div class="text-gray-700 mb-2 text-left">이상치 세부 스코어</div>
        <div class="w-full mt-8 flex justify-center">
            <canvas id="radarChart" style="max-width: 400px; max-height: 400px;"></canvas>
        </div>

        <div class="w-full mt-8">
            <table class="w-full text-left text-sm border-collapse">
                <tbody>
                    <tr v-for="(categoryScore, index) in categoryScores" :key="index" class="hover:bg-gray-100">
                        <td class="px-4 py-3 text-gray-700 font-medium w-20"> {{ categoryScore.category }}</td>
                        <td :class="categoryScore.score >= 80 ? 'text-red-500' : 'text-gray-700'"
                            class="px-4 py-3 font-medium w-8">{{ categoryScore.score }}</td>
                        <td v-if="categoryScore.category === '카테고리 이상'" class="text-gray-700 w-40">
                            추천하는 카테고리는 <span class="text-green-600">신선/가공식품</span> 입니다
                        </td>
                        <td v-else class="text-gray-700 text-center w-40">
                            -
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { Chart, RadarController, RadialLinearScale, PointElement, LineElement, Filler, Tooltip } from 'chart.js'

Chart.register(RadarController, RadialLinearScale, PointElement, LineElement, Filler, Tooltip)

const score = ref(75)

const categoryScores = ref([
    { category: '할인이상', score: 20 },
    { category: '이미지이상', score: 10 },
    { category: '가격이상', score: 15 },
    { category: '리뷰이상', score: 25 },
    { category: '카테고리 이상', score: 95 },
])

const scoreColor = computed(() => {
    if (score.value <= 30) return '#4CAF50'
    if (score.value <= 60) return '#FFA500'
    return '#FF0000'
})

onMounted(() => {
    const canvas = document.getElementById('radarChart') as HTMLCanvasElement;
    const ctx = canvas?.getContext('2d');

    if (ctx) {
        new Chart(ctx, {
            type: 'radar',
            data: {
                labels: categoryScores.value.map(item => item.category),
                datasets: [{
                    label: '이상치 점수',
                    data: categoryScores.value.map(item => item.score),
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    pointBackgroundColor: 'rgba(255, 99, 132, 1)',
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                aspectRatio: 1,
                scales: {
                    r: {
                        angleLines: { display: true },
                        suggestedMin: 0,
                        suggestedMax: 100
                    }
                }
            }
        });
    } 
});
</script>

<style scoped>
</style>
