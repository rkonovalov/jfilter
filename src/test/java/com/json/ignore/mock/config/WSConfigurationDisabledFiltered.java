package com.json.ignore.mock.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages ={"com.json.ignore", "com.json.ignore.advice", "com.json.ignore.mock.webservice"},
        excludeFilters = {@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value = WSConfigurationEnabled.class)})
@EnableWebMvc
@EnableScheduling
public class WSConfigurationDisabledFiltered extends WebMvcConfigurerAdapter {

}