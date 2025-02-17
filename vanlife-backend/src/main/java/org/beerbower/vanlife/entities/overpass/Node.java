package org.beerbower.vanlife.entities.overpass;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class Node {
    // Getters and Setters
    private long id;
    private double lat;
    private double lon;
    private Map<String, String> tags;

}
