package com.sundar.starwars.api;

import com.sundar.starwars.api.model.response.Project;
import com.sundar.starwars.api.model.response.StarWarsResponse;
import com.sundar.starwars.exception.NotFound;
import com.sundar.starwars.rest.model.StarWarsFilmServerResponse;
import com.sundar.starwars.service.StarWarsService;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static com.sundar.starwars.TestObjectBuilder.prepareStarWarsResponse;
import static com.sundar.starwars.common.Helper.getAsteriscs;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ApplicationApiTests {

    // bind the above RANDOM_PORT
    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @MockBean
    StarWarsService starWarsService;

    private static final String NOT_FOUND_MESSAGE = "Star wars details not found for the given type=ABCD and name=Sand Crawler";

    @Test
    public void getVersion_returnSuccess() throws Exception {

        ResponseEntity<Project> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/starwars/version").toString(), Project.class);
        assertEquals("startwars-service-test", response.getBody().getName());
        assertEquals("1.0.0", response.getBody().getVersion());
    }

    @Test
    public void getStarWarsDetails_returnSuccess() throws Exception {
        log.info("{} getStarWarsDetails_returnSuccess START {}", getAsteriscs(), getAsteriscs());

        String URL = "http://localhost:" + port + "/starwars/details";

        // prepare the expected response
        StarWarsResponse expectedResponse = prepareStarWarsResponse();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("type", "vehicles")
                .queryParam("name", "Sand Crawler");

        when(starWarsService.loadDetails(anyString(), anyString())).thenReturn(expectedResponse);

        ResponseEntity<StarWarsResponse> actualResponse = restTemplate.getForEntity(
                builder.toUriString(), StarWarsResponse.class);
        assertNotNull(actualResponse.getBody());
        assertEquals(actualResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(expectedResponse.getCount(), actualResponse.getBody().getCount());

        log.info("{} getStarWarsDetails_returnSuccess END {}", getAsteriscs(), getAsteriscs());

    }

    @Test
    public void getStarWarsDetails_returnNotFound() throws Exception {
        log.info("{} getStarWarsDetails_returnNotFound START {}", getAsteriscs(), getAsteriscs());

        String URL = "http://localhost:" + port + "/starwars/details";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("type", "ABCD")
                .queryParam("name", "Sand Crawler");

        when(starWarsService.loadDetails(anyString(), anyString())).thenThrow(new NotFound(NOT_FOUND_MESSAGE));

        ResponseEntity<StarWarsResponse> actualResponse = restTemplate.getForEntity(
                builder.toUriString(), StarWarsResponse.class);
        assertNotNull(actualResponse.getBody());
        assertEquals(actualResponse.getStatusCode(), HttpStatus.NOT_FOUND);

        log.info("{} getStarWarsDetails_returnNotFound END {}", getAsteriscs(), getAsteriscs());

    }

    @Test
    public void getStarWarsDetails_returnNull() throws Exception {
        log.info("{} getStarWarsDetails_returnNull START {}", getAsteriscs(), getAsteriscs());

        String URL = "http://localhost:" + port + "/starwars/details";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("type", "people")
                .queryParam("name", "Luke");

        when(starWarsService.loadDetails(anyString(), anyString())).thenReturn(null);

        ResponseEntity<StarWarsResponse> actualResponse = restTemplate.getForEntity(
                builder.toUriString(), StarWarsResponse.class);
        assertNull(actualResponse.getBody());

        log.info("{} getStarWarsDetails_returnNull END {}", getAsteriscs(), getAsteriscs());

    }
}
