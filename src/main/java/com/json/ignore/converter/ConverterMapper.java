package com.json.ignore.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

import java.util.List;
import java.util.Map;

public class ConverterMapper {
    private final ObjectMapper mapper;
    private final Map<Class, List<String>> ignoreList;

    public ConverterMapper(ObjectMapper mapper, Map<Class, List<String>> ignoreList) {
        this.mapper = mapper;
        this.ignoreList = ignoreList;
        setIgnoreModifier();
    }

    private void setIgnoreModifier() {
        SerializerFactory factory = BeanSerializerFactory.instance
                .withSerializerModifier(new ConverterMapperModifier(ignoreList));
        mapper.setSerializerFactory(factory);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
