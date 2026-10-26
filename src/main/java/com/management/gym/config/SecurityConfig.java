package com.management.gym.config;

import com.management.gym.security.JwtAuthenticatorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    @Lazy
    private JwtAuthenticatorFilter jwtAuthenticatorFilter;

    //Authentication to use APIS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/plans/**").hasAnyRole("Member","Trainer","Boss", "Admin")
                        .requestMatchers(HttpMethod.POST, "/plans/**").hasAnyRole("Admin", "Boss")
                        .requestMatchers(HttpMethod.PATCH, "/plans/**").hasAnyRole("Admin", "Boss")
                        .requestMatchers(HttpMethod.DELETE, "/plans/**").hasAnyRole("Admin", "Boss")

                        .requestMatchers(HttpMethod.GET,"/members/**").hasAnyRole("Trainer","Boss","Admin")
                        .requestMatchers(HttpMethod.POST,"/members/**").hasAnyRole("Trainer","Boss","Admin")
                        .requestMatchers(HttpMethod.PUT,"/members/**").hasAnyRole("Boss","Admin")
                        .requestMatchers(HttpMethod.DELETE,"/members/**").hasAnyRole("Boss","Admin")


                        .requestMatchers(HttpMethod.GET,"/gymclass/**").hasAnyRole("Trainer", "Boss", "Admin")
                        .requestMatchers(HttpMethod.POST,"/gymclass/**").hasAnyRole("Boss", "Admin")
                        .requestMatchers(HttpMethod.PUT,"/gymclass/**").hasAnyRole("Boss", "Admin")
                        .requestMatchers(HttpMethod.DELETE,"/gymclass/**").hasAnyRole("Boss", "Admin")

                        .requestMatchers("/trainer/**").hasAnyRole("Boss","Admin")
                        .anyRequest().authenticated()
        )
                .addFilterBefore(jwtAuthenticatorFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    //Encrypt the passwords
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Authentication manager
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }

}
