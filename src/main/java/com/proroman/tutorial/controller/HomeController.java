package com.proroman.tutorial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping (path = {"/", "/index", "/public"})
    public String publicPage() {
        return "public page";
    }

    @GetMapping (path = {"/private"})
    public String privatePage() {
        return "private page";
    }
}
