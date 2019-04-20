package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.mock.config.WSConfigurationEnabled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.*;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FilterConfigurationITest {
    private FilterConfiguration filterConfiguration;

    @Autowired
    public FilterConfigurationITest setFilterConfiguration(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
        return this;
    }

    @Test
    public void testNotNull() {
        assertNotNull(filterConfiguration);
    }

    @Test
    public void testEnabled() {
        filterConfiguration.setEnabled(true);
        assertTrue(filterConfiguration.isEnabled());
    }

    @Test
    public void testDisabled() {
        filterConfiguration.setEnabled(false);
        assertFalse(filterConfiguration.isEnabled());
    }

    @Test
    public void testSetMapperOverride() {
        ObjectMapper objectMapper = new ObjectMapper();
        filterConfiguration.setMapper(MediaType.APPLICATION_JSON, objectMapper);
        assertEquals(filterConfiguration.getMapper(MediaType.APPLICATION_JSON), objectMapper);
    }

    @Test
    public void testSetMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        MediaType mediaType = new MediaType("application", "test");
        filterConfiguration.setMapper(mediaType, objectMapper);
        assertEquals(filterConfiguration.getMapper(mediaType), objectMapper);
    }
}
