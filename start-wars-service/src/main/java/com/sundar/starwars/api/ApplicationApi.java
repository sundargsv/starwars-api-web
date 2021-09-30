package com.sundar.starwars.api;


import com.sundar.starwars.api.model.response.Project;
import com.sundar.starwars.api.model.response.StarWarsResponse;
import com.sundar.starwars.common.Constants;
import com.sundar.starwars.service.StarWarsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {Constants.API_BASE_CONTEXT_PATH}, produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class ApplicationApi {

    @Value("${project.name}")
    private String name;

    @Value("${project.version}")
    private String version;

    @Autowired
    private StarWarsService service;

    @GetMapping(path = {"/version"})
    public Project getVersion() {
        log.info("Checking Health point...{}", "Configuration Management Server");

        return Project.builder()
                .name(name)
                .version(version)
                .build();
    }

    @GetMapping(path = {"/details"})
    public StarWarsResponse getStarWarsDetails(@RequestParam String type, @RequestParam String name) {
        log.info("Request Received to get star wars details for type={} | name={}", type, name);
        return service.loadDetails(type, name);
    }

}
