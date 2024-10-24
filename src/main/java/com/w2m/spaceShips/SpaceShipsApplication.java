package com.w2m.spaceShips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author javiloguai
 */
@SpringBootApplication
@EnableCaching
@EnableJpaRepositories
public class SpaceShipsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceShipsApplication.class, args);
    }

}
