package com.mmgon.jjajuka.global.config;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 관련 설정을 위한 구성 클래스
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private String frontendHost = "http://localhost:3000";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(frontendHost)
                .allowedOrigins("https://dev.jjajuka.site")
                .allowedOrigins("http://dev.jjajuka.site")
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Access-Token")
                .exposedHeaders("Access-Token")
                .allowCredentials(true);
    }


}