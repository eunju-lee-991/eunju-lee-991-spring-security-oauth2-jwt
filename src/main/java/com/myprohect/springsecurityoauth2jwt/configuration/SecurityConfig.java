package com.myprohect.springsecurityoauth2jwt.configuration;

import com.auth0.jwt.JWTVerifier;
import com.myprohect.springsecurityoauth2jwt.filter.JwtAuthorizationFilter;
import com.myprohect.springsecurityoauth2jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // WebSecurityConfigurerAdapter를 구현할 때.... 스프링 시큐리티 필터가 스프링 필터 체인에 등록
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTVerifier jwtVerifier;
    private final UserService userService;

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic().disable() // Authorization header에 id, pw 담아가는 방식
                // => 우리는 Authorization header에 토큰을 넣을 거니까 사용 안 함
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 인증 방식도 사용 안 함
                .and()
                .formLogin().disable()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration)))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(authenticationConfiguration), userRepository))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(authenticationConfiguration), jwtVerifier, userService))
                .authorizeRequests()
                .antMatchers("/api/test/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/api/test/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/api/test/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/test").permitAll()
                .anyRequest().permitAll();

        return httpSecurity.build();
    }
}
