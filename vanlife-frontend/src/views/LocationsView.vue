<template>
    <div class="locations-view">
        <div class="map-section">
            <MapComponent 
                :locations="locations" 
                @refresh-locations="fetchLocationsInBounds"
            />
        </div>
        <div class="locations-grid">
            <div v-for="location in locations" :key="location.id" class="location-card">
                <div class="location-image" :style="{ backgroundImage: `url(${location.photoUrls?.[0] || '/placeholder-image.jpg'})` }"></div>
                <div class="location-content">
                    <h2>{{ location.name }}</h2>
                    <p class="description">{{ location.description }}</p>
                    <div class="location-details">
                        <div class="rating" v-if="location.ratings && location.ratings.length">
                            <span class="stars">★</span>
                            {{ calculateAverageRating(location.ratings).toFixed(1) }}
                        </div>
                        <div class="cost" v-if="location.cost">
                            {{ location.cost }}
                        </div>
                    </div>
                    <div class="tags" v-if="location.tags && location.tags.length">
                        <span v-for="tag in location.tags" :key="tag.id" class="tag">
                            {{ tag.name }}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import api from '@/services/api';
import MapComponent from '@/components/MapComponent.vue';

export default {
    name: 'LocationsView',
    components: {
        MapComponent
    },
    data() {
        return {
            locations: []
        };
    },
    methods: {
        async fetchLocations() {
            try {
                const response = await api.get('/locations');
                this.locations = response.data;
            } catch (error) {
                console.error('Error fetching locations:', error);
            }
        },
        async fetchLocationsInBounds(bounds) {
            try {
                console.log('Fetching locations with bounds:', bounds); // Debug log
                const response = await api.get('/locations', {
                    params: {
                        minLat: bounds.minLat,
                        maxLat: bounds.maxLat,
                        minLon: bounds.minLng,
                        maxLon: bounds.maxLng
                    }
                });
                console.log('Received locations:', response.data); // Debug log
                this.locations = response.data;
            } catch (error) {
                console.error('Error fetching locations in bounds:', error);
            }
        },
        calculateAverageRating(ratings) {
            if (!ratings || ratings.length === 0) return 0;
            const sum = ratings.reduce((acc, rating) => acc + rating.score, 0);
            return sum / ratings.length;
        }
    },
    created() {
        this.fetchLocations();
    }
};
</script>

<style scoped>
.locations-view {
    grid-template-columns: 2fr 1fr;
    grid-template-areas: "map grid";
    display: grid;
    gap: 20px;
    height: 75vh

}

.map-section {
    grid-area: map;
    margin-bottom: 20px;
   max-height: 100%;
}

.locations-grid {
    overflow-y: auto;
    grid-area: grid;
    max-height: 100%;
}

.location-card {
    background: linen;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s ease;
    margin-top: 7px;
    margin-right: 7px;
}

.location-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.location-image {
    width: 200px;
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
}

.location-content {
    flex: 1;
    padding: 16px;
}

h2 {
    margin: 0 0 8px 0;
    font-size: 1.5rem;
}

.description {
    margin: 0 0 16px 0;
    color: #666;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.location-details {
    display: flex;
    align-items: center;
    gap: 16px;
    margin-bottom: 12px;
}

.rating {
    display: flex;
    align-items: center;
    gap: 4px;
}

.stars {
    color: #ffd700;
}

.cost {
    color: #2c3e50;
    font-weight: 500;
}

.tags {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
}

.tag {
    background: #e9ecef;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 0.875rem;
    color: #495057;
}
</style>