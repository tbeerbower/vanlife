package org.beerbower.vanlife.controllers;

import org.beerbower.vanlife.entities.Comment;
import org.beerbower.vanlife.entities.Location;
import org.beerbower.vanlife.entities.User;
import org.beerbower.vanlife.repositories.CommentRepository;
import org.beerbower.vanlife.repositories.LocationRepository;
import org.beerbower.vanlife.repositories.UserRepository;
import org.beerbower.vanlife.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    // Create a new location
    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location savedLocation = locationRepository.save(location);
        return ResponseEntity.ok(savedLocation);
    }

    // Get all locations
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations(
            @RequestParam(required = false) Double minLat,
            @RequestParam(required = false) Double maxLat,
            @RequestParam(required = false) Double minLon,
            @RequestParam(required = false) Double maxLon) {
        List<Location> locations;
        if (minLat != null && maxLat != null && minLon != null && maxLon != null) {
            locations = locationRepository.findByLatitudeBetweenAndLongitudeBetween(minLat, maxLat, minLon, maxLon);
        } else {
            locations = locationRepository.findAll();
        }
        return ResponseEntity.ok(locations);
    }

    // Get a location by ID
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        return locationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Associate tags with a location
    @PostMapping("/{locationId}/tags")
    public ResponseEntity<Location> associateTagsWithLocation(
            @PathVariable Long locationId,
            @RequestBody Set<Long> tagIds) {
        Location updatedLocation = locationService.associateTagsWithLocation(locationId, tagIds);
        return ResponseEntity.ok(updatedLocation);
    }

    // Remove a tag from a location
    @DeleteMapping("/{locationId}/tags/{tagId}")
    public ResponseEntity<Location> removeTagFromLocation(
            @PathVariable Long locationId,
            @PathVariable Long tagId) {
        Location updatedLocation = locationService.removeTagFromLocation(locationId, tagId);
        return ResponseEntity.ok(updatedLocation);
    }

    // Find locations by tag name
    @GetMapping("/search")
    public ResponseEntity<List<Location>> findLocationsByTag(@RequestParam String tagName) {
        List<Location> locations = locationRepository.findByTagsName(tagName);
        return ResponseEntity.ok(locations);
    }


    // Add a comment to a location
    @PostMapping("/{locationId}/comments")
    public ResponseEntity<Comment> addComment(
            Principal principal,
            @PathVariable Long locationId,
            @RequestBody Comment comment) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + principal.getName()));
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + locationId));

        comment.setUser(user);
        comment.setLocation(location);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(savedComment);
    }
}