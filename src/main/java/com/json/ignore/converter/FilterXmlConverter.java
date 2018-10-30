package com.json.ignore.converter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Arrays;

public class FilterXmlConverter extends FilterConverter {

    public FilterXmlConverter() {
        getSupportedMediaTypes().addAll(Arrays.asList(
                MediaType.APPLICATION_XML,
                new MediaType("application", "xml", Charset.defaultCharset()),
                new MediaType("application", "*+xml"),
                new MediaType("text", "xml")
        ));
    }

    @Override
    protected ConverterMapper getIgnoreMapper(FilterClassWrapper object, MediaType mediaType, HttpOutputMessage httpOutputMessage) {
        return new ConverterMapper(new XmlMapper(), object.getIgnoreList());
    }
}