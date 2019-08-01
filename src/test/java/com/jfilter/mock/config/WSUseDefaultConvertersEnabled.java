package com.jfilter.mock.config;

import com.jfilter.EnableJsonFilter;
import com.jfilter.components.FilterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableJsonFilter
@ComponentScan({"com.jfilter.mock.webservice"})
public class WSUseDefaultConvertersEnabled extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

    public void configure(@Autowired FilterConfiguration filterConfiguration) {
        filterConfiguration.setUseDefaultConverters(true);
    }
}