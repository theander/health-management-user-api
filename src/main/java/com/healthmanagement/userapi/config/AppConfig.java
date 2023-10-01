package com.healthmanagement.userapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class AppConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://54.80.8.44:3000")
                        .allowedMethods("GET","POST","DELETE","OPTIONS")
                        .allowedHeaders("authorization","contentType","Origin","Content-Type","X-Auth-Token")
                        .exposedHeaders("Authorization");
            }
        };
    }
}
