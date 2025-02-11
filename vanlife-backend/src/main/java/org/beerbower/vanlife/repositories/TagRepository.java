package org.beerbower.vanlife.repositories;

import org.beerbower.vanlife.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Find a tag by name
    Optional<Tag> findByName(String name);

    // Find all predefined tags
    List<Tag> findByPredefined(boolean predefined);

    // Find tags by name containing a keyword (case-insensitive)
    List<Tag> findByNameContainingIgnoreCase(String name);
}