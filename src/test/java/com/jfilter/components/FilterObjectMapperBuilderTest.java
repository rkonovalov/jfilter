package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.ser.BasicSerializerFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Serializers;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jfilter.converter.ConverterMapperModifier;
import com.jfilter.converter.SerializationConfig;
import com.jfilter.filter.FilterFields;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterObjectMapperBuilderTest {
    private ObjectMapper filterObjectMapperTrue;
    private ObjectMapper filterObjectMapperFalse;

    private boolean modifierExist(Iterable<BeanSerializerModifier> modifiers, Class modifier) {
        while (modifiers.iterator().hasNext()) {
            if (modifiers.iterator().next().getClass().equals(modifier))
                return true;
        }
        return false;
    }

    private boolean serializerExist(Iterable<Serializers> serializers, Class serializer) {
        while (serializers.iterator().hasNext()) {
            if (serializers.iterator().next().getClass().equals(serializer))
                return true;
        }
        return false;
    }

    @Before
    public void init() {
        SerializationConfig configTrue = new SerializationConfig();
        SerializationConfig configFalse = new SerializationConfig()
                .enableDateTimeModule(false)
                .enableDefaultSerializers(false);

        filterObjectMapperTrue = new FilterObjectMapperBuilder(new ObjectMapper())
                .withFilterFields(new FilterFields())
                .withSetSerializationConfig(configTrue)
                .build();

        filterObjectMapperFalse = new FilterObjectMapperBuilder(new ObjectMapper())
                .withFilterFields(null)
                .withSetSerializationConfig(configFalse)
                .build();
    }

    private SerializerFactoryConfig getFactoryConfig(ObjectMapper objectMapper) {
        BasicSerializerFactory serializationConfig = (BasicSerializerFactory) objectMapper.getSerializerFactory();
        return serializationConfig.getFactoryConfig();
    }

    private boolean registeredModule(ObjectMapper objectMapper, String moduleName) {
        try {
            return objectMapper.getRegisteredModuleIds().stream().anyMatch(m -> ((String) m).contains(moduleName));
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Test
    public void testConfigureModifierTrue() {
        SerializerFactoryConfig serializerFactoryConfig = getFactoryConfig(filterObjectMapperTrue);

        boolean modifierExist = modifierExist(serializerFactoryConfig.serializerModifiers(), ConverterMapperModifier.class);
        assertTrue(modifierExist);
    }

    @Test
    public void testConfigureSerializersTrue() {
        SerializerFactoryConfig serializerFactoryConfig = getFactoryConfig(filterObjectMapperTrue);

        boolean jdk8SerializerExist = serializerExist(serializerFactoryConfig.serializers(), Jdk8Serializers.class);
        assertTrue(jdk8SerializerExist);

        boolean simpleSerializerExist = serializerExist(serializerFactoryConfig.serializers(), SimpleSerializers.class);
        assertTrue(simpleSerializerExist);
    }

    @Test
    public void testRegisteredModulesTrue() {
        boolean timeModuleExist = registeredModule(filterObjectMapperTrue, JavaTimeModule.class.getName());
        assertTrue(timeModuleExist);
    }


    @Test
    public void testConfigureModifierFalse() {
        SerializerFactoryConfig serializerFactoryConfig = getFactoryConfig(filterObjectMapperFalse);

        boolean modifierExist = modifierExist(serializerFactoryConfig.serializerModifiers(), ConverterMapperModifier.class);
        assertFalse(modifierExist);
    }

    @Test
    public void testConfigureSerializersFalse() {
        SerializerFactoryConfig serializerFactoryConfig = getFactoryConfig(filterObjectMapperFalse);

        boolean jdk8SerializerExist = serializerExist(serializerFactoryConfig.serializers(), Jdk8Serializers.class);
        assertFalse(jdk8SerializerExist);

        boolean simpleSerializerExist = serializerExist(serializerFactoryConfig.serializers(), SimpleSerializers.class);
        assertFalse(simpleSerializerExist);
    }

    @Test
    public void testRegisteredModulesFalse() {
        boolean timeModuleExist = registeredModule(filterObjectMapperFalse, JavaTimeModule.class.getName());
        assertFalse(timeModuleExist);
    }

}
