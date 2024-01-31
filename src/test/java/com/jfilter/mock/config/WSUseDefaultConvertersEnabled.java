package com.jfilter.mock.config;

import com.jfilter.EnableJsonFilter;
import com.jfilter.components.FilterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableJsonFilter
@ComponentScan({"com.jfilter.mock.webservice", "com.jfilter"})
public class WSUseDefaultConvertersEnabled implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        extendMessageConverters(converters);
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void configure(FilterConfiguration filterConfiguration) {
        filterConfiguration.setUseDefaultConverters(true);
    }


}