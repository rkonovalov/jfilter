package com.json.ignore.converter;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.List;
import java.util.Map;

public class ConverterMapperModifier extends BeanSerializerModifier {
    private final Map<Class, List<String>> ignoreList;

    public ConverterMapperModifier(Map<Class, List<String>> ignoreList) {
        this.ignoreList = ignoreList;
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        removeFields(beanDesc, beanProperties);
        return super.changeProperties(config, beanDesc, beanProperties);
    }

    private void removeFieldsByClass(Class clazz, List<BeanPropertyWriter> beanProperties) {
        if(ignoreList.containsKey(clazz)) {
            List<String> ignores = ignoreList.get(clazz);
            beanProperties.removeIf(beanProperty -> ignores.contains(beanProperty.getName()));
        }
    }

    private void removeFields(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        removeFieldsByClass(beanDesc.getType().getRawClass(), beanProperties);
        removeFieldsByClass(null, beanProperties);
        removeFieldsByClass(void.class, beanProperties);
    }
}
