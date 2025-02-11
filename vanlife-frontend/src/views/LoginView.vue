<template>
  <div class="login-view">
    <img src="@/assets/vanlife.webp" alt="Van Life Logo" class="vanlife-logo">
    <h1>Welcome to Van Life</h1>
    
    <!-- Email/Password Login Form -->
    <form @submit.prevent="handleEmailLogin" v-if="loginMethod === 'email'" class="login-form">
      <div class="form-group">
        <input 
          v-model="form.email"
          type="email"
          placeholder="Email"
          required
        >
      </div>
      <div class="form-group">
        <input 
          v-model="form.password"
          type="password"
          placeholder="Password"
          required
        >
      </div>
      <div v-if="error" class="error-message">
        {{ error }}
      </div>
      <button type="submit" class="login-btn">Sign in</button>
    </form>

    <!-- Login Buttons -->
    <div v-if="loginMethod !== 'email'" class="login-buttons">
      <button @click="toggleLoginMethod" class="email-login-btn">
        Sign in with Email
      </button>
      
      <button @click="handleGoogleLogin" class="google-login-btn">
        <img src="@/assets/google-icon.png" alt="Google" class="google-icon">
        Sign in with Google
      </button>
    </div>

    <div class="register-link">
      Don't have an account? 
      <router-link to="/register">Sign up</router-link>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'LoginView',
  setup() {
    const store = useStore()
    const router = useRouter()
    const loginMethod = ref('google')
    const error = ref('')
    const form = ref({
      email: '',
      password: ''
    })

    const handleGoogleLogin = async () => {
      try {
        console.log('Initiating Google login...')
        const google = window.google
        const client = google.accounts.oauth2.initCodeClient({
          client_id: '32904200865-06fav6dc33cs4judtp1abrt10373ldsj.apps.googleusercontent.com',
          scope: 'email profile',
          callback: async (response) => {
            console.log('Google response received:', response)
            if (response.code) {
              try {
                await store.dispatch('auth/loginWithGoogle', response)
                console.log('Login successful, redirecting...')
                router.push('/')
              } catch (error) {
                console.error('Store dispatch failed:', error)
                error.value = 'Login failed. Please try again.'
              }
            } else {
              console.warn('No authorization code in response:', response)
            }
          },
        })
        client.requestCode()
      } catch (error) {
        console.error('Login failed:', error)
        error.value = 'Login failed. Please try again.'
      }
    }

    const handleEmailLogin = async () => {
      try {
        error.value = ''
        await store.dispatch('auth/loginWithEmail', form.value)
        console.log('Login successful, redirecting...')
        router.push('/')
      } catch (err) {
        console.error('Login failed:', err)
        error.value = err.response?.data?.message || 'Login failed. Please try again.'
      }
    }

    const toggleLoginMethod = () => {
      loginMethod.value = loginMethod.value === 'email' ? 'google' : 'email'
      error.value = ''
    }

    return {
      loginMethod,
      form,
      error,
      handleGoogleLogin,
      handleEmailLogin,
      toggleLoginMethod
    }
  }
}
</script>

<style scoped>
.login-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  background-color: #2d3436;
  color: #dfe6e9;
}

.vanlife-logo {
  width: 200px;
  margin-bottom: 20px;
  filter: drop-shadow(0 0 8px rgba(116, 185, 255, 0.3));
}

h1 {
  color: #dfe6e9;
  font-weight: 600;
  font-size: 1.8rem;
  margin-bottom: 2rem;
}

.login-form {
  width: 100%;
  max-width: 400px;
}

.form-group {
  margin-bottom: 1rem;
}

input {
  width: 100%;
  padding: 12px;
  border: none;
  border-radius: 4px;
  background: #636e72;
  color: #dfe6e9;
  font-size: 16px;
}

input::placeholder {
  color: #b2bec3;
}

.login-btn {
  width: 100%;
  padding: 12px 24px;
  border: none;
  border-radius: 4px;
  background: #74b9ff;
  color: #2d3436;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
  transition: background-color 0.2s ease;
}

.login-btn:hover {
  background-color: #0984e3;
}

.login-buttons {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  max-width: 400px;
}

.email-login-btn,
.google-login-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px 24px;
  border: none;
  border-radius: 4px;
  background: #636e72;
  color: #dfe6e9;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
  transition: background-color 0.2s ease;
}

.email-login-btn:hover,
.google-login-btn:hover {
  background-color: #74b9ff;
}

.google-icon {
  width: 20px;
  height: 20px;
  margin-right: 8px;
}

.error-message {
  color: #ff7675;
  margin-bottom: 1rem;
  text-align: center;
}

.register-link {
  margin-top: 2rem;
  color: #b2bec3;
}

.register-link a {
  color: #74b9ff;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}

@media (max-width: 768px) {
  h1 {
    font-size: 1.4rem;
    text-align: center;
  }
  
  .vanlife-logo {
    width: 300px;
  }
}
</style> 