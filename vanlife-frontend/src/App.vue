<template>
  <div id="app">
    <app-header v-if="isAuthenticated" />
    <router-view></router-view>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import AppHeader from './components/AppHeader.vue'

export default {
  name: 'App',
  components: {
    AppHeader
  },
  setup() {
    const store = useStore()
    
    // Initialize user state from token
    store.dispatch('auth/initializeFromToken')
    
    return {
      isAuthenticated: computed(() => store.getters['auth/isAuthenticated'])
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
