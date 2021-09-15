package com.proroman.tutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("user").password("{noop}user").roles("USER").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("admin").password("{noop}admin").roles("ADMIN").build());
        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("noop", NoOpPasswordEncoder.getInstance());

        return new DelegatingPasswordEncoder("noop", encoders);
    }
}
