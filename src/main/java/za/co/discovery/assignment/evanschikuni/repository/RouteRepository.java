package za.co.discovery.assignment.evanschikuni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.assignment.evanschikuni.entity.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findByPlanetOriginAndPlanetDestination(String a, String b);
}
