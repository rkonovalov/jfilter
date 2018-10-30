package com.json.ignore.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Arrays;

public class FilterJsonConverter extends FilterConverter {

    public FilterJsonConverter() {
        getSupportedMediaTypes().addAll(Arrays.asList(
                MediaType.APPLICATION_JSON,
                new MediaType("application", "json", Charset.defaultCharset()),
                new MediaType("application", "*+json")
        ));
    }

    @Override
    protected ConverterMapper getIgnoreMapper(FilterClassWrapper object, MediaType mediaType, HttpOutputMessage httpOutputMessage) {
        return new ConverterMapper(new ObjectMapper(), object.getIgnoreList());
    }
}