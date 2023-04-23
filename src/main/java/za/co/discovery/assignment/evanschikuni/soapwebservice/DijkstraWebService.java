package za.co.discovery.assignment.evanschikuni.soapwebservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import za.co.discovery.GetRouteRequest;
import za.co.discovery.GetRouteResponse;
import za.co.discovery.assignment.evanschikuni.service.api.DijkstraService;

import java.util.List;

@Slf4j
@Endpoint
@RequiredArgsConstructor
public class DijkstraWebService {

    private final DijkstraService dijkstraService;

    @PayloadRoot(namespace = "http://discovery.co.za", localPart = "getRouteRequest")
    @ResponsePayload
    public GetRouteResponse getShortestPath(@RequestPayload GetRouteRequest requestDto){
        log.info("Received the following {} and {}", requestDto.getStartPlanet(),requestDto.getStopPlanet());

        var response = new GetRouteResponse();
        List<String> paths = dijkstraService.shortestPath(requestDto.getStartPlanet(), requestDto.getStopPlanet());

        paths.forEach(s -> response.getPath().add(s));
        return response;
    }
}
