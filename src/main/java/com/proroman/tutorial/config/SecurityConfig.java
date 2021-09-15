package com.proroman.tutorial.config;

import com.proroman.tutorial.auth.JwtTokenAuthenticationFilter;
import com.proroman.tutorial.auth.JwtTokenAuthenticationProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@ComponentScan(basePackages = "com.proroman.tutorial.auth")
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenAuthenticationFilter authenticationFilter;
    private final JwtTokenAuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtTokenAuthenticationFilter authenticationFilter,
                          JwtTokenAuthenticationProvider authenticationProvider) {
        this.authenticationFilter = authenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
