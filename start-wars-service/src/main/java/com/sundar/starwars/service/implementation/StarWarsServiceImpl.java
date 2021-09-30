package com.sundar.starwars.service.implementation;

import com.sundar.starwars.api.model.response.StarWarsFilmResponse;
import com.sundar.starwars.api.model.response.StarWarsResponse;
import com.sundar.starwars.exception.NotFound;
import com.sundar.starwars.rest.model.StarWarsFilmServerResponse;
import com.sundar.starwars.rest.wrapper.RestServiceHelper;
import com.sundar.starwars.service.StarWarsDtoMapper;
import com.sundar.starwars.service.StarWarsService;
import com.sundar.starwars.rest.model.StarWarsAPIServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class StarWarsServiceImpl implements StarWarsService {

    @Value("${api.sw.url}")
    private String swBaseUrl;

    private static final String FILMS_PATH = "/films/";

    @Autowired
    private RestServiceHelper restServiceHelper;

    @Autowired
    private StarWarsDtoMapper mapper;x

    @Override
    public StarWarsResponse loadDetails(String type, String name) {

        //1. fetch star wars data by type and search value
        StarWarsAPIServerResponse serverResponse = restServiceHelper.getSWDetails(type.toLowerCase(), name.toLowerCase());

        //2. check if results not available
        if (serverResponse.getCount() == 0)
            throw new NotFound(String.format("Star wars details not found for the given type=%s and name=%s", type, name));

        //3. fetch film details
        List<StarWarsFilmResponse> filmResponses = serverResponse.getResults().parallelStream()
                .flatMap(starWarsAPIResult -> starWarsAPIResult.getFilms()
                        .stream().map(s -> loadFilm(findFilmId(s)))
                ).collect(Collectors.toList());

        //4. prepare the response
        return StarWarsResponse.builder()
                .type(type)
                .count(serverResponse.getCount())
                .name(name)
                .films(filmResponses)
                .build();
    }

    public StarWarsFilmResponse loadFilm(String id) {
        return mapper.toStarWarsFilmResponse(restServiceHelper.getSWFilmsDetails(id));

    }

    public String findFilmId(String filmUrl){
        return filmUrl.replace(swBaseUrl + FILMS_PATH, "").replace("/", "");
    }
}