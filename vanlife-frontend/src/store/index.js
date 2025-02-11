import { createStore } from 'vuex'
import auth from './modules/auth'

const store = createStore({
  state: {
    locations: [],
    loading: false,
    error: null
  },
  mutations: {
    setLocations(state, locations) {
      state.locations = locations
    },
    setLoading(state, loading) {
      state.loading = loading
    },
    setError(state, error) {
      state.error = error
    }
  },
  actions: {
 
  },
  getters: {
    getLocations: state => state.locations,
    isLoading: state => state.loading,
    error: state => state.error
  },
  modules: {
    auth
  }
})

export default store 