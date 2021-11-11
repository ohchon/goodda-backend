package com.finalproject.gooddabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GooddaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GooddaBackendApplication.class, args);
    }

}
