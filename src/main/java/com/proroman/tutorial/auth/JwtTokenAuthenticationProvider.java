package com.proroman.tutorial.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public JwtTokenAuthenticationProvider(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtTokenAuthentication tokenAuthentication = (JwtTokenAuthentication) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(authentication.getName()));
        tokenAuthentication.setAuthenticated(true);
        tokenAuthentication.setUserDetails(userDetails);
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtTokenAuthentication.class);
    }
}
