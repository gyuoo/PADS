<template>
  <div>
    <Layout :mainTitle="title">
      <template #content>
        <ProductList></ProductList>
      </template>
    </Layout>
  </div>
</template>

<script setup lang="ts">
import ProductList from '@/components/product/ProductList.vue'
import Layout from '@/components/layout/Layout.vue'

const title = '상품 현황'
</script>

<style scoped></style>
