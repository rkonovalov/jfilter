package com.json.ignore.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * This class represents of JSON serialization from response of Spring Web Service
 */
public class FilterJsonConverter extends FilterConverter {

    /**
     * Constructor
     * Add supported media types
     */
    public FilterJsonConverter() {
        getSupportedMediaTypes().addAll(Arrays.asList(
                MediaType.APPLICATION_JSON,
                new MediaType("application", "json", Charset.defaultCharset()),
                new MediaType("application", "*+json")
        ));
    }

    /**
     * Returns converter mapper
     *
     * @param object {@link FilterClassWrapper} wrapped object which should be serialized
     * @return {@link ConverterMapper} converter mapper
     */
    @Override
    protected ConverterMapper getIgnoreMapper(FilterClassWrapper object) {
        return new ConverterMapper(new ObjectMapper(), object.getIgnoreList());
    }
}