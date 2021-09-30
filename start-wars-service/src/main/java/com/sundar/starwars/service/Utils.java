package com.sundar.starwars.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Utils {

    @Value("${api.sw.url}")
    private String swBaseUrl;

    /**
     * helper to check if file exists for the given file path
     * */
    public Boolean isExists(String path) {
        return new File(path).exists();
    }

    /**
     * helper is to remove the base url and to have only a resource entity path (Adding HATEOS href link in the film response)
     * */
    public  List<String> toHateOSHrefLink(List<String> urls){
        return urls.parallelStream().map(url -> url.replace(swBaseUrl, "")).collect(Collectors.toList());
    }
}
