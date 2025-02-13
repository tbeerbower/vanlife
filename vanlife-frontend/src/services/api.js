import axios from 'axios'
import store from '@/store'

const api = axios.create({
  baseURL: 'http://localhost:9000/api'
})

api.interceptors.request.use(config => {
  const token = store.state.auth.token
  console.log('API Request:', config.url, 'Token:', !!token)
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  response => {
    console.log('API Response:', response.config.url, response.status)
    return response
  },
  error => {
    console.error('API Error:', error.config?.url, error.response?.status)
    return Promise.reject(error)
  }
)

// Comment endpoints
export const addComment = async (locationId, text, rating) => {
  const response = await api.post(`/locations/${locationId}/comments`, {
    text,
    rating
  });
  return response.data;
};

export const getLocationComments = async (locationId) => {
  const response = await api.get(`/locations/${locationId}/comments`);
  return response.data;
};

export default api