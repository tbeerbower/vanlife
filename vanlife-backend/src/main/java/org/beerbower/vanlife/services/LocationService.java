package org.beerbower.vanlife.services;

import org.beerbower.vanlife.entities.Location;
import org.beerbower.vanlife.entities.Tag;
import org.beerbower.vanlife.repositories.LocationRepository;
import org.beerbower.vanlife.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TagRepository tagRepository;

    // Associate tags with a location
    public Location associateTagsWithLocation(Long locationId, Set<Long> tagIds) {
        // Fetch the location
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + locationId));

        // Fetch the tags
        Set<Tag> tags = new HashSet<>();
        for (Long tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new IllegalArgumentException("Tag not found with ID: " + tagId));
            tags.add(tag);
        }

        // Associate the tags with the location
        location.getTags().addAll(tags);

        // Save the updated location
        return locationRepository.save(location);
    }

    // Remove a tag from a location
    public Location removeTagFromLocation(Long locationId, Long tagId) {
        // Fetch the location
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + locationId));

        // Fetch the tag
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found with ID: " + tagId));

        // Remove the tag from the location
        location.getTags().remove(tag);

        // Save the updated location
        return locationRepository.save(location);
    }
}