package org.beerbower.vanlife.services.overpass;

import lombok.Getter;
import lombok.Setter;
import org.beerbower.vanlife.entities.overpass.Node;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class OverpassService {
    private static final String OVERPASS_URL = "https://overpass-api.de/api/interpreter";

    public List<Node> fetchPOIs(double minLat, double minLon, double maxLat, double maxLon) {
        // Define Overpass QL Query
        String query = String.format(
                "[out:json];(node[\"highway\"=\"rest_area\"](%f,%f,%f,%f);"
                        + "node[\"tourism\"=\"camp_site\"](%f,%f,%f,%f);"
                        + "node[\"amenity\"=\"restaurant\"](%f,%f,%f,%f);"
                        + "node[\"amenity\"=\"parking\"][\"access\"=\"public\"](%f,%f,%f,%f);" +
                        ");out body;",
                minLat, minLon, maxLat, maxLon,
                minLat, minLon, maxLat, maxLon,
                minLat, minLon, maxLat, maxLon,
                minLat, minLon, maxLat, maxLon
        );

        // Use UriComponentsBuilder to safely encode the query
        String encodedQuery = UriComponentsBuilder.fromUriString(OVERPASS_URL)
                .queryParam("data", query)
                .build()
                .toUriString();

        // Make the HTTP request and map the response directly to a List of Node objects
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OverpassResponse> response = restTemplate.getForEntity(encodedQuery, OverpassResponse.class);

        return response.getBody().getElements();
    }


    public Optional<Node> fetchPOI(long id) {
        // Define Overpass QL Query
        String query = String.format(
                "[out:json];node(%d);out body;",
                id
        );

        // Use UriComponentsBuilder to safely encode the query
        String encodedQuery = UriComponentsBuilder.fromUriString(OVERPASS_URL)
                .queryParam("data", query)
                .build()
                .toUriString();

        // Make the HTTP request and map the response directly to a List of Node objects
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OverpassResponse> response = restTemplate.getForEntity(encodedQuery, OverpassResponse.class);

        OverpassResponse body = response.getBody();
        return body == null ? Optional.empty() : Optional.of(body.elements.getFirst());
    }

    @Setter
    @Getter
    private static class OverpassResponse {
        // Getter and Setter
        private List<Node> elements;
    }
}
