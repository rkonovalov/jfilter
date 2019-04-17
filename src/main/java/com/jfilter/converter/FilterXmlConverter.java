package com.jfilter.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jfilter.components.FilterMapperConfig;
import org.springframework.http.MediaType;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * This class represents of XML serialization from response of Spring Web Service
 */
public class FilterXmlConverter extends FilterConverter {
    private FilterMapperConfig filterMapperConfig;

    /**
     * Creates a new instance of the {@link FilterXmlConverter} class.
     *
     * And specify supported media types
     * @param filterMapperConfig {@link FilterMapperConfig} filter configuration
     */
    public FilterXmlConverter(FilterMapperConfig filterMapperConfig) {
        this.filterMapperConfig = filterMapperConfig;
        getSupportedMediaTypes().addAll(Arrays.asList(
                MediaType.APPLICATION_XML,
                new MediaType("application", "xml", Charset.defaultCharset()),
                new MediaType("application", "*+xml"),
                new MediaType("text", "xml")
        ));
    }

    /**
     * Returns XML object mapper
     * If envrimoment is Spring Boot, trying to get XmlMapper from MappingJackson2XmlHttpMessageConverter class which already created by Spring Boot.
     * Otherwise get XmlMapper from Jackson2ObjectMapperBuilder
     * @return {@link XmlMapper}
     */
    @Override
    public ObjectMapper getObjectMapper() {
        return filterMapperConfig.getDefaultXmlMapper().copy();
    }
}