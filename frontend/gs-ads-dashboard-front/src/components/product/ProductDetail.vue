<template>
    <div class="flex flex-col md:flex-row h-full">
        <div class="flex flex-col gap-3 w-full md:w-1/2 h-full p-2">
            <ProductInfo :productData="productData" />
        </div>
        <div class="flex flex-col gap-3 w-full md:w-1/2 h-full p-2">
            <ProductAnomalyReport :productData="productData" />
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router'; // 라우트 훅
import ProductInfo from './ProductInfo.vue';
import ProductAnomalyReport from './ProductAnomalyReport .vue';
import { getAnomalyProductDetail } from '@/api/product'; // API 호출 함수

const route = useRoute(); // 라우트 객체 가져오기
const productData = ref({
    prdId: null,
    viewName: "",
    cate1Nm: "",
    cate2Nm: "",
    cate3Nm: "",
    className: "",
    price: 0,
    discPrice: 0,
    buyCount: 0,
    reviewScore: 0,
    reviewCount: 0,
    brdName: "",
    totalScore: 0,
    anomalyLogDetails: [],
});


const fetchProductData = async () => {
    const prdId = route.params.id; 
    try {
        const data = await getAnomalyProductDetail(prdId); 
        productData.value = data; 
    } catch (error) {
        console.error("상품 데이터를 불러오는 데 실패했습니다:", error);
    }
};

onMounted(() => {
    fetchProductData();
});
</script>
