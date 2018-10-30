package com.json.ignore.mock.config;
  
import com.json.ignore.EnableJsonFilter;
import com.json.ignore.advice.FilterConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@ComponentScan({"com.json.ignore", "com.json.ignore.advice", "com.json.ignore.mock.webservice"})
@EnableWebMvc
@EnableJsonFilter
public class WSConfigurationEnabled extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FilterConverter());
        super.configureMessageConverters(converters);
    }
}