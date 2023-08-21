package com.myprohect.springsecurityoauth2jwt.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", configuration);
//        System.out.println("cors filter");
//
//        return new CorsFilter(source);
//    }
//
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5000")
                .allowCredentials(true)
                .allowedMethods("OPTIONS", "GET", "POST", "PATCH", "DELETE")
                .allowedHeaders("*");
    }
}