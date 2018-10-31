package com.json.ignore.advice;

import com.json.ignore.EnableJsonFilter;
import com.json.ignore.converter.FilterJsonConverter;
import com.json.ignore.converter.FilterXmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * This class used for register JSON/ and XML message converters
 * Class depends from {@link EnableJsonFilter} annotation
 */
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

    /**
     * Add converters if {@link EnableJsonFilter} annotation is found in project
     * @param converters list of {@link HttpMessageConverter}
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (enabled) {
            converters.add(new FilterJsonConverter());
            converters.add(new FilterXmlConverter());
        }
        super.configureMessageConverters(converters);
    }
}