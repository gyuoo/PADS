<template>
    <div>
      <Layout :mainTitle="title">
        <template #content>
          <ProductDetail></ProductDetail>
        </template>
      </Layout>
    </div>
  </template>
  
  <script setup lang="ts">
  import ProductList from '@/components/product/ProductList.vue'
  import Layout from '@/components/layout/Layout.vue'
import ProductDetail from '../../components/product/ProductDetail.vue';
  
  const title = '상품 상세 리포트'
  </script>
  
  <style scoped></style>
  