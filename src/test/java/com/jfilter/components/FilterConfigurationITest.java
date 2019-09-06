package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.mapper.FilterObjectMapper;
import com.jfilter.mapper.FilterXmlMapper;
import com.jfilter.mock.config.WSConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Component
public class FilterConfigurationITest {
    private FilterConfiguration filterConfiguration;

    @Autowired
    public FilterConfigurationITest setFilterConfiguration(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
        return this;
    }

    @Before
    public void init() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED2, this);
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
    public void testSetUseDefaultConvertersTrue() {
        filterConfiguration.setUseDefaultConverters(true);
        assertTrue(filterConfiguration.isUseDefaultConverters());
    }

    @Test
    public void testSetUseDefaultConvertersFalse() {
        filterConfiguration.setUseDefaultConverters(false);
        assertFalse(filterConfiguration.isUseDefaultConverters());
    }

    @Test
    public void testEnableDateTimeModule() {
        filterConfiguration.getSerializationConfig().enableDateTimeModule(true);
        assertTrue(filterConfiguration.getSerializationConfig().isDateTimeModuleEnabled());
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

    @Test(expected = IllegalArgumentException.class)
    public void testWithCustomConverteNull() {
        filterConfiguration.withCustomConverter(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithCustomConverterIncorrect() {
        MappingJackson2HttpMessageConverter incorrectConverter = new MappingJackson2HttpMessageConverter();
        filterConfiguration.withCustomConverter(incorrectConverter);
    }

    @Test
    public void testWithCustomConverterCorrectObjectMapper() {
        MappingJackson2HttpMessageConverter correctConverter = new MappingJackson2HttpMessageConverter(new FilterObjectMapper(filterConfiguration));
        filterConfiguration.withCustomConverter(correctConverter);
        assertTrue(filterConfiguration.getCustomConverters().size() > 0);
    }

    @Test
    public void testWithCustomConverterCorrectXmlMapper() {
        MappingJackson2HttpMessageConverter correctConverter = new MappingJackson2HttpMessageConverter(new FilterXmlMapper(filterConfiguration));
        filterConfiguration.withCustomConverter(correctConverter);
        assertTrue(filterConfiguration.getCustomConverters().size() > 0);
    }

    @Test
    public void testFindObjectMapperFound() {
        ObjectMapper[] foundObjectMapper = new ObjectMapper[1];
        filterConfiguration.findObjectMapper(MediaType.APPLICATION_JSON, (objectMapper) -> foundObjectMapper[0] = objectMapper);
        assertNotNull(foundObjectMapper[0]);
    }

    @Test
    public void testFindObjectMapperNotFound() {
        ObjectMapper[] foundObjectMapper = new ObjectMapper[1];
        filterConfiguration.findObjectMapper(MediaType.IMAGE_GIF, (objectMapper) -> foundObjectMapper[0] = objectMapper);
        assertNull(foundObjectMapper[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindObjectMapperMediaTypeNull() {
        filterConfiguration.findObjectMapper(null, (objectMapper) -> {});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindObjectMapperOnFindNull() {
        filterConfiguration.findObjectMapper(MediaType.APPLICATION_JSON, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindObjectMapperAllNull() {
        filterConfiguration.findObjectMapper(null);
    }

    @Test
    public void testFindObjectMapperAll() {
        List<ObjectMapper> foundObjectMappers = new ArrayList<>();
        filterConfiguration.findObjectMapper(foundObjectMappers::add);
        assertEquals(6, foundObjectMappers.size());
    }

}
