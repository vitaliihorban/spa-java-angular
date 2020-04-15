package com.spa.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("spa")
public class SpaBeProperties {

    private Security security = new Security();

    @Setter
    @Getter
    static class Security{
        private String username;
        private String password;
        private String role;
    }
}
