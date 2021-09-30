package com.sundar.starwars.service;

import com.sundar.starwars.api.model.response.StarWarsResponse;

public interface StarWarsService {

    StarWarsResponse loadDetails(String type, String name);
}
