package com.jfilter.mapper;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.jfilter.components.FilterConfiguration;
import com.jfilter.components.FilterProvider;
import com.jfilter.converter.FilterClassWrapper;
import com.jfilter.converter.MethodParameterDetails;
import com.jfilter.filter.BaseFilter;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfigurationEnabled;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FilterObjectWriterITest {
    private FilterObjectWriter writer;
    private MockUser user;
    private ByteArrayOutputStream out;
    private JsonGenerator generator;

    @Autowired
    private FilterConfiguration filterConfiguration;

    @Autowired
    private FilterProvider filterProvider;

    @Before
    public void init() throws IOException {
        FilterObjectMapper objectMapper = new FilterObjectMapper(filterConfiguration);
        writer = (FilterObjectWriter) objectMapper.writer();
        user = new MockUser();

        out = new ByteArrayOutputStream();
        generator = objectMapper.getFactory().createGenerator(out, JsonEncoding.UTF8);
    }

    @Test
    public void testWriteValue() throws IOException {
        writer.writeValue(generator, user);
        assertNotNull(out.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testWriteValueFilterClassWrapperNull() throws IOException {
        FilterClassWrapper filterClassWrapper = new FilterClassWrapper(user, null);
        writer.writeValue(generator, filterClassWrapper);
    }

    @Test
    public void testWriteValueFilterClassWrapperNotNull() throws IOException {

        MethodParameter methodParameter = MockMethods.singleAnnotation();

        BaseFilter filter = filterProvider.getFilter(methodParameter);

        MethodParameterDetails methodParameterDetails = new MethodParameterDetails(methodParameter,
                MediaType.APPLICATION_JSON, filter.getFields(user, null));

        FilterClassWrapper filterClassWrapper = new FilterClassWrapper(user, methodParameterDetails);
        writer.writeValue(generator, filterClassWrapper);

        String output = out.toString();

        assertEquals(-1, output.indexOf("\"id\""));
    }

    @Test
    public void testGetFilterConfiguration() {
        assertEquals(filterConfiguration, writer.getFilterConfiguration());
    }
}
