import { createApp } from 'vue'
import App from './App.vue'
import './styles/global.css'
import router from './router'

const app = createApp(App)
app.use(router) // 라우터 사용 설정
app.mount('#app')
