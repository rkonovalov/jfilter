package com.jfilter.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * This class represents of XML serialization from response of Spring Web Service
 */
public class FilterXmlConverter extends FilterConverter {

    /**
     * Creates a new instance of the {@link FilterXmlConverter} class.
     *
     * And specify supported media types
     */
    public FilterXmlConverter() {
        getSupportedMediaTypes().addAll(Arrays.asList(
                MediaType.APPLICATION_XML,
                new MediaType("application", "xml", Charset.defaultCharset()),
                new MediaType("application", "*+xml"),
                new MediaType("text", "xml")
        ));
    }

    /**
     * Returns XML object mapper
     *
     * @return {@link XmlMapper}
     */
    @Override
    public ObjectMapper getObjectMapper() {
        return Jackson2ObjectMapperBuilder.xml().build();
    }
}