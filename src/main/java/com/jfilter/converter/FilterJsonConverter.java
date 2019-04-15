package com.jfilter.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * This class represents of JSON serialization from response of Spring Web Service
 */
public class FilterJsonConverter extends FilterConverter {

    private MappingJackson2HttpMessageConverter jsonConverter;

    /**
     * Creates a new instance of the {@link FilterJsonConverter} class.
     *
     * And specify supported media types
     */
    public FilterJsonConverter(MappingJackson2HttpMessageConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
        getSupportedMediaTypes().addAll(Arrays.asList(
                MediaType.APPLICATION_JSON,
                new MediaType("application", "json", Charset.defaultCharset()),
                new MediaType("application", "*+json")
        ));
    }

    /**
     * Returns JSON ObjectMapper
     * If envrimoment is Spring Boot, trying to get ObjectMapper from MappingJackson2HttpMessageConverter class which already created by Spring Boot.
     * Otherwise get ObjectMapper from Jackson2ObjectMapperBuilder
     *
     * @return {@link ObjectMapper}
     */
    @Override
    public ObjectMapper getObjectMapper() {
        return jsonConverter != null ? jsonConverter.getObjectMapper() : Jackson2ObjectMapperBuilder.json().build();
    }
}