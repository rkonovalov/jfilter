package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

@Component
public class FilterMapperConfig {
    private ObjectMapper defaultJsonMapper;
    private ObjectMapper defaultXmlMapper;

    public FilterMapperConfig() {
    }

    public ObjectMapper getDefaultJsonMapper() {
        return defaultJsonMapper != null ? defaultJsonMapper : Jackson2ObjectMapperBuilder.json().build();
    }

    public FilterMapperConfig setDefaultJsonMapper(ObjectMapper defaultJsonMapper) {
        this.defaultJsonMapper = defaultJsonMapper;
        return this;
    }

    public ObjectMapper getDefaultXmlMapper() {
        return defaultXmlMapper != null ? defaultXmlMapper : Jackson2ObjectMapperBuilder.xml().build();
    }

    public FilterMapperConfig setDefaultXmlMapper(ObjectMapper defaultXmlMapper) {
        this.defaultXmlMapper = defaultXmlMapper;
        return this;
    }
}
