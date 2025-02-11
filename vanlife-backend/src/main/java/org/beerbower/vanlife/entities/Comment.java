package org.beerbower.vanlife.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer rating; // Rating score (e.g., 1-5 stars)

    @Column(length = 1000, nullable = false)
    private String text; // Comment text

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User who left the comment

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location; // Location being commented on

    private LocalDateTime createdAt; // Timestamp for when the comment was added
}