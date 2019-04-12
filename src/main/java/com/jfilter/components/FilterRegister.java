package com.jfilter.components;

import com.jfilter.ConverterUtil;
import com.jfilter.EnableJsonFilter;
import com.jfilter.converter.FilterXmlConverter;
import com.jfilter.converter.FilterJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * This class used for register JSON/ and XML message converters
 *
 * <p>Class depends from {@link EnableJsonFilter} annotation
 */
@Configuration
public class FilterRegister extends WebMvcConfigurationSupport {
    private boolean enabled;

    private MappingJackson2HttpMessageConverter defaultJsonConverter;
    private MappingJackson2XmlHttpMessageConverter defaultXmlConverter;


    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        /*
         * Important! For enabling filtration, should be specified one of application bean with EnableJsonFilter annotation
         */
        enabled = FilterProvider.isFilterEnabled(webApplicationContext);

        defaultJsonConverter = ConverterUtil.getDefaultJsonConverter(webApplicationContext);
        defaultXmlConverter = ConverterUtil.getDefaultXmlConverter(webApplicationContext);
    }



    /**
     * Add converters if {@link EnableJsonFilter} annotation is found in project
     * @param converters list of {@link HttpMessageConverter}
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (enabled) {
            converters.add(new FilterJsonConverter(defaultJsonConverter));
            converters.add(new FilterXmlConverter(defaultXmlConverter));
        }
        super.configureMessageConverters(converters);
    }
}