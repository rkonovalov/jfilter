package com.jfilter.mock.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SuppressWarnings("ALL")
@Configuration
@ComponentScan({"com.jfilter.mock.webservice"})
@EnableWebMvc
public class WSConfigurationDisabled implements WebMvcConfigurer {

}