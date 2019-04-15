package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.ConverterUtil;
import com.jfilter.EnableJsonFilter;
import com.jfilter.converter.FilterXmlConverter;
import com.jfilter.converter.FilterJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * This class used for register JSON/ and XML message converters
 *
 * <p>Class depends from {@link EnableJsonFilter} annotation
 */
@Configuration
public class FilterRegister implements WebMvcConfigurer {
    private boolean enabled;
    private MappingJackson2HttpMessageConverter defaultJsonConverter;
    private MappingJackson2XmlHttpMessageConverter defaultXmlConverter;
    private ObjectMapper objectMapper;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        /*
         * Important! For enabling filtration, should be specified one of application bean with EnableJsonFilter annotation
         */
        enabled = FilterProvider.isFilterEnabled(webApplicationContext);

        defaultJsonConverter = ConverterUtil.getDefaultJsonConverter(webApplicationContext);
        defaultXmlConverter = ConverterUtil.getDefaultXmlConverter(webApplicationContext);
        objectMapper = ConverterUtil.getBean(webApplicationContext, ObjectMapper.class);
    }

    /**
     * Add converters if {@link EnableJsonFilter} annotation is found in project
     *
     * @param converters list of {@link HttpMessageConverter}
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (enabled) {
            converters.add(new FilterJsonConverter(defaultJsonConverter, objectMapper));
            converters.add(new FilterXmlConverter(defaultXmlConverter));
        }
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Do nothing
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Do nothing
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // Do nothing
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // Do nothing
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Do nothing
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Do nothing
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Do nothing
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Do nothing
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Do nothing
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // Do nothing
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // Do nothing
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        // Do nothing
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Do nothing
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // Do nothing
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // Do nothing
    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}