package za.co.discovery.assignment.evanschikuni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanetNode {

    String planetName;
    Map<PlanetNode, Double> neighbors = new HashMap<>();

    public PlanetNode(String startPoint) {
        this.planetName = startPoint;
    }

    public void addNeighbor(PlanetNode neighbor, Double distance) {
        neighbors.put(neighbor, distance);
    }

    public PlanetNode[] getNeighbor() {
        PlanetNode[] result = new PlanetNode[neighbors.size()];
        neighbors.keySet().toArray(result);
        return result;
    }

    public double getNeighborDistance(PlanetNode node) {
        return neighbors.get(node);
    }

    @Override
    public int hashCode() {
        return getPlanetName().hashCode();
    }

}
