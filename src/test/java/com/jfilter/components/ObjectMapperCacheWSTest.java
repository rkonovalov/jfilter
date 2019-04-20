package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.converter.MethodParameterDetails;
import com.jfilter.filter.FilterFields;
import com.jfilter.mock.config.WSConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import static org.junit.Assert.*;

@Component
public class ObjectMapperCacheWSTest {
    private ObjectMapperCache  objectMapperCache;
    private FilterConfiguration filterConfiguration;

    @Autowired
    public ObjectMapperCacheWSTest setObjectMapperCache(ObjectMapperCache objectMapperCache) {
        this.objectMapperCache = objectMapperCache;
        return this;
    }

    @Autowired
    public ObjectMapperCacheWSTest setFilterConfiguration(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
        return this;
    }

    @Before
    public void init() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED2, this);
        filterConfiguration.setEnabled(true);
    }

    @Test
    public void testNotNull() {
        assertNotNull(objectMapperCache);
    }

    @Test
    public void testFindObjectMapper() {
        MethodParameterDetails methodParameterDetails = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, new FilterFields());
        ObjectMapper objectMapper = objectMapperCache.findObjectMapper(methodParameterDetails);
        assertNotNull(objectMapper);
    }

    @Test
    public void testFindObjectMapperAlreadyCreated() {
        MethodParameterDetails methodParameterDetails = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, new FilterFields());
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, new FilterFields());
        ObjectMapper objectMapper = objectMapperCache.findObjectMapper(methodParameterDetails);
        ObjectMapper objectMapper2 = objectMapperCache.findObjectMapper(methodParameterDetails2);

        assertEquals(objectMapper, objectMapper2);
    }

}
