package com.sundar.starwars.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarWarsAPIServerResponse {

    private Integer count;
    private String next;
    private String previous;
    private List<StarWarsAPIResult> results;
}
