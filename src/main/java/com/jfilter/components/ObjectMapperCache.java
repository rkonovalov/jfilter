package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.jfilter.converter.ConverterMapperModifier;
import com.jfilter.converter.MethodParameterDetails;
import com.jfilter.filter.FilterFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ObjectMapper cache
 * <p>
 * This class contains cached list of ObjectMappers which used in {@link FilterConverter#write(Object, MediaType, HttpOutputMessage)}
 *
 */
@Component
public class ObjectMapperCache {
    private ConcurrentMap<MethodParameterDetails, ObjectMapper> items;
    private FilterConfiguration filterConfiguration;

    public ObjectMapperCache() {
        items = new ConcurrentHashMap<>();
    }

    /**
     * Set of FilterConfiguration
     *
     * @param filterConfiguration {@link FilterConfiguration}
     * @return {@link ObjectMapperCache}
     */
    @Autowired
    public ObjectMapperCache setFilterConfiguration(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
        return this;
    }

    /**
     * Find ObjectMapper by MethodParameterDetails
     *
     * @param item {@link MethodParameterDetails}
     * @return {@link ObjectMapper}
     */
    public ObjectMapper findObjectMapper(MethodParameterDetails item) {

        ObjectMapper objectMapper = items.get(item);
        if(objectMapper == null)
            objectMapper = addNewMapper(item);
        return objectMapper;
    }

    /**
     * Add new ObjectMapper in cache
     * <p>
     * Method creates copy of ObjectMapper by media type which listed in filterConfiguration
     * Also if MethodParameterDetails has filter fields method tries to set ignore modifier to ObjectMapper
     *
     * @param item {@link MethodParameterDetails}
     * @return {@link ObjectMapper}
     */
    private ObjectMapper addNewMapper(MethodParameterDetails item) {
        ObjectMapper objectMapper = filterConfiguration.getMapper(item.getMediaType()).copy();

        if (item.getFilterFields() != null)
            setIgnoreModifier(item.getFilterFields(), objectMapper);

        items.put(item, objectMapper);
        return objectMapper;
    }

    /**
     * Method sets serialize modifier in ObjectMapper
     *
     * @param filterFields {@link FilterFields} list of fields which should be hide or keep by ObjectMapper
     * @param objectMapper {@link ObjectMapper}
     */
    private void setIgnoreModifier(FilterFields filterFields, ObjectMapper objectMapper) {
        SerializerFactory factory = BeanSerializerFactory.instance
                .withSerializerModifier(new ConverterMapperModifier(filterFields));
        objectMapper.setSerializerFactory(factory);
    }
}
