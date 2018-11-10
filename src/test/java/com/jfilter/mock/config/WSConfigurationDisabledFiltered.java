package com.jfilter.mock.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages ={"com.jfilter", "com.jfilter.advice", "com.jfilter.mock.webservice"},
        excludeFilters = {@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value = WSConfigurationEnabled.class)})
@EnableWebMvc
public class WSConfigurationDisabledFiltered extends WebMvcConfigurerAdapter {

}