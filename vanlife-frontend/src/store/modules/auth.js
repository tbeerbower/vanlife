import api from '@/services/api'

export default {
  namespaced: true,
  state: {
    user: null,
    token: localStorage.getItem('token') || null
  },
  mutations: {
    setUser(state, user) {
      console.log('Setting user:', user)
      state.user = user
    },
    setToken(state, token) {
      console.log('Setting token:', token)
      state.token = token
      if (token) {
        localStorage.setItem('token', token)
      } else {
        localStorage.removeItem('token')
      }
    }
  },
  actions: {
    initializeFromToken({ state, commit }) {
      if (state.token) {
        try {
          const payload = JSON.parse(atob(state.token.split('.')[1]))
          commit('setUser', payload)
        } catch (error) {
          console.error('Failed to initialize user from token:', error)
          commit('setToken', null)
          commit('setUser', null)
        }
      }
    },
    async loginWithGoogle({ commit }, googleResponse) {
      try {
        console.log('Processing Google login with code:', googleResponse.code)
        const response = await api.post('/auth/google', { 
          code: googleResponse.code
        })
        console.log('Backend response:', response.data)
        
        commit('setToken', response.data.jwt)
        commit('setUser', response.data.user)
        return response
      } catch (error) {
        console.error('Login failed in auth module:', error)
        if (error.response) {
          console.error('Backend error response:', error.response.data)
        }
        throw error
      }
    },
    async loginWithEmail({ commit }, credentials) {
      try {
        console.log('Processing email login...')
        const response = await api.post('/login', credentials)
        console.log('Backend response:', response.data)
        
        commit('setToken', response.data.jwt)
        commit('setUser', response.data.user)
        return response
      } catch (error) {
        console.error('Login failed in auth module:', error)
        if (error.response) {
          console.error('Backend error response:', error.response.data)
        }
        throw error
      }
    },
    logout({ commit }) {
      commit('setUser', null)
      commit('setToken', null)
    },
    async updateProfile({ commit, state }, profileData) {
      try {
        const updatedUser = {
          ...profileData
        }
        const response = await api.patch(`/users/${state.user.id}`, updatedUser)
        commit('setUser', response.data)
        return response
      } catch (error) {
        console.error('Profile update failed:', error)
        throw error
      }
    },
    async updateProfilePicture({ commit }, formData) {
      try {
        const response = await api.put('/users/profile/picture', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        commit('setUser', response.data)
        return response
      } catch (error) {
        console.error('Profile picture update failed:', error)
        throw error
      }
    }
  },
  getters: {
    isAuthenticated: state => !!state.token,
    currentUser: state => state.user
  }
} 