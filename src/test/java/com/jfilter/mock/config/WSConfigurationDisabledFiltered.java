package com.jfilter.mock.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages ={"com.jfilter", "com.jfilter.components", "com.jfilter.mock.webservice"},
        excludeFilters = {@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value = WSConfigurationEnabled.class)})
@EnableWebMvc
public class WSConfigurationDisabledFiltered implements WebMvcConfigurer {

}