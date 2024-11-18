<script setup lang="ts">
import { ref, provide } from 'vue';

const isLoggedIn = ref(false);
const setLoggedIn = (value: boolean) => {
  isLoggedIn.value = value;
};

provide('isLoggedIn', isLoggedIn);
provide('setLoggedIn', setLoggedIn);
</script>

<template>
  <router-view />
</template>
