package com.sundar.starwars.rest.wrapper;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sundar.starwars.caching.StarWarsCacheKey;
import com.sundar.starwars.exception.InternalServerError;
import com.sundar.starwars.rest.model.StarWarsFilmServerResponse;
import com.sundar.starwars.rest.model.StarWarsAPIServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RestServiceHelper {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.sw.url}")
    private String swApiUrl;

    // TODO: 18/09/21 The below caching is an implementation of fallBack
    // TODO: 18/09/21 This can be improved well using a redis like distributed caching with cache miss and cache hit controls
    private static Map<StarWarsCacheKey, StarWarsAPIServerResponse> cachedObject = new HashMap<>();
    private static Map<String, StarWarsFilmServerResponse> cachedFilmObject = new HashMap<>();


    private static final String FILMS_URL = "/films";

    @HystrixCommand(fallbackMethod = "getSWDetailsFallBack",
            commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    public StarWarsAPIServerResponse getSWDetails(String entityTpe, String searchValue){

        //1. prepare url to load details
        String url = String.format("%s/%s?search=%s", swApiUrl, entityTpe, searchValue);
        log.info("Fetching Star wars details for the given URL={}", url);
        try{

            //2. rest call to sw api
            ResponseEntity<StarWarsAPIServerResponse> response = restTemplate.getForEntity(
                    url, StarWarsAPIServerResponse.class
            );

            log.info("Retrieved Star wars data Status={}", response.getStatusCodeValue());

            //3. caching data explicitly to an internal hashMap for now, later it can be cached with external caching db like redis
            if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody().getCount() > 0)
                cache(entityTpe, searchValue, response.getBody());

            return response.getBody();
        }catch (HttpClientErrorException hce){
            log.error("Error in fetching Star wars details for the given Type={} | name={} | Message={}", entityTpe, searchValue, hce.getLocalizedMessage());
            throw new InternalServerError("Error in fetching star wars details", hce.getCause());
        }
    }

    public StarWarsAPIServerResponse getSWDetailsFallBack(String entityTpe, String searchValue){
        log.info("FallBack - Fetching Star wars cached details for the given type={} | name={}", entityTpe, searchValue);

        //1. prepare a search key used to cache sw details
        StarWarsCacheKey searchKey = StarWarsCacheKey.builder()
                .type(entityTpe)
                .name(searchValue)
                .build();

        //2. check if cache key available and fetch cached data
        if (cachedObject.containsKey(searchKey)){
            return cachedObject.get(searchKey);
        }

        //3. if cache key not available, build no data found rest response
        return StarWarsAPIServerResponse.builder()
                .count(0)
                .build();
    }

    @HystrixCommand(fallbackMethod = "getSWFilmsDetailsFallBack",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            })
    public StarWarsFilmServerResponse getSWFilmsDetails(String id){

        //1. prepare url to load details
        String url = String.format("%s%s/%s", swApiUrl, FILMS_URL, id);
        log.info("Fetching Star wars film data for the given URL={}", url);
        try{

            //2. rest call to sw api
            ResponseEntity<StarWarsFilmServerResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET , null, StarWarsFilmServerResponse.class
            );

            log.info("Retrieved Star wars film data Status={}", response.getStatusCodeValue());

            //3. caching data explicitly to an internal hashMap for now, later it can be cached with external caching db like redis
            if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody().getTitle() != null)
                cacheFilms(id, response.getBody());

            return response.getBody();
        }catch (HttpClientErrorException hce){
            log.error("Error in fetching Star wars film data for the given id={} | Message={}", id, hce.getLocalizedMessage());
            throw new InternalServerError("Error in fetching star wars film data", hce.getCause());
        }
    }

    public StarWarsFilmServerResponse getSWFilmsDetailsFallBack(String id){
        log.info("FallBack - Fetching cached Star wars films details for the given id={}", id);

        if (cachedFilmObject.containsKey(id)){
            return cachedFilmObject.get(id);
        }

        return StarWarsFilmServerResponse.builder().build();
    }

    private void cache(String entityTpe, String searchValue, StarWarsAPIServerResponse response){

        log.info("cache - caching Star wars cached details for the given type={} | name={}", entityTpe, searchValue);
        cachedObject.put(StarWarsCacheKey.builder()
                .type(entityTpe)
                .name(searchValue)
                .build(), response);
    }

    private void cacheFilms(String id, StarWarsFilmServerResponse response){
        log.info("cache - caching Star wars film cached details for the given id={}", id);
        cachedFilmObject.put(id, response);
    }
}
