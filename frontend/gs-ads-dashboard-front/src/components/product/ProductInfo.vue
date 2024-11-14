<template>
    <div class="bg-white rounded-3xl shadow-lg p-10 w-full h-full text-center space-y-3">
        <img src="https://velog.velcdn.com/images/gangintheremark/post/79b7b073-1f97-4492-bb79-75a339210944/image.png"
            alt="상품 이미지" class="w-52 h-52 rounded-lg mx-auto">
        
        <div class="text-xl">
            {{ viewName }}
        </div>
        <div class="text-3xl text-right font-semibold">
            <div class="text-sm text-right text-gray-500 font-normal">
                {{ cate1Nm }} > {{ cate2Nm }} > {{ cate3Nm }}
            </div>
            {{ price }} 원
        </div>

        <table class="w-full mt-6 border border-gray-200 text-left text-sm">
            <thead>
                <tr class="bg-gray-100">
                    <th class="px-4 py-2 border-b">항목</th>
                    <th class="px-4 py-2 border-b">내용</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="px-4 py-2 border-b">대분류</td>
                    <td class="px-4 py-2 border-b">{{ cate1Nm }}</td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border-b">중분류</td>
                    <td class="px-4 py-2 border-b">{{ cate2Nm }}</td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border-b">소분류</td>
                    <td class="px-4 py-2 border-b">{{ cate3Nm }}</td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border-b">할인가격</td>
                    <td class="px-4 py-2 border-b">{{ discPrice }} 원</td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border-b">상품구매수</td>
                    <td class="px-4 py-2 border-b">{{ buyCount }}</td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border-b">상품리뷰 스코어</td>
                    <td class="px-4 py-2 border-b">{{ reviewScore }}</td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border-b">상품리뷰수</td>
                    <td class="px-4 py-2 border-b">{{ reviewCount }}</td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border-b">브랜드명</td>
                    <td class="px-4 py-2 border-b">{{ brdName }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const viewName = ref("HACCP인증 맥반석 구운계란 군계란 대란 30구 1판")
const cate1Nm = ref("신선/가공식품")
const cate2Nm = ref("축산")
const cate3Nm = ref("계란")
const price = ref(12500)
const discPrice = ref(12500)
const buyCount = ref(0)
const reviewScore = ref(0)
const reviewCount = ref(0)
const brdName = ref("계란 최고")

</script>
