package com.jfilter.converter;

import com.jfilter.components.FilterConfiguration;
import com.jfilter.components.FilterObjectMapperBuilder;

public class SerializationConfig {
    private boolean defaultSerializers;
    private boolean dateTimeModule;

    public SerializationConfig() {
        this.defaultSerializers = true;
        this.dateTimeModule = true;
    }

    /**
     * Get using of default Serializers state
     *
     * @return true if adding is enabled, otherwise false
     */
    public boolean isDefaultSerializersEnabled() {
        return defaultSerializers;
    }

    /**
     * Enabling/disabling using of default Serializers
     *
     * @param defaultSerializers if true {@link FilterObjectMapperBuilder} will add default Serializers to ObjectMappers, otherwise not
     * @return instance of {@link FilterConfiguration}
     */
    public SerializationConfig enableDefaultSerializers(boolean defaultSerializers) {
        this.defaultSerializers = defaultSerializers;
        return this;
    }

    /**
     * Get using of JavaDateTimeModule state
     *
     * @return true if using is enabled, otherwise false
     */
    public boolean isDateTimeModuleEnabled() {
        return dateTimeModule;
    }

    /**
     * Enabling/disabling using of JavaDateTimeModule in ObjectMappers
     *
     * @param dateTimeModule if true {@link FilterObjectMapperBuilder} will add JavaTimeModule to ObjectMappers, otherwise not
     * @return instance of {@link FilterConfiguration}
     */
    public SerializationConfig enableDateTimeModule(boolean dateTimeModule) {
        this.dateTimeModule = dateTimeModule;
        return this;
    }
}
