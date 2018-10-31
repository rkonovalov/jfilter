package com.json.ignore.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.List;
import java.util.Map;

/**
 * Class represents additional functionality of standard ObjectMapper class
 */
public class ConverterMapper {
    private final ObjectMapper mapper;
    private final Map<Class, List<String>> ignoreList;

    /**
     * Creates a new instance of the {@link ConverterMapper} class.
     *
     * @param mapper  could be instance of {@link ObjectMapper} or {@link XmlMapper}
     * @param ignoreList {@link Map} map of fields which could be ignored
     */
    public ConverterMapper(ObjectMapper mapper, Map<Class, List<String>> ignoreList) {
        this.mapper = mapper;
        this.ignoreList = ignoreList;
        setIgnoreModifier();
    }

    /**
     * Creates instance of {@link SerializerFactory}  with configured withSerializerModifier value.
     * Also see {@link ConverterMapperModifier}
     */
    private void setIgnoreModifier() {
        SerializerFactory factory = BeanSerializerFactory.instance
                .withSerializerModifier(new ConverterMapperModifier(ignoreList));
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
