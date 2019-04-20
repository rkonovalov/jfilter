package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.mock.config.WSConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

@Component
public class FilterConfigurationITest {
    private FilterConfiguration filterConfiguration;
    private MockMvc mockMvc;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Before
    public void init() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
    }

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
    public void testDisabled() {
        filterConfiguration.setEnabled(false);
        assertFalse(filterConfiguration.isEnabled());
    }

    @Test
    public void testEnabled() {
        filterConfiguration.setEnabled(true);
        assertTrue(filterConfiguration.isEnabled());
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
