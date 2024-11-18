<template>
    <div>
      <Layout :mainTitle="title">
        <template #content>
          <BatchList></BatchList>
        </template>
      </Layout>
    </div>
  </template>
  
  <script setup lang="ts">
  import BatchList from '@/components/batch/BatchList.vue'
  import Layout from '@/components/layout/Layout.vue'
  
  const title = '배치 현황'
  </script>
  
  <style scoped></style>
  