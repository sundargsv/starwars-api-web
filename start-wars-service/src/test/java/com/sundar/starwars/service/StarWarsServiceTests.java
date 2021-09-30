package com.sundar.starwars.service;


import com.sundar.starwars.TestObjectBuilder;
import com.sundar.starwars.api.model.response.StarWarsResponse;
import com.sundar.starwars.exception.InternalServerError;
import com.sundar.starwars.exception.NotFound;
import com.sundar.starwars.rest.model.StarWarsAPIServerResponse;
import com.sundar.starwars.rest.model.StarWarsFilmServerResponse;
import com.sundar.starwars.rest.wrapper.RestServiceHelper;
import com.sundar.starwars.service.implementation.StarWarsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static com.sundar.starwars.common.Helper.getAsteriscs;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class StarWarsServiceTests {

    @Mock
    RestServiceHelper restServiceHelper;

    @InjectMocks
    StarWarsServiceImpl starWarsService;

    @Mock
    StarWarsDtoMapper mapper;

    private static final String TEST_BASE_URL = "https://sw.local.test/api";
    private static final String SERVER_ERROR_MESSAGE = "Error in fetching star wars details";


    @Test
    public void c1_loadDetails_returnSuccess() {
        log.info("{} c1_loadDetails_returnSuccess START {}", getAsteriscs(), getAsteriscs());

        ReflectionTestUtils.setField(starWarsService, "swBaseUrl", TEST_BASE_URL);

        // prepare the expected response
        StarWarsAPIServerResponse starWarsAPIServerResponse = TestObjectBuilder.prepareStarWarsAPIServerResponse_success();
        StarWarsFilmServerResponse starWarsFilmServerResponse = TestObjectBuilder.prepareStarWarsFilmServerResponse();
        StarWarsResponse expectedResponse = TestObjectBuilder.prepareStarWarsResponse();

        when(restServiceHelper.getSWDetails(anyString(), anyString())).thenReturn(starWarsAPIServerResponse);
        when(restServiceHelper.getSWFilmsDetails(anyString())).thenReturn(starWarsFilmServerResponse);

        StarWarsResponse actualResponse = starWarsService.loadDetails("vehicles", "Sand Crawler");
        assertEquals(expectedResponse.getCount(), actualResponse.getCount());
        assertEquals(expectedResponse.getType(), actualResponse.getType());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getFilms().size(), actualResponse.getFilms().size());

        log.info("{} c1_loadDetails_returnSuccess END {}", getAsteriscs(), getAsteriscs());
    }

    @Test(expected = NotFound.class)
    public void c2_loadDetails_returnNotFound() {
        log.info("{} c2_loadDetails_returnNotFound START {}", getAsteriscs(), getAsteriscs());

        ReflectionTestUtils.setField(starWarsService, "swBaseUrl", TEST_BASE_URL);

        // prepare the server response
        StarWarsAPIServerResponse starWarsAPIServerResponse = TestObjectBuilder.prepareStarWarsAPIServerResponse_failure();

        // mock the response to send count as 0
        when(restServiceHelper.getSWDetails(anyString(), anyString())).thenReturn(starWarsAPIServerResponse);

        StarWarsResponse actualResponse = starWarsService.loadDetails("ABCD", "Sand Crawler");

        log.info("{} c2_loadDetails_returnNotFound END {}", getAsteriscs(), getAsteriscs());
    }

    @Test(expected = InternalServerError.class)
    public void c3_loadDetails_returnServerError() {
        log.info("{} c3_loadDetails_returnServerError START {}", getAsteriscs(), getAsteriscs());

        ReflectionTestUtils.setField(starWarsService, "swBaseUrl", TEST_BASE_URL);

        // prepare the mock server error response
        when(restServiceHelper.getSWDetails(anyString(), anyString())).
                thenThrow(new InternalServerError(SERVER_ERROR_MESSAGE, new Throwable("Timeout")));;

        StarWarsResponse actualResponse = starWarsService.loadDetails("ABCD", "");

        log.info("{} c3_loadDetails_returnServerError END {}", getAsteriscs(), getAsteriscs());
    }
}
