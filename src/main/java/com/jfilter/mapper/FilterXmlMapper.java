package com.jfilter.mapper;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jfilter.components.FilterConfiguration;

@SuppressWarnings("Duplicates")
public class FilterXmlMapper extends XmlMapper {
    private static final long serialVersionUID = 3983476365887537283L;
    private FilterConfiguration filterConfiguration;

    public FilterXmlMapper(FilterConfiguration filterConfiguration) {
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
        return FilterObjectWriter.congfiguredWriter(this, getSerializationConfig().withFilters(filterProvider), filterConfiguration);
    }


}
