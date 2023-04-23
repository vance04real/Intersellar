package za.co.discovery.assignment.evanschikuni.entity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long routeId;

    private String planetOrigin;

    private String planetDestination;

    private Double distance;

}
