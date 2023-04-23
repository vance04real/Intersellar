package za.co.discovery.assignment.evanschikuni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanetPath implements Comparable<PlanetPath>{

    private String planetName;
    private String currentPlanet;
    private double distance2origin = Double.MAX_VALUE;

    @Override
    public int compareTo(PlanetPath o) {
        return Double.compare(this.distance2origin, o.distance2origin);
    }



}
