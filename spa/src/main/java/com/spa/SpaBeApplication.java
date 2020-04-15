package com.spa;

import com.spa.config.SpaBeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(SpaBeProperties.class)
public class SpaBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaBeApplication.class, args);
    }

}
