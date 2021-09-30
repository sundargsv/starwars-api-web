package com.sundar.starwars;

import com.sundar.starwars.api.model.response.StarWarsFilmResponse;
import com.sundar.starwars.api.model.response.StarWarsResponse;
import com.sundar.starwars.rest.model.StarWarsAPIResult;
import com.sundar.starwars.rest.model.StarWarsAPIServerResponse;
import com.sundar.starwars.rest.model.StarWarsFilmServerResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestObjectBuilder {

    public static StarWarsResponse prepareStarWarsResponse() {
        return StarWarsResponse.builder()
                .type("vehicles")
                .count(1)
                .name("Sand Crawler")
                .films(prepareFilms())
                .build();
    }

    public static List<StarWarsFilmResponse> prepareFilms() {
        List<StarWarsFilmResponse> films = new ArrayList<>();
        films.add(StarWarsFilmResponse.builder()
                .title("A New Hope")
                .director("George Lucas")
                .producer("Gary Kurtz, Rick McCallum")
                .episodeId(4)
                .build());
        films.add(StarWarsFilmResponse.builder()
                .title("Attack of the Clones")
                .director("George Lucas")
                .producer("Rick McCallum")
                .episodeId(2)
                .build());
        return films;
    }

    // the below is the API Server response mock for vehicles type
    public static StarWarsAPIServerResponse prepareStarWarsAPIServerResponse_success() {
        return StarWarsAPIServerResponse.builder()
                .count(1)
                .results(Arrays.asList(StarWarsAPIResult.builder()
                        .name("Sand Crawler")
                        .films(Arrays.asList("https://sw.local.test/api/film/1", "https://sw.local.test/api/film/5"))
                        .build()))
                .build();
    }

    public static StarWarsAPIServerResponse prepareStarWarsAPIServerResponse_failure() {
        return StarWarsAPIServerResponse.builder()
                .count(0)
                .build();
    }

    public static StarWarsFilmServerResponse prepareStarWarsFilmServerResponse() {
        return StarWarsFilmServerResponse.builder()
                .title("A New Hope")
                .director("George Lucas")
                .producer("Gary Kurtz, Rick McCallum")
                .episodeId(4)
                .build();
    }
}
