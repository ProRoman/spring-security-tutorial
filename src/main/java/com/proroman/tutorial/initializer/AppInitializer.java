package com.proroman.tutorial.initializer;

import com.proroman.tutorial.config.SecurityConfig;
import com.proroman.tutorial.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                SecurityConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        }; }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
