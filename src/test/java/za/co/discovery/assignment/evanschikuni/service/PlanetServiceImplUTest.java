package za.co.discovery.assignment.evanschikuni.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import za.co.discovery.assignment.evanschikuni.dtos.request.PlanetRequest;
import za.co.discovery.assignment.evanschikuni.dtos.request.RouteRequest;
import za.co.discovery.assignment.evanschikuni.repository.PlanetNameRepository;
import za.co.discovery.assignment.evanschikuni.repository.RouteRepository;
import za.co.discovery.assignment.evanschikuni.service.impl.PlanetServiceImpl;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlanetServiceImplUTest {

    @Mock
    private PlanetNameRepository planetNameRepository;

    @Mock
    private RouteRepository routesRepository;

    @Test
    void processFileTest() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "planets.xlsx", "application/vnd.ms-excel", new ClassPathResource("planets.xlsx").getInputStream());

        PlanetServiceImpl planetService = new PlanetServiceImpl(planetNameRepository, routesRepository);
        planetService.processFile(file);

        verify(planetNameRepository, times(1)).saveAll(any());
        verify(routesRepository, times(1)).saveAll(any());
    }

    @Test
    void getAllPlanetsTest() {
        PlanetServiceImpl planetService = new PlanetServiceImpl(planetNameRepository, routesRepository);
        planetService.getAllPlanets(Pageable.unpaged());

        verify(planetNameRepository, times(1)).findAll(Pageable.unpaged());
    }

    @Test
    void getAllRoutesTest() {
        PlanetServiceImpl planetService = new PlanetServiceImpl(planetNameRepository, routesRepository);
        planetService.getAllRoutes(Pageable.unpaged());

        verify(routesRepository, times(1)).findAll(Pageable.unpaged());
    }

    @Test
    void savePlanetTest() {
        PlanetRequest planetRequest = new PlanetRequest("Mars", "M");
        PlanetServiceImpl planetService = new PlanetServiceImpl(planetNameRepository, routesRepository);
        planetService.savePlanet(planetRequest);

        verify(planetNameRepository, times(1)).save(any());
    }

    @Test
    void saveRouteTest() {
        RouteRequest routeRequest = new RouteRequest("Earth", "Mars", 100000.0);
        PlanetServiceImpl planetService = new PlanetServiceImpl(planetNameRepository, routesRepository);
        planetService.saveRoute(routeRequest);

        verify(routesRepository, times(1)).save(any());
    }
}
