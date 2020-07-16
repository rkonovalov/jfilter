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
