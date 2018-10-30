package com.json.ignore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import java.util.List;
import java.util.Map;

public class IgnoreMapper {
    private final ObjectMapper mapper;
    private final Map<Class, List<String>> ignoreList;

    public IgnoreMapper(ObjectMapper mapper, Map<Class, List<String>> ignoreList) {
        this.mapper = mapper;
        this.ignoreList = ignoreList;
        setIgnoreModifier();
    }

    private void setIgnoreModifier() {
        SerializerFactory factory = BeanSerializerFactory.instance
                .withSerializerModifier(new IgnoreMapperModifier(ignoreList));
        mapper.setSerializerFactory(factory);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
