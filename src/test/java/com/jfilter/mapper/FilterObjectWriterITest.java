package com.jfilter.mapper;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jfilter.components.FilterConfiguration;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfigurationEnabled;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.*;

import static org.junit.Assert.*;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FilterObjectWriterITest {
    private FilterObjectMapper objectMapper;
    private ObjectWriter writer;
    private MockUser user;

    @Autowired
    private FilterConfiguration filterConfiguration;

    @Before
    public void init() {
        objectMapper = new FilterObjectMapper(filterConfiguration);
        writer = objectMapper.writer();
        user = new MockUser();
    }

    @Test
    public void testWriter() {
        assertTrue(writer instanceof FilterObjectWriter);
    }

    @Test
    public void testWriteValue() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JsonEncoding encoding = JsonEncoding.UTF8;
        JsonGenerator generator = this.objectMapper.getFactory().createGenerator(out, encoding);
        writer.writeValue(generator, user);
        assertNotNull(out.toString());
    }
}
