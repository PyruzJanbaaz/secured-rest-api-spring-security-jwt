package com.pyruz.rest.secured.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("Access-Control-Allow-Origin", "*")
                .allowedHeaders("Access-Control-Allow-Headers", "Content-Type")
                .allowedHeaders("Access-Control-Max-Age", "1")
                .allowedHeaders("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE", "OPTIONS")
                .allowedHeaders("Access-Control-Allow-Origin", "*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS");
    }
}