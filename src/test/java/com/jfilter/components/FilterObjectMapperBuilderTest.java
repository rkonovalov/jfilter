package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.ser.BasicSerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.jfilter.converter.SerializationConfig;
import com.jfilter.filter.FilterFields;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterObjectMapperBuilderTest {
    private ObjectMapper filterObjectMapperTrue;
    private ObjectMapper filterObjectMapperFalse;

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
                .enableDateTimeModule(false);

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
    public void testConfigureSerializersTrue() {
        SerializerFactoryConfig serializerFactoryConfig = getFactoryConfig(filterObjectMapperTrue);


        boolean simpleSerializerExist = serializerExist(serializerFactoryConfig.serializers(), SimpleSerializers.class);
        assertTrue(simpleSerializerExist);
    }

    @Test
    public void testRegisteredModulesTrue() {
        String javaTimeModuleName = PackageVersion.VERSION.getArtifactId();
        boolean timeModuleExist = registeredModule(filterObjectMapperTrue, javaTimeModuleName);
        assertTrue(timeModuleExist);
    }

    @Test
    public void testConfigureSerializersFalse() {
        SerializerFactoryConfig serializerFactoryConfig = getFactoryConfig(filterObjectMapperFalse);
        boolean timeModuleExist = registeredModule(filterObjectMapperFalse, JavaTimeModule.class.getName());
        assertFalse(timeModuleExist);
        boolean simpleSerializerExist = serializerExist(serializerFactoryConfig.serializers(), SimpleSerializers.class);
        assertFalse(simpleSerializerExist);
    }

    @Test
    public void testRegisteredModulesFalse() {
        boolean timeModuleExist = registeredModule(filterObjectMapperFalse, JavaTimeModule.class.getName());
        assertFalse(timeModuleExist);
    }

}
