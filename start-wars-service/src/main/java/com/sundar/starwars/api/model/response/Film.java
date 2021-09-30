package com.sundar.starwars.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    private String title;

    private Integer episodeId;

    private String openingCrawl;

    private String director;

    private String producer;

    private String releaseDate;

    // characters
    // planets
    // starships
    // vehicles
    // species

    private String created;
    private String edited;
    private String url;


}
