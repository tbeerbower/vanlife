<template>
  <div id="app">
    <app-header />
    <router-view></router-view>
    <app-footer />
  </div>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'

export default {
  name: 'App',
  components: {
    AppHeader,
    AppFooter
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
  display: grid;
  grid-template-rows: 100px 1fr 40px;
  grid-template-areas: "header" "main" "footer";
  height: 95vh;
  overflow: hidden;
}

app-header {
  grid-area: header;
  max-height: 100%;
}

router-view {
  grid-area: main;
  overflow: hidden;
  max-height: 100%;

}

app-footer {
  grid-area: footer;
}

</style>
