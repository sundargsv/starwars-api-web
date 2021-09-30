package com.sundar.starwars;

import com.sundar.starwars.common.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
@Slf4j
public class StarWarsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarWarsServiceApplication.class, args);
		log.info("Star wars API Service started ..... At={}", Helper.getLocalUtcDateTime());
	}
}
