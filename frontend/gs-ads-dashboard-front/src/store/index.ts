import { createStore } from 'vuex'

export default createStore({
  state: {
    isLoggedIn: false, // 로그인 상태
    sessionId: '', // 세션 ID
  },
  mutations: {
    setLoginState(state, payload) {
      state.isLoggedIn = payload.isLoggedIn
      state.sessionId = payload.sessionId || ''
    },
    logout(state) {
      state.isLoggedIn = false
      state.sessionId = ''
    },
  },
  actions: {
    login({ commit }, sessionId) {
      commit('setLoginState', { isLoggedIn: true, sessionId })
      localStorage.setItem('sessionId', sessionId)
    },
    restoreLoginState({ commit }) {
      const sessionId = localStorage.getItem('sessionId')
      if (sessionId) {
        commit('setLoginState', { isLoggedIn: true, sessionId })
      }
    },
    logout({ commit }) {
      commit('logout')
      localStorage.removeItem('sessionId')
    },
  },
  getters: {
    isLoggedIn: (state) => state.isLoggedIn,
    sessionId: (state) => state.sessionId,
  },
})
