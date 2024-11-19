import { createApp } from 'vue'
import App from './App.vue'
import './styles/global.css'
import router from './router'
import axios from 'axios';
import { createPinia } from 'pinia';

const app = createApp(App)
const pinia = createPinia();
app.use(pinia);
app.use(router) // 라우터 사용 설정
app.mount('#app')

axios.defaults.withCredentials = true;

axios.interceptors.request.use(
    function (config) {
      config.withCredentials = true;
      return config;
    },
    function (error) {
      return Promise.reject(error);
    },
    );