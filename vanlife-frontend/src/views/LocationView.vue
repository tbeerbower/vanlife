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
          <p><strong>Rating:</strong> {{ location.rating }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MapComponent from '@/components/MapComponent.vue';
import api from '@/services/api';

export default {
  name: 'LocationView',
  components: {
    MapComponent,
  },
  data() {
    return {
      location: null,
      loading: true,
      error: null,
    };
  },
  created() {
    const locationId = this.$route.params.id;
    this.fetchLocation(locationId);
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
  },
};
</script>

<style scoped>
.location-view {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  padding: 20px;
}

.map-container {
  height: 70vh;
}

.location-details {
  padding: 20px;
}

.location-images img {
  width: 100%;
  margin-bottom: 10px;
  border-radius: 8px;
}

.location-info {
  margin-top: 20px;
}

.loading,
.error {
  text-align: center;
  padding: 20px;
}
</style>
