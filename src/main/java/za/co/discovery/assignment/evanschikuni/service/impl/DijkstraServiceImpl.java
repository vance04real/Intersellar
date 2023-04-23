package za.co.discovery.assignment.evanschikuni.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.evanschikuni.entity.Route;
import za.co.discovery.assignment.evanschikuni.model.PlanetNode;
import za.co.discovery.assignment.evanschikuni.model.PlanetPath;
import za.co.discovery.assignment.evanschikuni.repository.RouteRepository;
import za.co.discovery.assignment.evanschikuni.service.api.DijkstraService;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class DijkstraServiceImpl implements DijkstraService {

    private final Map<String, PlanetNode> nodes = new HashMap<>();

    private final RouteRepository routeRepository;

    private static final long dist = 9460730472580800L;

    @Override
    public void addRoute(String startPoint, String endPoint, Double distance) {
        PlanetNode startNode = nodes.get(startPoint);
        if (isNull(startNode)) {
            startNode = new PlanetNode(startPoint);

        }

        PlanetNode endNode = nodes.get(endPoint);
        if (isNull(endNode)) {
            endNode = new PlanetNode(endPoint);
        }

        startNode.addNeighbor(endNode, distance);
        endNode.addNeighbor(startNode, distance);

        nodes.put(startPoint, startNode);
        nodes.put(endPoint, endNode);

        log.info("Added route from {} to {} with distance {}", startPoint, endPoint, distance);

    }

    @Override
    public List<String> shortestPath(String startNodeName, String endNodeName) {
        log.info("Finding shortest path from {} to {}", startNodeName, endNodeName);

        // Get all routes from the route repository and add them to the map of nodes
        routeRepository.findAll().
                stream().
                map(route ->{
                    addRoute(route.getPlanetOrigin(),route.getPlanetDestination(),route.getDistance());
                    return null;
                }).
                collect(Collectors.toList());

        // Define a map of parent nodes, a set of visited nodes, and a priority queue of planet paths
        Map<String, String> parents = new HashMap<>();
        Set<String> visited = new HashSet<String>();
        PriorityQueue<PlanetPath> priorityQueue = new PriorityQueue<>();

        // Add the start node to the priority queue
        PlanetPath start = new PlanetPath(startNodeName, null, 0);
        priorityQueue.add(start);

        // If the map of nodes is empty, return null
        if(nodes.isEmpty()) return null;

        // Continue until the priority queue is empty
        while (priorityQueue.size() > 0) {
            PlanetPath currentPlanetPath = priorityQueue.remove();

            // If the current planet has not been visited yet, mark it as visited and update its neighbors
            if (!visited.contains(currentPlanetPath.getPlanetName())) {
                PlanetNode currentNode = nodes.get(currentPlanetPath.getPlanetName());
                parents.put(currentPlanetPath.getPlanetName(), currentPlanetPath.getCurrentPlanet());
                visited.add(currentPlanetPath.getPlanetName());
                if (currentPlanetPath.getPlanetName().equals(endNodeName)) {
                    return getPath(parents, endNodeName);
                }
                Arrays.stream(nodes.get(currentPlanetPath.getPlanetName()).getNeighbor())
                        .filter(Objects::nonNull)
                        .map(neighbor -> new PlanetPath(
                                neighbor.getPlanetName(),
                                currentPlanetPath.getPlanetName(),
                                currentPlanetPath.getDistance2origin() + currentNode.getNeighborDistance(neighbor)
                        ))
                        .forEach(priorityQueue::add);

            }
        }
        log.warn("No path found from {} to {}", startNodeName, endNodeName);
        return null;

    }

    @Override
    public Double getDistance(List<String> path) {
        log.info("Calculating distance for path: {}", path);
        // Create a new atomic reference for the distance and initialize it to zero
        AtomicReference<Double> distance = new AtomicReference<>(0.);

        // Loop through each pair of adjacent nodes in the path
        IntStream.range(0, path.size() - 1)
                // Get the route between the current node and the next node in the path from the route repository
                .forEach(i -> {
                    String origin = path.get(i);
                    String destination = path.get(i + 1);
                    Route route = routeRepository.findByPlanetOriginAndPlanetDestination(path.get(i),path.get(i + 1));
                    // Add the distance of the route to the distance accumulator
                    if (isNull(route)) {
                        log.warn("No route found between {} and {}", origin, destination);
                    } else {
                        // Add the distance of the route to the distance accumulator
                        Double routeDistance = route.getDistance();
                        distance.set(distance.get() + routeDistance);
                        log.info("Added distance {} from {} to {}", routeDistance, origin, destination);
                    }
                });

        // Return the total distance multiplied by the constant dist
        Double totalDistance = distance.get() * dist;
        log.info("Total distance for path {} is {}", path, totalDistance);
        return totalDistance;

    }

    private List<String> getPath(Map<String, String> parents, String endNodeName) {
        List<String> path = new ArrayList<String>();
        String node = endNodeName;
        while (!Strings.isBlank(node)) {
            path.add(0, node);
            String parent = parents.get(node);
            node = parent;
        }

        return path;
    }
}
