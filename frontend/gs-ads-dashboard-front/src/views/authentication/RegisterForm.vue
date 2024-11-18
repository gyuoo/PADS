<template>
  <div class="register-container">
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <div class="input-group">
          <input
            type="email"
            class="email"
            v-model="email"
            id="email"
            placeholder="이메일"
            required
          />
          <button
            type="button"
            class="auth-button"
            @click="handleAuthClick"
            :disabled="loading"
          >
            인증번호 전송
          </button>
          <transition name="slide-fade">
            <p v-if="loading" class="success-message">전송 준비 중...</p>
          </transition>
          <transition name="slide-fade">
            <p v-if="emailError" class="error-message">
              이메일 형식이 잘못되었습니다.
            </p>
          </transition>
          <transition name="slide-fade">
            <p v-if="emailSent" class="success-message">
              인증번호가 전송되었습니다.
            </p>
          </transition>
          <transition name="slide-fade">
            <p v-if="emailExistsError" class="error-message">
              이미 존재하는 이메일입니다.
            </p>
          </transition>
        </div>
      </div>
      <div class="form-group">
        <input
          type="auth-code"
          class="auth-code"
          v-model="authCode"
          id="auth-code"
          placeholder="인증번호 입력"
          required
        />
        <button type="button" @click="handleVerifyAuthCode" class="auth-button">
          확인　
        </button>
        <transition name="slide-fade">
          <p v-if="verificationError" class="error-message">
            인증번호가 일치하지 않습니다.
          </p>
        </transition>
        <transition name="slide-fade">
          <p v-if="verificationSuccess" class="success-message">
            인증이 완료되었습니다.
          </p>
        </transition>
      </div>
      <div class="form-group">
        <input
          type="password"
          v-model="password"
          id="password"
          placeholder="비밀번호"
          required
          @input="validatePassword"
        />
        <transition name="slide-fade">
          <p v-if="passwordFormatError" class="error-message">
            비밀번호는 8자 이상, 알파벳과 숫자가 포함되어야 합니다.
          </p>
        </transition>
      </div>
      <div class="form-group">
        <input
          type="password"
          v-model="confirmPassword"
          id="confirmPassword"
          placeholder="비밀번호 확인"
          required
          @input="validatePasswordMatch"
        />
        <transition name="slide-fade">
          <p v-if="passwordError" class="error-message">
            비밀번호가 일치하지 않습니다.
          </p>
        </transition>
      </div>
      <div class="form-group">
        <button type="button" class="register-button" @click="handleRegister">
          회원가입
        </button>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default defineComponent({
  name: 'RegisterForm',
  setup() {
    const email = ref('')
    const authCode = ref('')
    const password = ref('')
    const confirmPassword = ref('')
    const emailError = ref(false)
    const emailExistsError = ref(false)
    const passwordError = ref(false)
    const passwordFormatError = ref(false)
    const emailSent = ref(false)
    const verificationSuccess = ref(false)
    const verificationError = ref(false)
    const loading = ref(false)
    const router = useRouter()

    const validateEmail = () => {
      const emailPattern = /^[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$/
      emailError.value = !emailPattern.test(email.value)
    }

    const validatePassword = () => {
      const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/
      passwordFormatError.value = !passwordPattern.test(password.value)
    }

    const validatePasswordMatch = () => {
      passwordError.value = password.value !== confirmPassword.value
    }

    const handleAuthClick = async () => {
      validateEmail()
      if (!emailError.value) {
        loading.value = true
        emailExistsError.value = false
        try {
          const response = await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/mails/send`,
            {
              email: email.value,
            },
          )
          emailSent.value = response.status === 200
        } catch (error: any) {
          if (error.response && error.response.status === 400) {
            emailExistsError.value = true
          } else {
            emailSent.value = false
            console.error('Failed to send email:', error)
          }
        } finally {
          loading.value = false
        }
      } else {
        emailSent.value = false
      }
    }

    const handleVerifyAuthCode = async () => {
      try {
        const response = await axios.get(
          `${import.meta.env.VITE_API_BASE_URL}/mails/check`,
          {
            params: {
              email: email.value,
              code: authCode.value,
            },
          },
        )
        verificationSuccess.value = response.data.success
        verificationError.value = !response.data.success
      } catch (error) {
        verificationSuccess.value = false
        verificationError.value = true
        console.error('Failed to verify code:', error)
      }
    }

    const handleRegister = async () => {
      validatePassword()
      validatePasswordMatch()
      if (!passwordError.value && verificationSuccess.value) {
        console.log('회원가입 진행 중...')
        try {
          const response = await axios.post(`${import.meta.env.VITE_API_BASE_URL}/members/register`, {
            email: email.value,
            password: password.value,
          },
          { 
            withCredentials: true
          });

          if (response.status === 201) {
            alert('회원가입이 완료되었습니다.')
            router.push('/login')
          }
        } catch (error) {
          console.error('회원가입 중 오류 발생:', error)
          alert('회원가입 중 문제가 발생했습니다. 다시 시도해 주세요.')
        }
      } else if (!verificationSuccess.value) {
        alert('이메일 인증이 완료되지 않았습니다.')
      }
    }

    return {
      email,
      authCode,
      password,
      confirmPassword,
      emailError,
      emailExistsError,
      passwordError,
      passwordFormatError,
      emailSent,
      verificationSuccess,
      verificationError,
      loading,
      validateEmail,
      validatePassword,
      validatePasswordMatch,
      handleAuthClick,
      handleVerifyAuthCode,
      handleRegister,
    }
  },
})
</script>

<style scoped>
.register-container {
  margin: auto;
  margin-top: 200px;
  max-width: 400px;
  padding: 20px;
  border-radius: 8px;
  justify-content: center;
  align-items: center;
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

.email {
  max-width: 240px;
  margin-right: 10px;
}

.auth-code {
  max-width: 285px;
  margin-right: 10px;
}

.auth-button,
.register-button {
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.auth-button {
  max-width: 100%;
}

.register-button {
  width: 100%;
}

button:hover {
  background-color: #0056b3;
}

.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 0.3s ease;
}
.slide-fade-enter-from,
.slide-fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

.error-message {
  color: red;
  font-size: 0.9rem;
  margin-top: 5px;
}

.success-message {
  color: #007bff;
  font-size: 0.9rem;
  margin-top: 5px;
}
</style>
