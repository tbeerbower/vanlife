<template>
    <div class="register-view">
      <img src="@/assets/vanlife.webp" alt="Van Life Logo" class="vanlife-logo">
      <h1>Create Account</h1>
      
      <form @submit.prevent="handleRegister" class="register-form">
        <div class="form-group">
          <input 
            v-model="form.name"
            type="text"
            placeholder="Full Name"
            required
          >
        </div>
        
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
        
        <div class="form-group">
          <input 
            v-model="confirmPassword"
            type="password"
            placeholder="Confirm Password"
            required
          >
        </div>
  
        <div v-if="error" class="error-message">
          {{ error }}
        </div>
        
        <button type="submit" class="register-btn">Create Account</button>
      </form>
      
      <div class="login-link">
        Already have an account? 
        <router-link to="/login">Sign in</router-link>
      </div>
    </div>
  </template>
  
  <script>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import api from '@/services/api'
  
  export default {
    name: 'RegisterView',
    setup() {
      const router = useRouter()
      const error = ref('')
      const confirmPassword = ref('')
      const form = ref({
        name: '',
        email: '',
        password: ''
      })
  
      const handleRegister = async () => {
        try {
          error.value = ''
          
          if (form.value.password !== confirmPassword.value) {
            error.value = 'Passwords do not match'
            return
          }
  
          const response = await api.post('/register', form.value)
          console.log('Registration successful:', response.data)
          router.push('/login')
        } catch (err) {
          console.error('Registration failed:', err)
          error.value = err.response?.data?.message || 'Registration failed'
        }
      }
  
      return {
        form,
        confirmPassword,
        error,
        handleRegister
      }
    }
  }
  </script>
  
  <style scoped>
  .register-view {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
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
  
  .register-form {
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
  
  .register-btn {
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
  
  .register-btn:hover {
    background-color: #0984e3;
  }
  
  .error-message {
    color: #ff7675;
    margin-bottom: 1rem;
    text-align: center;
  }
  
  .login-link {
    margin-top: 2rem;
    color: #b2bec3;
  }
  
  .login-link a {
    color: #74b9ff;
    text-decoration: none;
  }
  
  .login-link a:hover {
    text-decoration: underline;
  }
  
  @media (max-width: 768px) {
    h1 {
      font-size: 1.4rem;
      text-align: center;
    }
    
    .vanlife-logo {
      width: 150px;
    }
  }
  </style>