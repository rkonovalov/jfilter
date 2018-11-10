package com.jfilter.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jfilter.filter.FilterFields;

import java.util.Map;

/**
 * Class represents additional functionality of standard ObjectMapper class
 */
public class ConverterMapper {
    private final ObjectMapper mapper;
    private final FilterFields filterFields;

    /**
     * Creates a new instance of the {@link ConverterMapper} class.
     *
     * @param mapper  could be instance of {@link ObjectMapper} or {@link XmlMapper}
     * @param filterFields {@link Map} map of fields which could be ignored
     */
    public ConverterMapper(ObjectMapper mapper, FilterFields filterFields) {
        this.mapper = mapper;
        this.filterFields = filterFields;
        setIgnoreModifier();
    }

    /**
     * Creates instance of {@link SerializerFactory}  with configured withSerializerModifier value.
     * Also see {@link ConverterMapperModifier}
     */
    private void setIgnoreModifier() {
        SerializerFactory factory = BeanSerializerFactory.instance
                .withSerializerModifier(new ConverterMapperModifier(filterFields));
        mapper.setSerializerFactory(factory);
    }

    /**
     * Returns configured object mapper
     * @return could be instance of {@link ObjectMapper} or {@link XmlMapper}
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}
