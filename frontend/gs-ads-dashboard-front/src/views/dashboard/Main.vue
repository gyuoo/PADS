<template>
  <Layout :mainTitle="'대시보드'">
    <template #content>
      <DashboardMain></DashboardMain>
    </template>
  </Layout>
</template>

<script setup lang="ts">
import DashboardMain from '@/components/dashboard/DashboardMain.vue'
import Layout from '@/components/layout/Layout.vue'
</script>

<style scoped></style>
