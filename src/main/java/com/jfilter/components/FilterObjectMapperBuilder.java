package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.jfilter.converter.MixinFilter;
import com.jfilter.converter.SerializationConfig;
import com.jfilter.filter.FilterFields;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class allows to add SerializerModifier to ObjectMapper
 */
public class FilterObjectMapperBuilder {
    private ObjectMapper objectMapper;
    private FilterFields filterFields;
    private SerializationConfig serializationConfig;

    public FilterObjectMapperBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public FilterObjectMapperBuilder withFilterFields(FilterFields filterFields) {
        this.filterFields = filterFields;
        return this;
    }

    public FilterObjectMapperBuilder withSetSerializationConfig(SerializationConfig serializationConfig) {
        this.serializationConfig = serializationConfig;
        return this;
    }

    /**
     * Build configured ObjectMapper
     *
     * @return {@link ObjectMapper}
     */
    public ObjectMapper build() {
        //Add mixin filter
        objectMapper.addMixIn(Object.class, MixinFilter.class);
        objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter("com.jfilter.converter.MixinFilter", new MixinFilter(filterFields)));

        //Set dateTimeModule if option is enabled
        if (serializationConfig.isDateTimeModuleEnabled()) {
            //Add JavaTimeModule to fix issue with LocalDate/LocalDateTime serialization
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
            javaTimeModule.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
            javaTimeModule.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
            javaTimeModule.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
            javaTimeModule.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
            javaTimeModule.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
            objectMapper.registerModule(javaTimeModule);
            objectMapper.registerModule(new Jdk8Module());
            objectMapper.findAndRegisterModules();
        }

        return objectMapper;
    }
}
