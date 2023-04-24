package za.co.discovery.assignment.evanschikuni.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import za.co.discovery.assignment.evanschikuni.dtos.request.DistanceSearchRequest;
import za.co.discovery.assignment.evanschikuni.service.api.DijkstraService;
import za.co.discovery.assignment.evanschikuni.service.api.PlanetService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final DijkstraService dijkstraService;

    private final PlanetService planetService;
    @GetMapping("/")
    String homePage(){
        return "index";
    }

    @GetMapping("/upload")
    String uploadPage(){
        return "upload";
    }

    @PostMapping("/api/distance/get")
    String homePage(@ModelAttribute("routeDto") DistanceSearchRequest request, Model model){
        List<String> path = dijkstraService.shortestPath(request.getStart().toUpperCase(), request.getEnd().toUpperCase());
        if(path == null){
            model.addAttribute("ErrorMessage","Failed to calculate distance");
            return "index";
        }
        model.addAttribute("PlanetsResponse",path);
        model.addAttribute("Distance",dijkstraService.getDistance(path));

        return "index";
    }

    @PostMapping(value = "/api/file", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String routeFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("msg", "Please select a file to upload");
            return "upload";
        }
        planetService.processFile(file);
        model.addAttribute("msg",String.format("File uploaded successfully : %s",file.getOriginalFilename() ));
        return "redirect:/";

    }
}
