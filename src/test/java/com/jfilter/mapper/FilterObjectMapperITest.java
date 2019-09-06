package com.jfilter.mapper;

import com.jfilter.components.FilterConfiguration;
import com.jfilter.mock.config.WSConfigurationEnabled;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.fasterxml.jackson.databind.ser.FilterProvider;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FilterObjectMapperITest {
    private FilterObjectMapper objectMapper;

    @Autowired
    private FilterConfiguration filterConfiguration;

    @Before
    public void init() {
        objectMapper = new FilterObjectMapper(filterConfiguration);
    }

    @Test
    public void testWriterNotNull() {
        assertNotNull(objectMapper.writer());
    }

    @Test
    public void testWriterWithViewNotNull() {
        assertNotNull(objectMapper.writerWithView(null));
    }

    @Test
    public void testWriterWithfilterProviderNotNull() {
        assertNotNull(objectMapper.writer((FilterProvider) null));
    }
}
