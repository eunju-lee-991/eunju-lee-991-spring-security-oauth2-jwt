package com.myprohect.springsecurityoauth2jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.myprohect.springsecurityoauth2jwt.constant.JwtConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BasicConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTVerifier jwtVerifier() {
        Algorithm algorithm = Algorithm.HMAC512(JwtConstant.SECRET_KEY);
        return JWT.require(algorithm).build();
    }
}
