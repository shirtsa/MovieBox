package bg.movieboxproductions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity httpSecurity,
      JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

    return httpSecurity
            //Doesn't need csrf protection because there is csrf in the controller that comes the requests
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize ->
                    //All Get Request for productions are permit
                authorize.requestMatchers(HttpMethod.GET, "/productions/**").permitAll()
                        .requestMatchers("/").permitAll()
                        //For the creation of productions will need authenticated user
                    .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();

  }
}
