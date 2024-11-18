<template>
    <div class="bg-white rounded-3xl shadow-lg p-10 w-full h-full space-y-8">
        <div class="text-2xl font-bold text-gray-700 mb-2">📑 이상치 분석 리포트</div>
        <div class="relative">
            <div class="text-gray-700 mb-2 text-left">이상치 총 스코어</div>
            <div class="w-full bg-gray-200 rounded-full h-5 overflow-hidden">
                <div :style="{ width: productData.totalScore + '%', backgroundColor: scoreColor }"
                    class="h-full flex items-center justify-center text-white text-xs font-medium transition-all duration-500 ease-in-out">
                    {{ productData.totalScore }}%
                </div>
            </div>
        </div>
        <div class="text-gray-700 mb-2 text-left">이상치 세부 스코어</div>
        <div class="w-full mt-8 flex justify-center">
            <canvas id="radarChart" style="max-width: 400px; max-height: 400px;"></canvas>
        </div>
        <div class="w-full mt-8">
            <table class="w-full text-left text-sm border-collapse">
                <thead>
                    <tr class="bg-gray-100 text-center">
                        <th class="px-4 py-2">이상</th>
                        <th class="px-4 py-2">세부</th>
                        <th class="px-4 py-2">점수</th>
                        <th class="px-4 py-2">메시지</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(anomaly, index) in filteredAnomalies" :key="index" class="hover:bg-gray-100">
                        <td class="px-4 py-2 text-gray-700 font-medium w-24">
                            {{ getCategoryName(anomaly.code) }}
                        </td>
                        <td class="px-4 py-2 text-gray-700 font-medium w-20">
                            {{ getSubCategoryText(anomaly.code, anomaly.subCode) }}
                        </td>
                        <!-- 점수 색상 동적으로 변경 -->
                        <td :class="anomaly.score > 0 ? 'text-red-500' : 'text-gray-700'"
                            class="px-4 py-2 font-medium w-20 text-center">{{ anomaly.score }} %</td>
                        <td class="px-4 py-2 text-gray-700">
                            {{ anomaly.message }}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'; // 라우트 훅
import { Chart, RadarController, RadialLinearScale, PointElement, LineElement, Filler, Tooltip } from 'chart.js'
import { getAnomalyProductDetail } from '../../api/product'

Chart.register(RadarController, RadialLinearScale, PointElement, LineElement, Filler, Tooltip)

type AnomalyDetail = {
    code: string;
    subCode: string;
    score: number;
    message: string | null;
};

const route = useRoute(); 

const productData = ref({
    anomalyLogDetails: [] as AnomalyDetail[],
    totalScore: 0
});

const fetchProductData = async (prdId: number) => {
    try {
        productData.value = await getAnomalyProductDetail(prdId);
    } catch (error) {
        console.error("상품 데이터를 불러오는 데 실패했습니다:", error);
    }
};

const scoreColor = computed(() => {
    if (productData.value.totalScore <= 30) return '#4CAF50';
    if (productData.value.totalScore <= 60) return '#FFA500';
    return '#FF0000';
});

const radarData = computed(() => {
    const categories = ['A', 'B', 'C', 'D', 'E'];
    const scores = categories.map(category => {
        const anomaly = productData.value.anomalyLogDetails.find(
            log => log.code === category && log.subCode === '000'
        );
        return anomaly ? anomaly.score : 0;
    });
    return { categories, scores };
});

const filteredAnomalies = computed(() => {
    return productData.value.anomalyLogDetails.filter(log => log.code !== 't' && log.subCode !== '000');
});

const getCategoryName = (code: string): string => {
    const categoryMap: Record<string, string> = {
        'A': '카테고리',
        'B': '할인',
        'C': '이미지',
        'D': '가격',
        'E': '리뷰'
    };

    return categoryMap[code] || '알 수 없음';
};

const getSubCategoryText = (code: string, subCode: string): string => {
    if (code === 'A') {
        const subCategoryMapping: Record<'001' | '002' | '003' | '004', string> = {
            '001': '대분류',
            '002': '중분류',
            '003': '소분류',
            '004': '클래스'
        };
        return subCategoryMapping[subCode as '001' | '002' | '003' | '004'] || subCode;
    }
    return '-'; 
};

const renderRadarChart = () => {
    const canvas = document.getElementById('radarChart') as HTMLCanvasElement;
    const ctx = canvas?.getContext('2d');

    if (ctx) {
        new Chart(ctx, {
            type: 'radar',
            data: {
                labels: radarData.value.categories.map(category => getCategoryName(category)),
                datasets: [
                    {
                        label: '이상치 점수',
                        data: radarData.value.scores,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        pointBackgroundColor: 'rgba(255, 99, 132, 1)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 2,
                    },
                ],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                aspectRatio: 1,
                scales: {
                    r: {
                        angleLines: { display: true },
                        suggestedMin: 0,
                        suggestedMax: 100,
                    },
                },
            },
        });
    }
};

onMounted(async () => {
    const prdId = Number(route.params.id);
    await fetchProductData(prdId); 
    renderRadarChart(); 
});
</script>

<style scoped></style>
