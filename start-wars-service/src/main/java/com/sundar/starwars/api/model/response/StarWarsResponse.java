package com.sundar.starwars.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarWarsResponse {

    private String type;
    private Integer count;
    private String name;
    private List<StarWarsFilmResponse> films;

}
