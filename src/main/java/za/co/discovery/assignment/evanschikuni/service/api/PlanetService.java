package za.co.discovery.assignment.evanschikuni.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import za.co.discovery.assignment.evanschikuni.dtos.request.PlanetRequest;
import za.co.discovery.assignment.evanschikuni.dtos.request.RouteRequest;
import za.co.discovery.assignment.evanschikuni.dtos.response.ApiResponse;
import za.co.discovery.assignment.evanschikuni.entity.PlanetName;
import za.co.discovery.assignment.evanschikuni.entity.Route;

public interface PlanetService {
    void processFile(MultipartFile file);
    Page<PlanetName> getAllPlanets(Pageable pageable);
    Page<Route> getAllRoutes(Pageable pageable);

    ResponseEntity<ApiResponse> savePlanet(PlanetRequest planetRequest);
    ResponseEntity<ApiResponse> saveRoute(RouteRequest routeRequest );
}
