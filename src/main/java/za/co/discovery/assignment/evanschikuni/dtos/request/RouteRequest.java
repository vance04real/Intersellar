package za.co.discovery.assignment.evanschikuni.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {

    private String origin;

    private String destination;

    private Double distance;
}
