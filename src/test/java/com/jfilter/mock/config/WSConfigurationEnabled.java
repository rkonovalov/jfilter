package com.jfilter.mock.config;

import com.jfilter.EnableJsonFilter;
import com.jfilter.components.FilterRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Configuration
@EnableJsonFilter
@ComponentScan({"com.jfilter.mock.webservice", "com.jfilter"})
public class WSConfigurationEnabled implements WebMvcConfigurer {
    @Autowired
    private FilterRegister filterRegister;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (filterRegister != null)
            filterRegister.configureMessageConverters(converters);
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new MappingJackson2XmlHttpMessageConverter());
        extendMessageConverters(converters);
    }
}