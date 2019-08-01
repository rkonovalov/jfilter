package com.jfilter.components;

import com.jfilter.EnableJsonFilter;
import com.jfilter.mapper.FilterObjectMapper;
import com.jfilter.mapper.FilterXmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import java.util.List;

/**
 * This class used for register FilterConverter in Spring converter list
 *
 * <p>Class depends from {@link EnableJsonFilter} annotation
 */
@Configuration
public class FilterRegister implements WebMvcConfigurer {
    private FilterConfiguration filterConfiguration;


    @Autowired
    public FilterRegister(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Do nothing
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

    /**
     * Remove default Spring MessageConverters MappingJackson2HttpMessageConverter and MappingJackson2XmlHttpMessageConverter
     *
     * @param converters list of HttpMessageConverter
     */
    private void removeDefaultConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter ||
                converter instanceof MappingJackson2XmlHttpMessageConverter);
    }

    /**
     * Add filter converters if filtration is enabled
     * If isUseDefaultConverters enabled, JFilter uses default message converters MappingJackson2HttpMessageConverter
     * and MappingJackson2XmlHttpMessageConverter
     * <p>
     * Otherwise uses FilterConverter
     *
     * @param converters list of {@link HttpMessageConverter}
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (filterConfiguration.isEnabled()) {
            if (filterConfiguration.isUseDefaultConverters()) {
                removeDefaultConverters(converters);
                converters.add(0, new MappingJackson2HttpMessageConverter(new FilterObjectMapper(filterConfiguration)));
                converters.add(0, new MappingJackson2XmlHttpMessageConverter(new FilterXmlMapper(filterConfiguration)));
            } else
                converters.add(0, new FilterConverter(filterConfiguration));
        }
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