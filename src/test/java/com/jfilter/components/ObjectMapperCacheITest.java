package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.converter.MethodParameterDetails;
import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.config.WSConfigurationHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Component
public class ObjectMapperCacheITest {
    private ObjectMapperCache  objectMapperCache;
    private FilterConfiguration filterConfiguration;
    private MethodParameter methodParameter;

    @Autowired
    public ObjectMapperCacheITest setObjectMapperCache(ObjectMapperCache objectMapperCache) {
        this.objectMapperCache = objectMapperCache;
        return this;
    }

    @Autowired
    public ObjectMapperCacheITest setFilterConfiguration(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
        return this;
    }

    @Before
    public void init() throws Exception {
        WSConfigurationHelper.instance(WSConfigurationHelper.Instance.FILTER_ENABLED2, this);
        filterConfiguration.setEnabled(true);
        methodParameter = MockMethods.mockIgnoreSettingsMethod(null);
    }

    @Test
    public void testNotNull() {
        assertNotNull(objectMapperCache);
    }

    @Test
    public void testFindObjectMapper() {
        MethodParameterDetails methodParameterDetails = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, new FilterFields());
        ObjectMapper objectMapper = objectMapperCache.findObjectMapper(methodParameterDetails);
        assertNotNull(objectMapper);
    }

    @Test
    public void testFindObjectMapperAlreadyCreated() {
        MethodParameterDetails methodParameterDetails = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, new FilterFields());
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, new FilterFields());
        ObjectMapper objectMapper = objectMapperCache.findObjectMapper(methodParameterDetails);
        ObjectMapper objectMapper2 = objectMapperCache.findObjectMapper(methodParameterDetails2);

        assertEquals(objectMapper, objectMapper2);
    }

}
