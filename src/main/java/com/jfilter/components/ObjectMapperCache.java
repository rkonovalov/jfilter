package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.converter.MethodParameterDetails;
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
        if (objectMapper == null)
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

        //Make copy of configured ObjectMapper
        ObjectMapper configuredObjectMapper = filterConfiguration.getMapper(item.getMediaType()).copy();

        //Build modified objectMapper
        ObjectMapper objectMapper =  new FilterObjectMapper(configuredObjectMapper)
                .withFilterFields(item.getFilterFields())
                .enableDateTimeModule(filterConfiguration.isDateTimeModule())
                .enableDefaultSerializers(filterConfiguration.isDateTimeModule())
                .build();

        items.put(item, objectMapper);
        return objectMapper;
    }
}
