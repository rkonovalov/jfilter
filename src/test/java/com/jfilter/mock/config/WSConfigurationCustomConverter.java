package com.jfilter.mock.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.EnableJsonFilter;
import com.jfilter.components.FilterConfiguration;
import com.jfilter.mapper.FilterObjectMapper;
import com.jfilter.mapper.FilterXmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableJsonFilter
@ComponentScan({"com.jfilter.mock.webservice"})
public class WSConfigurationCustomConverter extends WebMvcConfigurerAdapter {

    public class CustomJsonConverter extends MappingJackson2HttpMessageConverter {
        public CustomJsonConverter(ObjectMapper objectMapper) {
            super(objectMapper);
        }
    }

    public class CustomXmlConverter extends MappingJackson2XmlHttpMessageConverter {
        public CustomXmlConverter(ObjectMapper objectMapper) {
            super(objectMapper);
        }
    }

    @Autowired
    public void configure(FilterConfiguration filterConfiguration) {
        //Add custom message converters
        filterConfiguration.withCustomConverter(new CustomJsonConverter(new FilterObjectMapper(filterConfiguration)));
        filterConfiguration.withCustomConverter(new CustomXmlConverter(new FilterXmlMapper(filterConfiguration)));
    }
}