package org.beerbower.vanlife.controllers;

import org.beerbower.vanlife.entities.Comment;
import org.beerbower.vanlife.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;


    // Get all comments for a location
    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<Comment>> getCommentsByLocation(@PathVariable Long locationId) {
        List<Comment> comments = commentRepository.findByLocationId(locationId);
        return ResponseEntity.ok(comments);
    }

    // Delete a comment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
