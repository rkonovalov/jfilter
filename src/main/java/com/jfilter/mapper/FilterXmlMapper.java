package com.jfilter.mapper;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jfilter.components.FilterConfiguration;

public class FilterXmlMapper extends XmlMapper {
    private static final long serialVersionUID = 3983476365887537283L;
    private FilterObjectWriter objectWriter;
    private FilterConfiguration filterConfiguration;

    public FilterXmlMapper(FilterConfiguration filterConfiguration) {
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
