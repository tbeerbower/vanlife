package org.beerbower.vanlife.services;

import org.beerbower.vanlife.entities.Tag;
import org.beerbower.vanlife.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    // Create a new tag (predefined or user-defined)
    public Tag createTag(Tag tag) {
        // Check if the tag already exists
        Optional<Tag> existingTag = tagRepository.findByName(tag.getName());
        if (existingTag.isPresent()) {
            throw new IllegalArgumentException("Tag with name '" + tag.getName() + "' already exists.");
        }

        // Save the new tag
        return tagRepository.save(tag);
    }

    // Associate a tag with a location
    public void associateTagWithLocation(Long tagId, Long locationId) {
        // This method assumes you have a LocationService to fetch the location
        // and a mechanism to handle the many-to-many relationship.
        // Implementation details depend on your Location and Tag entities.
    }

    // Find a tag by name
    public Optional<Tag> findTagByName(String name) {
        return tagRepository.findByName(name);
    }

    // Find all predefined tags
    public List<Tag> findAllPredefinedTags() {
        return tagRepository.findByPredefined(true);
    }

    // Find tags by name containing a keyword (case-insensitive)
    public List<Tag> findTagsByNameContaining(String keyword) {
        return tagRepository.findByNameContainingIgnoreCase(keyword);
    }

    // Delete a tag by ID
    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }
}
