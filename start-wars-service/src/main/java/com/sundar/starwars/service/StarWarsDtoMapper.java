package com.sundar.starwars.service;

import com.sundar.starwars.api.model.response.StarWarsFilmResponse;
import com.sundar.starwars.rest.model.StarWarsFilmServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StarWarsDtoMapper {

    @Autowired
    private Utils utils;

    public StarWarsFilmResponse toStarWarsFilmResponse(StarWarsFilmServerResponse serverResponse) {
        return StarWarsFilmResponse.builder()
                .title(serverResponse.getTitle())
                .director(serverResponse.getDirector())
                .producer(serverResponse.getProducer())
                .episodeId(serverResponse.getEpisodeId())
                .openingCrawl(serverResponse.getOpeningCrawl())
                .releaseDate(serverResponse.getReleaseDate())
                .characters((serverResponse.getCharacters() != null && !serverResponse.getCharacters().isEmpty())? utils.toHateOSHrefLink(serverResponse.getCharacters()): null)
                .planets((serverResponse.getCharacters() != null && !serverResponse.getCharacters().isEmpty())? utils.toHateOSHrefLink(serverResponse.getPlanets()): null)
                .starships((serverResponse.getCharacters() != null && !serverResponse.getCharacters().isEmpty())? utils.toHateOSHrefLink(serverResponse.getStarships()): null)
                .vehicles((serverResponse.getCharacters() != null && !serverResponse.getCharacters().isEmpty())? utils.toHateOSHrefLink(serverResponse.getVehicles()): null)
                .species((serverResponse.getCharacters() != null && !serverResponse.getCharacters().isEmpty())? utils.toHateOSHrefLink(serverResponse.getSpecies()): null)
                .created(serverResponse.getCreated())
                .edited(serverResponse.getEdited())
                .build();
    }


}
