package za.co.discovery.assignment.evanschikuni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.assignment.evanschikuni.entity.PlanetName;

@Repository
public interface PlanetNameRepository extends JpaRepository<PlanetName,Character> {
}
