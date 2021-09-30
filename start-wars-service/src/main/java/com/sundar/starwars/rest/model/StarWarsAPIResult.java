package com.sundar.starwars.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarWarsAPIResult {

    private String name;
    private List<String> films;

    /*private String model;
    private String manufacturer;

    @JsonProperty("cost_in_credits")
    private String costInCredits;

    private String length;

    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;

    private String crew;
    private String passengers;

    @JsonProperty("cargo_capacity")
    private String cargoCapacity;

    private String consumables;

    @JsonProperty("vehicle_class")
    private String vehicleClass;

    private List<String> pilots;
    private String created;
    private String edited;
    private String url;*/
}
