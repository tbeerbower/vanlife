package org.beerbower.vanlife.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Tag {
    public enum Type {
        SERVICE, ACTIVITY, AMENITY, HIGHWAY, TOURISM, OTHER;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Name of the tag (e.g., overnight_parking)

    @Column(nullable = false)
    private boolean predefined = false; // Indicates if the tag is predefined

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Type type = Type.OTHER; // Type of tag (e.g., service, activity, amenity, other)

    @Column(length = 1000)
    private String description; // Optional description of the tag
}