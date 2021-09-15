package com.proroman.tutorial.controller;

import com.proroman.tutorial.auth.JwtTokenUtil;
import com.proroman.tutorial.auth.TokenRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.security.InvalidParameterException;

@RestController
public class TestController {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public TestController(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/getToken")
    @PermitAll
    public String getToken(@RequestBody TokenRequest tokenRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRequest.getUsername());
        if (passwordEncoder.matches(tokenRequest.getPassword(), userDetails.getPassword())) {
            return jwtTokenUtil.generateToken(tokenRequest);
        }
        throw new InvalidParameterException("User not found!");
    }

    @GetMapping("/user")
    @RolesAllowed("ROLE_USER")
    public String userPage() {
        return "user page";
    }

    @GetMapping("/admin")
    @RolesAllowed("ROLE_ADMIN")
    public String adminPage() {
        return "admin page";
    }
}
