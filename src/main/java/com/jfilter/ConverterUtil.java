package com.jfilter;

import org.springframework.beans.BeansException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;

public class ConverterUtil {

    public static <T> T getBean(WebApplicationContext webApplicationContext, Class<T> tClass) {
        try {
            return webApplicationContext.getBean(tClass);
        } catch (BeansException e) {
            return null;
        }
    }

    public static MappingJackson2HttpMessageConverter getDefaultJsonConverter(WebApplicationContext webApplicationContext) {
        return getBean(webApplicationContext, MappingJackson2HttpMessageConverter.class);
    }

    public static MappingJackson2XmlHttpMessageConverter getDefaultXmlConverter(WebApplicationContext webApplicationContext) {
        return getBean(webApplicationContext, MappingJackson2XmlHttpMessageConverter.class);
    }
}
