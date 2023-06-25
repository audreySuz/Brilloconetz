package com.brillo.loginbackend.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**","/swagger-ui.html","/configuration/**",
                        "/swagger-resources/**","/v3/api-docs/**","/webjars/**","/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .exceptionHandling()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .build();
    }


}