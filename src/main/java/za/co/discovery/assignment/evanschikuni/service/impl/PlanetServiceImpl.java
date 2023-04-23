package za.co.discovery.assignment.evanschikuni.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.discovery.assignment.evanschikuni.dtos.request.PlanetRequest;
import za.co.discovery.assignment.evanschikuni.dtos.request.RouteRequest;
import za.co.discovery.assignment.evanschikuni.dtos.response.ApiResponse;
import za.co.discovery.assignment.evanschikuni.entity.PlanetName;
import za.co.discovery.assignment.evanschikuni.entity.Route;
import za.co.discovery.assignment.evanschikuni.repository.PlanetNameRepository;
import za.co.discovery.assignment.evanschikuni.repository.RouteRepository;
import za.co.discovery.assignment.evanschikuni.service.api.PlanetService;
import za.co.discovery.assignment.evanschikuni.utils.enums.ExcelSheetType;

import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService {


    private final PlanetNameRepository planetNameRepository;

    private final RouteRepository routesRepository;

    @Override
    public void processFile(MultipartFile file) {
        Workbook workbook;

        try {
            if (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xls")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                throw new NotSupportedException("File format not supported!");
            }

            EnumSet.allOf(ExcelSheetType.class).forEach(
                    type ->{
                        Sheet wbSheet = workbook.getSheet(type.getValue());
                        switch (type.name()){

                            case "PLANET":
                                savePlanets(wbSheet);
                                break;

                            case "ROUTE":
                                saveRoute(wbSheet);
                                break;

                            case "TRAFFIC":
                                //TODO logic for persisting traffic to be added in v2
                                break;
                            default:
                                break;

                        }
                    }
            );

        }catch (Exception e){
            log.error("Error processing file", e);
        }

    }

    @Override
    public Page<PlanetName> getAllPlanets(Pageable pageable) {
        log.info("Retrieving all planets with pagination");
        return planetNameRepository.findAll(pageable);
    }

    @Override
    public Page<Route> getAllRoutes(Pageable pageable) {
        log.info("Retrieving all routes with pagination");
        return routesRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<ApiResponse> savePlanet(PlanetRequest planetRequest) {
        log.info("Saving planet: {}", planetRequest.getPlanetName());

        planetNameRepository.save(PlanetName.builder()
                .planetName(planetRequest.getPlanetName())
                .planetNode(planetRequest.getPlanetNode()).build());

        return ResponseEntity.ok(ApiResponse.builder().message("Success").build());

    }

    @Override
    public ResponseEntity<ApiResponse> saveRoute(RouteRequest routeRequest) {
        log.info("Saving route from {} to {}", routeRequest.getOrigin(), routeRequest.getDestination());

        routesRepository.save(Route.builder()
                .planetOrigin(routeRequest.getOrigin())
                .planetDestination(routeRequest.getDestination())
                .build());

        return ResponseEntity.ok(ApiResponse.builder().message("Success").build());
    }
    private void saveRoute(Sheet sheet){
        log.info("Saving routes from sheet {}", sheet.getSheetName());

        List<Route> routes = new ArrayList<Route>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Route route = new Route();
            for (Cell cell: sheet.getRow(i)) {
                switch (cell.getColumnIndex()) {
                    case 0:
                        route.setRouteId((long) cell.getNumericCellValue());
                        break;
                    case 1:
                        route.setPlanetOrigin(cell.getStringCellValue());
                        break;
                    case 2:
                        route.setPlanetDestination(cell.getStringCellValue());
                        break;
                    case 3:
                        route.setDistance(cell.getNumericCellValue());
                        break;
                    default:
                        break;
                }
            }
            routes.add(route);
        }
        routesRepository.saveAll(routes);
    }

    private void savePlanets(Sheet sht){
        List<PlanetName> planets= new ArrayList<>();
        for (int i = 1; i < sht.getPhysicalNumberOfRows(); i++) {
            PlanetName planet = new PlanetName();
            for (Cell cell: sht.getRow(i)) {
                switch (cell.getColumnIndex()) {
                    case 0:
                        planet.setPlanetNode(cell.getStringCellValue());
                        break;
                    case 1:
                        planet.setPlanetName(cell.getStringCellValue());
                        break;
                    default:
                        break;
                }
            }
            planets.add(planet);
        }
        planetNameRepository.saveAll(planets);
    }
}
