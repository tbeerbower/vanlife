package org.beerbower.vanlife.controllers;

import org.beerbower.vanlife.entities.Tag;
import org.beerbower.vanlife.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    // Create a new tag
    @PostMapping
    public ResponseEntity<Tag> createTag(
            @RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.createTag(tag));
    }

    // Get all predefined tags
    @GetMapping("/predefined")
    public ResponseEntity<List<Tag>> getAllPredefinedTags() {
        List<Tag> tags = tagService.findAllPredefinedTags();
        return ResponseEntity.ok(tags);
    }

    // Get a tag by name
    @GetMapping("/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name) {
        return tagService.findTagByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a tag by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}