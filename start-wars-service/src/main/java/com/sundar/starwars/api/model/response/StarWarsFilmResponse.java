package com.sundar.starwars.api.model.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StarWarsFilmResponse {

    private String title;

    private Integer episodeId;

    private String openingCrawl;

    private String director;

    private String producer;

    private String releaseDate;

    // characters
    private List<String> characters;
    // planets
    private List<String> planets;
    // starships
    private List<String> starships;
    // vehicles
    private List<String> vehicles;
    // species
    private List<String> species;

    private String created;
    private String edited;
}
