package com.jfilter.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.jfilter.components.FilterConfiguration;

@SuppressWarnings("Duplicates")
public class FilterObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = -7353120387497261652L;
    private FilterConfiguration filterConfiguration;

    public FilterObjectMapper(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
    }

    @Override
    public ObjectWriter writer() {
        return FilterObjectWriter.congfiguredWriter(this, filterConfiguration);
    }

    @Override
    public ObjectWriter writerWithView(Class<?> serializationView) {
        return FilterObjectWriter.congfiguredWriter(this, serializationView, filterConfiguration);
    }

    @Override
    public ObjectWriter writer(FilterProvider filterProvider) {
        return FilterObjectWriter.congfiguredWriter(this, filterProvider, filterConfiguration);
    }
}
