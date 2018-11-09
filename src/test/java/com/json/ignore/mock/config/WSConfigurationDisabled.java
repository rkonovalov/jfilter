package com.json.ignore.mock.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({"com.json.ignore.mock.webservice"})
@EnableWebMvc
@EnableScheduling
public class WSConfigurationDisabled extends WebMvcConfigurerAdapter {

}