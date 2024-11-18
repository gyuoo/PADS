<template>
  <div
    class="bg-white rounded-3xl shadow-lg p-10 w-full h-full text-center space-y-4"
  >
    <img
      :src="imageUrl"
      alt="상품 이미지"
      class="w-full max-w-xs h-auto rounded-lg mx-auto"
    />
    <div class="text-2xl">
      {{ productData.viewName }}
    </div>
    <div class="text-right font-semibold">
      <div class="text-base text-right text-gray-500 font-normal">
        {{ productData.cate1Nm }} > {{ productData.cate2Nm }} >
        {{ productData.cate3Nm == '0' ? '' : productData.cate3Nm }}
      </div>
      <div class="text-3xl">{{ productData.price.toLocaleString() }} 원</div>
    </div>
    <table class="w-full border border-gray-200 text-left text-sm">
      <thead>
        <tr class="bg-gray-100">
          <th class="px-4 py-2 border-b">항목</th>
          <th class="px-4 py-2 border-b">내용</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="px-4 py-2 border-b">대분류</td>
          <td class="px-4 py-2 border-b">{{ productData.cate1Nm }}</td>
        </tr>
        <tr>
          <td class="px-4 py-2 border-b">중분류</td>
          <td class="px-4 py-2 border-b">{{ productData.cate2Nm }}</td>
        </tr>
        <tr>
          <td class="px-4 py-2 border-b">소분류</td>
          <td class="px-4 py-2 border-b">
            {{ productData.cate3Nm == '0' ? '-' : productData.cate3Nm }}
          </td>
        </tr>
        <tr>
          <td class="px-4 py-2 border-b">클래스</td>
          <td class="px-4 py-2 border-b">{{ productData.className }}</td>
        </tr>
        <tr>
          <td class="px-4 py-2 border-b">할인가격</td>
          <td class="px-4 py-2 border-b">
            {{ productData.discPrice.toLocaleString() }} 원
          </td>
        </tr>
        <tr>
          <td class="px-4 py-2 border-b">상품구매수</td>
          <td class="px-4 py-2 border-b">{{ productData.buyCount }}</td>
        </tr>
        <tr>
          <td class="px-4 py-2 border-b">상품리뷰 스코어</td>
          <td class="px-4 py-2 border-b">{{ productData.reviewScore }}</td>
        </tr>
        <tr>
          <td class="px-4 py-2 border-b">상품리뷰수</td>
          <td class="px-4 py-2 border-b">{{ productData.reviewCount }}</td>
        </tr>
        <tr>
          <td class="px-4 py-2 border-b">브랜드명</td>
          <td class="px-4 py-2 border-b">{{ productData.brdName }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue'
import { useRoute } from 'vue-router'

defineProps({
  productData: {
    type: Object,
    required: true,
  },
})

const route = useRoute()
const prdId = route.params.id as string

const imageUrl = computed(() => {
  if (!prdId) {
    return ''
  }
  return `http://image.gsshop.com/image/${prdId.slice(0, 2)}/${prdId.slice(2, 4)}/${prdId}_L1.jpg`
})
</script>
