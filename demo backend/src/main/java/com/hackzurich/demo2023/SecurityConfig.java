package com.hackzurich.demo2023;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("ALL")
@EnableWebSecurity
@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
        .httpBasic().disable()
        .authorizeHttpRequests().requestMatchers("/**").permitAll()
        .anyRequest().authenticated();
        return http.build();
    }
}