package com.json.ignore.converter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.MediaType;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * This class represents of XML serialization from response of Spring Web Service
 */
public class FilterXmlConverter extends FilterConverter {

    /**
     * Constructor
     * Add supported media types
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
     * Returns converter mapper
     *
     * @param object {@link FilterClassWrapper} wrapped object which should be serialized
     * @return {@link ConverterMapper} converter mapper
     */
    @Override
    protected ConverterMapper getIgnoreMapper(FilterClassWrapper object) {
        return new ConverterMapper(new XmlMapper(), object.getIgnoreList());
    }
}