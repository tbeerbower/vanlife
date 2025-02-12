<template>
  <div class="map-container">
    <div class="map-overlay">
      <button 
        v-show="boundsChanged"
        class="refresh-button"
        @click="refreshLocations"
      >
        Refresh Locations
      </button>
    </div>
    <l-map 
      ref="map" 
      :zoom="zoom"
      :center="initialCenter"
      @ready="initializeMap"
      @update:bounds="onBoundsUpdated"
    >
      <l-tile-layer :url="tileLayerUrl" :attribution="attribution"></l-tile-layer>
      <l-marker
        v-for="location in locations"
        :key="location.id"
        :lat-lng="[location.latitude, location.longitude]"
      >
        <l-popup>
          <h3>{{ location.name }}</h3>
          <p>{{ location.description }}</p>
        </l-popup>
      </l-marker>
    </l-map>
  </div>
</template>

<script>
import { LMap, LTileLayer, LMarker, LPopup } from "@vue-leaflet/vue-leaflet";
import "leaflet/dist/leaflet.css";
import L from 'leaflet';

// Fix Leaflet's default marker icons
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

export default {
  name: 'MapComponent',
  components: {
    LMap,
    LTileLayer,
    LMarker,
    LPopup,
  },
  props: {
    locations: {
      type: Array,
      required: true,
      default: () => [],
      validator: (value) => {
        return value.every(location => 
          typeof location.latitude === 'number' && 
          typeof location.longitude === 'number'
        );
      }
    }
  },
  data() {
    return {
      zoom: 13,
      tileLayerUrl: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      boundsChanged: false,
      currentBounds: null,
      initialBoundsSet: false
    };
  },
  computed: {
    initialCenter() {
      if (this.locations.length === 0) {
        return [40.0516, -76.3057]; // Default center
      }
      
      const lats = this.locations.map(loc => loc.latitude);
      const lngs = this.locations.map(loc => loc.longitude);
      const centerLat = (Math.min(...lats) + Math.max(...lats)) / 2;
      const centerLng = (Math.min(...lngs) + Math.max(...lngs)) / 2;
      return [centerLat, centerLng];
    }
  },
  mounted() {
  },
  methods: {
    calculateBoundsWithBuffer(locations) {
      // Get min and max coordinates
      const lats = locations.map(loc => loc.latitude);
      const lngs = locations.map(loc => loc.longitude);
      
      const minLat = Math.min(...lats);
      const maxLat = Math.max(...lats);
      const minLng = Math.min(...lngs);
      const maxLng = Math.max(...lngs);
      
      // Calculate the range of coordinates
      const latRange = maxLat - minLat;
      const lngRange = maxLng - minLng;
      
      // Add a buffer (10% of the range)
      const latBuffer = Math.max(latRange * 0.1, 0.01); // minimum buffer of 0.01 degrees
      const lngBuffer = Math.max(lngRange * 0.1, 0.01);
      
      return {
        southWest: [minLat - latBuffer, minLng - lngBuffer],
        northEast: [maxLat + latBuffer, maxLng + lngBuffer]
      };
    },
    
    initializeMap() {
      this.$nextTick(() => {
        const map = this.$refs.map?.leafletObject;
        if (!map || this.locations.length === 0) return;

        const bounds = this.calculateBoundsWithBuffer(this.locations);
        const latLngBounds = L.latLngBounds(
          bounds.southWest,
          bounds.northEast
        );
        
        map.fitBounds(latLngBounds);
        this.initialBoundsSet = true;
      });
    },
    
    onBoundsUpdated(bounds) {
      if (!bounds || !this.initialBoundsSet) return;

      this.currentBounds = {
        minLat: bounds._southWest.lat,
        maxLat: bounds._northEast.lat,
        minLng: bounds._southWest.lng,
        maxLng: bounds._northEast.lng
      };
      this.boundsChanged = true;
    },
    
    refreshLocations() {
      this.$emit('refresh-locations', this.currentBounds);
      this.boundsChanged = false;
    }
  },
  watch: {
    locations: {
      handler() {
        this.initializeMap();
      },
      immediate: true
    }
  }
};
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 400px;
  border-radius: 8px;
  position: relative;
}

.leaflet-container {
  height: 100%;
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.map-overlay {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1000;
}

.refresh-button {
  background-color: #ffffff;
  border: 2px solid rgba(0, 0, 0, 0.2);
  border-radius: 4px;
  padding: 8px 12px;
  font-size: 14px;
  font-weight: 500;
  color: #333;
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.2s ease;
}

.refresh-button:hover {
  background-color: #f8f8f8;
  transform: translateY(-1px);
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
}

.refresh-button:active {
  transform: translateY(0);
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}
</style>