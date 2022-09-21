package com.opencourse.path.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import com.opencourse.path.handlers.ExceptionHandlerFilter;
import com.opencourse.path.security.CustomAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationFilter filter;
    private final ExceptionHandlerFilter handlerFilter;
    @Bean
    public SecurityFilterChain authorisationServerFilterChain(HttpSecurity http)throws Exception{
        http.csrf().disable();
        http.cors().disable();
        http.httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().mvcMatchers(HttpMethod.GET,"/api/v1/path/**").permitAll()
        .mvcMatchers("/api/v1/path").hasAuthority("TEACHER")
        
        .mvcMatchers("/api/v1/cetififcate").authenticated()
        
        .mvcMatchers(HttpMethod.GET,"/api/v1/element").permitAll()
        .mvcMatchers("/api/v1/element").hasAuthority("TEACHER")

        .mvcMatchers("/api/v1/project/finish").hasAuthority("MENTOR")
        .mvcMatchers(HttpMethod.GET,"/api/v1/project/**").permitAll()
        .mvcMatchers("/api/v1/project/**").hasAuthority("TEACHER")

        .mvcMatchers("/api/v1/subscription/access").permitAll()
        .mvcMatchers("/api/v1/subscription").authenticated()

        .mvcMatchers(HttpMethod.GET, "/api/v1/topic").permitAll()
        .mvcMatchers("/api/v1/topic").hasAuthority("ADMIN")

        .and().addFilterBefore(filter, AnonymousAuthenticationFilter.class)
        .addFilterBefore(handlerFilter, CustomAuthenticationFilter.class);
        
        return http.build();
    }

}
