<template>
  <div>
    <div v-if="loading" class="loading">Loading...</div>
    <div v-else-if="error" class="error">Error: {{ error }}</div>
    <div class="location-view" v-else>
      <div class="map-container">
        <MapComponent :locations="[location]" />
      </div>
      <div class="location-details">
        <h1>{{ location.name }}</h1>
        <p>{{ location.description }}</p>
        <div v-if="location.photoUrls && location.photoUrls.length" class="location-images">
          <img v-for="(photoUrl, index) in location.photoUrls" :key="index" :src="photoUrl" :alt="`Location Photo ${index + 1}`" />
        </div>
        <div class="location-info">
          <p><strong>Address:</strong> {{ location.address }}</p>
          <p><strong>Cost:</strong> {{ location.cost }}</p>
          <p><strong>Rating:</strong> {{ averageRating.toFixed(1) }} / 5</p>
        </div>

        
      </div>
      <!-- Comments Section -->
      <div class="comments-section">
          <h2>Comments</h2>
          
          <!-- Add Comment Form -->
          <div v-if="isAuthenticated" class="add-comment-form">
            <div class="rating-input">
              <label>Rating:</label>
              <div class="star-rating">
                <span
                  v-for="star in 5"
                  :key="star"
                  :class="{ active: star <= newComment.rating }"
                  @click="newComment.rating = star"
                  class="star"
                >★</span>
              </div>
            </div>
            <textarea
              v-model="newComment.text"
              placeholder="Share your experience..."
              rows="3"
              class="comment-textarea"
            ></textarea>
            <button @click="submitComment" class="submit-button" :disabled="!isValidComment">
              Submit Comment
            </button>
          </div>
          <div v-else class="login-prompt">
            <p>Please <router-link to="/login">login</router-link> to leave a comment.</p>
          </div>

          <!-- Comments List -->
          <div class="comments-list">
            <div v-if="comments.length === 0" class="no-comments">
              No comments yet. Be the first to share your experience!
            </div>
            <div v-else v-for="comment in sortedComments" :key="comment.id" class="comment-item">
              <div class="comment-header">
                <img :src="comment.user.picture" :alt="comment.user.name" class="user-avatar" />
                <span class="user-name">{{ comment.user.name }}</span>
                <div class="rating-display">
                  <span v-for="star in 5" :key="star" class="star" :class="{ active: star <= comment.rating }">
                    ★
                  </span>
                </div>
                <span class="comment-date">{{ formatDate(comment.createdAt) }}</span>
              </div>
              <p class="comment-text">{{ comment.text }}</p>
            </div>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
import MapComponent from '@/components/MapComponent.vue';
import api, { addComment, getLocationComments } from '@/services/api';
import { useStore } from 'vuex';
import { computed } from 'vue';

export default {
  name: 'LocationView',
  components: {
    MapComponent,
  },
  setup() {
    const store = useStore();
    const isAuthenticated = computed(() => store.getters['auth/isAuthenticated']);
    const currentUser = computed(() => store.getters['auth/currentUser']);
    return { isAuthenticated, currentUser };
  },
  data() {
    return {
      location: null,
      loading: true,
      error: null,
      comments: [],
      newComment: {
        text: '',
        rating: 0
      }
    };
  },
  computed: {
    sortedComments() {
      return [...this.comments].sort((a, b) => 
        new Date(b.createdAt) - new Date(a.createdAt)
      );
    },
    averageRating() {
      if (this.comments.length === 0) return 0;
      const sum = this.comments.reduce((acc, comment) => acc + comment.rating, 0);
      return sum / this.comments.length;
    },
    isValidComment() {
      return this.newComment.text.trim().length > 0 && this.newComment.rating > 0;
    }
  },
  created() {
    const locationId = this.$route.params.id;
    this.fetchLocation(locationId);
    this.fetchComments(locationId);
    
    // Initialize auth from token if needed
    this.$store.dispatch('auth/initializeFromToken');
  },
  methods: {
    async fetchLocation(locationId) {
      try {
        const response = await api.get(`/locations/${locationId}`);
        this.location = response.data;
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },
    async fetchComments(locationId) {
      try {
        const comments = await getLocationComments(locationId);
        this.comments = comments;
      } catch (err) {
        console.error('Error fetching comments:', err);
      }
    },
    async submitComment() {
      if (!this.isValidComment) return;
      
      try {
        const locationId = this.$route.params.id;
        const comment = await addComment(locationId, this.newComment.text, this.newComment.rating);
        this.comments.unshift(comment);
        this.newComment.text = '';
        this.newComment.rating = 0;
      } catch (err) {
        console.error('Error submitting comment:', err);
        this.error = 'Failed to submit comment. Please try again.';
      }
    },
    formatDate(dateString) {
      return new Date(dateString).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
      });
    }
  }
};
</script>

<style scoped>
.location-view {
  display: grid;
  grid-template-columns: 2fr 3fr;
  grid-template-rows: 1fr 3fr;
  grid-template-areas: 
   "map details"
   "map comments";
  gap: 20px;
  padding: 20px;
  height: 70vh;
}

.map-container {
  grid-area: map;
  height: 100%;
}

.location-details {
  grid-area: details;
  display: flex;
  flex-direction: column;
  height: 100%;
  overflow: hidden; /* Prevent scrolling of the main container */
}

.location-details h1 {
  margin-top: 0;
  margin-bottom: 5px;
}
.location-details p {
  margin-top: 3px;
  margin-bottom: 3px;
}


.comments-section {
  grid-area: comments;
  border-top: 1px solid #eee;
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0; /* Important for nested flex scrolling */
}


.add-comment-form {
  margin-bottom: 20px;
  flex-shrink: 0; /* Prevent form from shrinking */
}

.comments-list {
  overflow-y: auto;
  flex: 1;
  padding-right: 10px; /* Space for scrollbar */
}

.comment-item {
  padding: 15px;
  border-bottom: 1px solid #eee;
  word-wrap: break-word; /* Ensure text wraps */
  overflow-wrap: break-word;
}

.comment-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
  flex-wrap: wrap; /* Allow wrapping of header items */
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  flex-shrink: 0; /* Prevent avatar from shrinking */
}

.user-name {
  font-weight: bold;
  margin-right: auto; /* Push rating and date to the right */
}

.comment-date {
  color: #666;
  font-size: 0.9em;
  white-space: nowrap; /* Keep date on one line */
}

.rating-display {
  display: flex;
  gap: 2px;
  flex-shrink: 0; /* Prevent stars from shrinking */
}

.comment-text {
  margin: 0;
  line-height: 1.5;
  white-space: pre-wrap; /* Preserve line breaks while wrapping */
}

.rating-input {
  margin-bottom: 15px;
}

.star-rating {
  display: inline-flex;
  gap: 5px;
}

.star {
  cursor: pointer;
  font-size: 24px;
  color: #ddd;
}

.star.active {
  color: #ffd700;
}

.comment-textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
  font-family: inherit;
  resize: vertical; /* Allow vertical resizing only */
}

.submit-button {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.no-comments {
  text-align: center;
  color: #666;
  padding: 20px;
}

.login-prompt {
  text-align: center;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.login-prompt a {
  color: #007bff;
  text-decoration: none;
}

.login-prompt a:hover {
  text-decoration: underline;
}
</style>
