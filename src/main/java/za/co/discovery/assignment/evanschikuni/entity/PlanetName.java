package za.co.discovery.assignment.evanschikuni.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "planet_names")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanetName {
    @Id
    @Column(length = 3)
    private String planetNode;
    private String planetName;
}
