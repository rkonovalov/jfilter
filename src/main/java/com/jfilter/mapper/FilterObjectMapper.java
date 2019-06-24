package com.jfilter.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.jfilter.components.FilterConfiguration;

public class FilterObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = -7353120387497261652L;
    private FilterConfiguration filterConfiguration;

    public FilterObjectMapper(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
    }

    @Override
    public ObjectWriter writer() {
        return new FilterObjectWriter(this, getSerializationConfig())
                .setFilterConfiguration(filterConfiguration);
    }

    @Override
    public ObjectWriter writerWithView(Class<?> serializationView) {
        return new FilterObjectWriter(this, getSerializationConfig().withView(serializationView))
                .setFilterConfiguration(filterConfiguration);
    }

    @Override
    public ObjectWriter writer(FilterProvider filterProvider) {
        return new FilterObjectWriter(this, getSerializationConfig().withFilters(filterProvider))
                .setFilterConfiguration(filterConfiguration);
    }


}
