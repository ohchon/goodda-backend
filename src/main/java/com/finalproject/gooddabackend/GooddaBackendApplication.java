package com.finalproject.gooddabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
@PropertySource("file:/home/ubuntu/application.properties")//ec2용
//@PropertySource("file:/Users/sunghyunoh/Desktop/application.properties")//로컬용
public class GooddaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GooddaBackendApplication.class, args);
    }

}
