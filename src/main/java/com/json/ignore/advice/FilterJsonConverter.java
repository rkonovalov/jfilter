package com.json.ignore.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.ignore.IgnoreMapper;
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
    protected IgnoreMapper getIgnoreMapper(FilterClassWrapper object, MediaType mediaType, HttpOutputMessage httpOutputMessage) {
        return new IgnoreMapper(new ObjectMapper(), object.getIgnoreList());
    }
}