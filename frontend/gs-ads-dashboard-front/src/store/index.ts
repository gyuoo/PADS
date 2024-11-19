import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
  state: () => ({
    isLoggedIn: false,
    sessionId: '',
  }),
  actions: {
    login(sessionId: string) {
      this.isLoggedIn = true;
      this.sessionId = sessionId;
      localStorage.setItem('sessionId', sessionId); // 세션 ID 저장
    },
    logout() {
      this.isLoggedIn = false;
      this.sessionId = '';
      localStorage.removeItem('sessionId'); // 세션 ID 제거
    },
  },
});
