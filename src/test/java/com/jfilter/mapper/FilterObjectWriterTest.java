package com.jfilter.mapper;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class FilterObjectWriterTest {
    private FilterObjectWriter filterObjectWriter;
    private  ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
        filterObjectWriter = new FilterObjectWriter(objectMapper, objectMapper.getSerializationConfig());
    }

    @Test
    public void testForTypeNull() {
        FilterObjectWriter filterObjectWriter = new FilterObjectWriter(objectMapper, null);
        JavaType javaType = null;
        ObjectWriter objectWriter = filterObjectWriter.forType(javaType);
        assertNotNull(objectWriter);
    }

    @Test(expected = NullPointerException.class)
    public void testForTypeNullNullPointerException() {
        FilterObjectWriter filterObjectWriter = new FilterObjectWriter(objectMapper, null);
        JavaType javaType = objectMapper.constructType(void.class);
        ObjectWriter objectWriter = filterObjectWriter.forType(javaType);
        assertNotNull(objectWriter);
    }

    @Test
    public void testForTypeNotNull() {
        JavaType javaType = objectMapper.constructType(void.class);
        ObjectWriter objectWriter = filterObjectWriter.forType(javaType);
        assertNotNull(objectWriter);
    }

    @Test
    public void testWithNotNull() {
        ObjectWriter objectWriter = filterObjectWriter.with(new DefaultPrettyPrinter());
        assertNotNull(objectWriter);
    }

    @Test
    public void testWithNull() {
        DefaultPrettyPrinter prettyPrinter = null;
        ObjectWriter objectWriter = filterObjectWriter.with(prettyPrinter);
        assertNotNull(objectWriter);
    }
}
