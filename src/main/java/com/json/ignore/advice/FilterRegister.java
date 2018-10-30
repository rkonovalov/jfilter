package com.json.ignore.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class FilterRegister extends WebMvcConfigurationSupport {
    private boolean enabled;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        /*
         * Important! For enabling filtration, should be specified one of application bean with EnableJsonFilter annotation
         */
        enabled = FilterProvider.isFilterEnabled(webApplicationContext);
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (enabled)
            converters.add(new FilterJsonConverter());
            converters.add(new FilterXmlConverter());
        super.configureMessageConverters(converters);
    }
}