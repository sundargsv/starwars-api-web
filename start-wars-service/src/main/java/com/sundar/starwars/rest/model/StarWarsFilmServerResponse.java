package com.sundar.starwars.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StarWarsFilmServerResponse {

    private String title;

    @JsonProperty(value = "episode_id")
    private Integer episodeId;

    @JsonProperty(value = "opening_crawl")
    private String openingCrawl;

    private String director;

    private String producer;

    @JsonProperty(value = "release_date")
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
    private String url;
}
