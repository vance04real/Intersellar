package za.co.discovery.assignment.evanschikuni.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.discovery.assignment.evanschikuni.dtos.request.PlanetRequest;
import za.co.discovery.assignment.evanschikuni.dtos.request.RouteRequest;
import za.co.discovery.assignment.evanschikuni.dtos.response.ApiResponse;
import za.co.discovery.assignment.evanschikuni.entity.PlanetName;
import za.co.discovery.assignment.evanschikuni.entity.Route;
import za.co.discovery.assignment.evanschikuni.service.api.PlanetService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PlanetController {


    private final PlanetService planetService;

    @PostMapping(value = "/planet/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> savePlanet(@RequestBody PlanetRequest planetRequest ) {
      return planetService.savePlanet(planetRequest);
    }


    @PostMapping(value = "/route/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> saveRoute(@RequestBody RouteRequest routeRequest ) {
        return planetService.saveRoute(routeRequest);
    }

    @PostMapping(value = "/file/upload",consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> routeFileUpload(@RequestParam("file") MultipartFile file) {
        planetService.processFile(file);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.format("You successfully uploaded %s !",file.getOriginalFilename()));
    }

    @GetMapping(value = "/planet/all",consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<PlanetName> getAllPlanetNames(@PageableDefault(sort = "planetNode", direction = Sort.Direction.DESC) Pageable pageable) {
        return planetService.getAllPlanets(pageable);
    }

    @GetMapping(value = "/routes/all",consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Route> getAllPlanetRoutes(@PageableDefault(sort = "routeId", direction = Sort.Direction.DESC) Pageable pageable) {
        return planetService.getAllRoutes(pageable);
    }

}
