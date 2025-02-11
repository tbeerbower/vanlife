<template>
  <header class="app-header">
    <nav>
      <router-link to="/" class="brand">
        <img src="@/assets/vanlife.webp
        " alt="Vanlife Logo" class="logo">
        <span>Van Life</span>
      </router-link>
      <div class="nav-links">
        <router-link to="/locations">Locations</router-link>
      </div>
      <div class="user-info" v-if="currentUser">
        <router-link to="/profile" class="user-profile-link">
          <img 
            :src="currentUser.picture || require('@/assets/anonymous-user.webp')" 
            :alt="currentUser.name" 
            class="user-avatar"
          >
          <span class="user-name">{{ currentUser.name }}</span>
        </router-link>
        <button @click="handleLogout" class="logout-btn">Logout</button>
      </div>
    </nav>
  </header>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'AppHeader',
  setup() {
    const store = useStore()
    const router = useRouter()

    const handleLogout = () => {
      store.dispatch('auth/logout')
      router.push('/login')
    }

    return {
      currentUser: computed(() => store.getters['auth/currentUser']),
      handleLogout
    }
  }
}
</script>

<style scoped>
.app-header {
  background-color: #2d3436;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

nav {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0.8rem 1.5rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.brand {
  display: flex;
  align-items: center;
  gap: 1rem;
  text-decoration: none;
  color: #dfe6e9;
  font-weight: 600;
  font-size: 1.2rem;
}

.logo {
  height: 80px;
  width: auto;
}

.nav-links {
  display: flex;
  gap: 2rem;
}

.nav-links a {
  color: #b2bec3;
  text-decoration: none;
  padding: 0.5rem 0;
  position: relative;
  transition: color 0.2s ease;
}

.nav-links a:hover {
  color: #dfe6e9;
}

.nav-links a::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 2px;
  background-color: #74b9ff;
  transition: width 0.2s ease;
}

.nav-links a:hover::after {
  width: 100%;
}

.nav-links a.router-link-active {
  color: #74b9ff;
}

.nav-links a.router-link-active::after {
  width: 100%;
  background-color: #74b9ff;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  border: 2px solid #74b9ff;
}

.user-name {
  color: #dfe6e9;
  font-size: 0.9rem;
}

.logout-btn {
  background-color: #636e72;
  color: #dfe6e9;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.logout-btn:hover {
  background-color: #74b9ff;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .brand span {
    display: none;
  }
  
  .nav-links {
    gap: 1rem;
  }
  
  .user-name {
    display: none;
  }
}

.user-profile-link {
  display: flex;
  align-items: center;
  gap: 1rem;
  text-decoration: none;
  color: inherit;
}

.user-profile-link:hover {
  opacity: 0.8;
}
</style> 