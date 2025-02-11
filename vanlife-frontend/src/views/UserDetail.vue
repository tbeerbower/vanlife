<template>
    <div class="user-detail">
      <h1>Profile</h1>
      <div class="profile-container">
        <div class="profile-image">
          <img 
            :src="currentUser?.picture || require('@/assets/anonymous-user.webp')" 
            :alt="currentUser?.name"
            class="user-avatar"
          >
          <input 
            type="file" 
            ref="fileInput" 
            @change="handleImageUpload" 
            accept="image/*"
            style="display: none"
          >
          <button @click="$refs.fileInput.click()" class="change-photo-btn">
            Change Photo
          </button>
        </div>
        
        <div class="profile-info">
          <div class="form-group">
            <label>Name</label>
            <input 
              v-model="form.name" 
              type="text"
            >
          </div>
          
          <div class="form-group">
            <label>Email</label>
            <input 
              :value="currentUser?.email" 
              type="email" 
              disabled
            >
          </div>
  
          <div v-if="error" class="error-message">
            {{ error }}
          </div>
          <div v-if="success" class="success-message">
            {{ success }}
          </div>

          <div class="button-group">
            <button @click="handleSave" class="save-btn" :disabled="!hasChanges">
              Save Changes
            </button>
            <button @click="handleCancel" class="cancel-btn" :disabled="!hasChanges">
              Cancel
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, computed, onMounted } from 'vue'
  import { useStore } from 'vuex'
  
  
  export default {
    name: 'UserDetail',
    setup() {
      const store = useStore()
      const error = ref('')
      const success = ref('')
      const fileInput = ref(null)
      const originalForm = ref({})
      const form = ref({
        name: '',
        picture: '',
        email: '',
        roles: '',
        active: true
      })
  
      onMounted(() => {
        const user = store.getters['auth/currentUser']
        if (user) {
          form.value = { ...user }
          originalForm.value = { ...user }
        }
      })
  
      const hasChanges = computed(() => {
        return form.value.name !== originalForm.value.name ||
               form.value.picture !== originalForm.value.picture
      })
  
      const handleSave = async () => {
        try {
          error.value = ''
          success.value = ''
          await store.dispatch('auth/updateProfile', { 
            ...form.value,
            id: originalForm.value.id 
          })
          success.value = 'Profile updated successfully'
          originalForm.value = { ...form.value }
        } catch (err) {
          error.value = 'Failed to update profile'
          console.error('Update failed:', err)
        }
      }
  
      const handleCancel = () => {
        form.value = { ...originalForm.value }
        error.value = ''
        success.value = ''
      }
  
      const handleImageUpload = async (event) => {
        try {
          error.value = ''
          success.value = ''
          const file = event.target.files[0]
          if (!file) return
  
          const formData = new FormData()
          formData.append('picture', file)
  
          await store.dispatch('auth/updateProfilePicture', formData)
          success.value = 'Profile picture updated successfully'
          // Update the form with new picture URL
          form.value.picture = store.getters['auth/currentUser'].picture
          originalForm.value = { ...form.value }
        } catch (err) {
          error.value = 'Failed to update profile picture'
          console.error('Upload failed:', err)
        }
      }
  
      return {
        currentUser: computed(() => store.getters['auth/currentUser']),
        form,
        error,
        success,
        fileInput,
        hasChanges,
        handleSave,
        handleCancel,
        handleImageUpload
      }
    }
  }
  </script>
  
  <style scoped>
  .user-detail {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
  }
  
  .profile-container {
    display: flex;
    gap: 40px;
    margin-top: 20px;
    background: #fff;
    padding: 30px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .profile-image {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 15px;
  }
  
  .user-avatar {
    width: 200px;
    height: 200px;
    border-radius: 50%;
    object-fit: cover;
    border: 3px solid #74b9ff;
  }
  
  .profile-info {
    flex: 1;
  }
  
  .form-group {
    margin-bottom: 20px;
  }
  
  .form-group label {
    display: block;
    margin-bottom: 5px;
    color: #636e72;
  }
  
  .form-group input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 16px;
  }
  
  .form-group input:disabled {
    background-color: #f5f5f5;
  }
  
  .change-photo-btn {
    padding: 8px 16px;
    background-color: #74b9ff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
  }
  
  .change-photo-btn:hover {
    background-color: #0984e3;
  }
  
  .error-message {
    color: #ff7675;
    margin-top: 10px;
  }
  
  .success-message {
    color: #00b894;
    margin-top: 10px;
  }
  
  .button-group {
    display: flex;
    gap: 1rem;
    margin-top: 2rem;
  }
  
  .save-btn, .cancel-btn {
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    transition: all 0.2s ease;
  }
  
  .save-btn {
    background-color: #74b9ff;
    color: white;
  }
  
  .save-btn:hover:not(:disabled) {
    background-color: #0984e3;
  }
  
  .cancel-btn {
    background-color: #636e72;
    color: white;
  }
  
  .cancel-btn:hover:not(:disabled) {
    background-color: #2d3436;
  }
  
  .save-btn:disabled, .cancel-btn:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
  </style>