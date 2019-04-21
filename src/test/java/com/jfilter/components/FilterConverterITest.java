package com.jfilter.components;

import com.jfilter.converter.FilterClassWrapper;
import com.jfilter.mock.config.WSConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

@Component
public class FilterConverterITest {
    private FilterConfiguration filterConfiguration;
    private FilterConverter filterConverter;

    @Autowired
    public FilterConverterITest setFilterConfiguration(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
        return this;
    }

    @Before
    public void init() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED2, this);
        filterConfiguration.setEnabled(true);

        filterConverter = new FilterConverter(filterConfiguration);
    }

    @Test
    public void testCanRead() {
        boolean canRead = filterConverter.canRead(null, MediaType.APPLICATION_JSON);
        assertFalse(canRead);
    }

    @Test
    public void testRead() {
        FilterClassWrapper wrapper = filterConverter.read(null, new MockHttpInputMessage("test".getBytes()));
        assertNull(wrapper);
    }

    @Test
    public void testCanWrite() {
        boolean canWrite = filterConverter.canWrite(null, MediaType.APPLICATION_JSON);
        assertTrue(canWrite);
    }

    @Test
    public void testCanWriteFalse() {
        boolean canWrite = filterConverter.canWrite(null, new MediaType("application", "test2"));
        assertFalse(canWrite);
    }

    @Test
    public void testCanWriteDisabled() {
        filterConfiguration.setEnabled(false);
        boolean canWrite = filterConverter.canWrite(null, MediaType.APPLICATION_JSON);
        assertFalse(canWrite);
    }

}
