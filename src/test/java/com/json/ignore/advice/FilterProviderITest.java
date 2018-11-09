package com.json.ignore.advice;

import com.json.ignore.filter.BaseFilter;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.mock.config.WSConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@Configuration
public class FilterProviderITest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ConfigurableApplicationContext applicationContext;


    private FilterProvider filterProvider;

    @Autowired(required = false)
    public FilterProviderITest setFilterProvider(FilterProvider filterProvider) {
        this.filterProvider = filterProvider;
        return this;
    }

    @Test
    public void testIsEnabledTrue() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);

        boolean result = FilterProvider.isFilterEnabled(webApplicationContext);

        assertTrue(result);
    }

    @Test
    public void testIsEnabledFalse() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_DISABLED, this);
        boolean result = FilterProvider.isFilterEnabled(webApplicationContext);

        assertFalse(result);
    }

    @Test
    public void testGetFilter() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
        MethodParameter methodParameter = MockMethods.singleAnnotation();

        BaseFilter filter = filterProvider.getFilter(methodParameter);

        assertNotNull(filter);
    }

    @Test
    public void testGetFilterNull() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
        MethodParameter methodParameter = MockMethods.methodWithLazyAnnotation();

        BaseFilter filter = filterProvider.getFilter(methodParameter);

        assertNull(filter);
    }




}
