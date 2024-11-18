<template>
  <div class="login-container">
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <input
          type="email"
          v-model="email"
          id="email"
          placeholder="이메일"
          required
        />
      </div>
      <div class="form-group">
        <input
          type="password"
          v-model="password"
          id="password"
          placeholder="비밀번호"
          required
        />
      </div>
      <button type="submit" :disabled="loading">로그인</button>
      <transition name="slide-fade">
        <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      </transition>
      <p>
        아직 계정이 없으신가요?
        <router-link to="/register" class="register-link">회원가입</router-link>
      </p>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default defineComponent({
  name: 'LoginForm',
  setup() {
    const email = ref('')
    const password = ref('')
    const errorMessage = ref('')
    const loading = ref(false)
    const router = useRouter()

    const handleSubmit = async () => {
      loading.value = true
      errorMessage.value = '' // 초기화

      try {
        const response = await axios.post(
          `${import.meta.env.VITE_API_BASE_URL}/members/login`,
          {
            email: email.value,
            password: password.value,
          },
        )

        if (response.status === 200) {
          router.push('/') // 로그인 성공 시 리다이렉트
        }
      } catch (error: any) {
        if (error.response && error.response.status === 401) {
          errorMessage.value = '이메일 또는 비밀번호가 잘못되었습니다.'
        } else {
          errorMessage.value =
            '로그인 중 문제가 발생했습니다. 다시 시도해 주세요.'
        }
        console.error('Login error:', error)
      } finally {
        loading.value = false
      }
    }

    return {
      email,
      password,
      errorMessage,
      loading,
      handleSubmit,
    }
  },
})
</script>

<style scoped>
.login-container {
  margin: auto;
  margin-top: 200px;
  max-width: 350px;
  padding: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1.2px solid #859bff;
  border-radius: 4px;
  font-size: 16px;
}

.register-link {
  color: #007bff;
  text-decoration: none;
}

.register-link:hover {
  text-decoration: underline;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #0056b3;
}

button:disabled {
  cursor: not-allowed;
  opacity: 0.7;
}

p {
  text-align: center;
  margin-top: 15px;
}

.error-message {
  color: red;
  font-size: 0.9rem;
  margin-top: 5px;
  text-align: center;
}
</style>
