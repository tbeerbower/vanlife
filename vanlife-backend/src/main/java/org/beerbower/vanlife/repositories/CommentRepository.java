package org.beerbower.vanlife.repositories;

import org.beerbower.vanlife.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Find all comments for a specific location
    List<Comment> findByLocationId(Long locationId);

    // Find all comments by a specific user
    List<Comment> findByUserId(Long userId);

    // Find comments containing a specific keyword (case-insensitive)
    List<Comment> findByTextContainingIgnoreCase(String keyword);
}