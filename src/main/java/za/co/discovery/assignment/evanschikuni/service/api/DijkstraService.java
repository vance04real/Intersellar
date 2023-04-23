package za.co.discovery.assignment.evanschikuni.service.api;


import java.util.List;

public interface DijkstraService {

    void addRoute(String startPoint, String endPoint, Double distance);
    List<String> shortestPath(String startNodeName, String endNodeName);
    Double getDistance(List<String> paths);
}
