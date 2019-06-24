package com.jfilter.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.jfilter.components.FilterConfiguration;
import com.jfilter.converter.FilterClassWrapper;

import java.io.IOException;

public class FilterObjectWriter extends ObjectWriter {
    private static final long serialVersionUID = -5795887415761168717L;
    private FilterConfiguration filterConfiguration;

    public static FilterObjectWriter congfiguredWriter(ObjectMapper mapper, FilterConfiguration filterConfiguration) {
        return new FilterObjectWriter(mapper, mapper.getSerializationConfig())
                .setFilterConfiguration(filterConfiguration);
    }

    public static FilterObjectWriter congfiguredWriter(ObjectMapper mapper, SerializationConfig config, FilterConfiguration filterConfiguration) {
        return new FilterObjectWriter(mapper, config)
                .setFilterConfiguration(filterConfiguration);
    }

    public static FilterObjectWriter congfiguredWriter(ObjectMapper mapper, Class<?> serializationView, FilterConfiguration filterConfiguration) {
        return congfiguredWriter(mapper, mapper.getSerializationConfig().withView(serializationView), filterConfiguration);
    }

    public static FilterObjectWriter congfiguredWriter(ObjectMapper mapper, FilterProvider filterProvider, FilterConfiguration filterConfiguration) {
        return congfiguredWriter(mapper, mapper.getSerializationConfig().withFilters(filterProvider), filterConfiguration);
    }


    protected FilterObjectWriter(ObjectMapper mapper, SerializationConfig config) {
        super(mapper, config);
    }

    protected FilterObjectWriter(ObjectWriter base, SerializationConfig config,
                                 GeneratorSettings genSettings, Prefetch prefetch) {
        super(base, config, genSettings, prefetch);
    }

    @Override
    public ObjectWriter forType(JavaType rootType) {
        return new FilterObjectWriter(this, _config, _generatorSettings, _prefetch.forRootType(this, rootType));
    }

    public ObjectWriter with(PrettyPrinter pp) {
        return new FilterObjectWriter(this, _config, _generatorSettings.with(pp), _prefetch);
    }

    public FilterConfiguration getFilterConfiguration() {
        return filterConfiguration;
    }

    public FilterObjectWriter setFilterConfiguration(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
        return this;
    }

    @Override
    public void writeValue(JsonGenerator gen, Object value) throws IOException {
        if (value instanceof FilterClassWrapper) {
            FilterClassWrapper wrapper = (FilterClassWrapper) value;

            ObjectMapper objectMapper = filterConfiguration.getObjectMapperCache()
                    .findObjectMapper(wrapper.getMethodParameterDetails());

            objectMapper.writeValue(gen, wrapper.getObject());
        } else
            super.writeValue(gen, value);
    }

}
