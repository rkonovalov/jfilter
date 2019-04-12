package com.jfilter.converter;

import com.jfilter.mock.MockUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertNull;

public class FilterXmlConverterTest {
    private FilterXmlConverter filterXmlConverter;

    @Before
    public void init() {
        filterXmlConverter = new FilterXmlConverter(null);
    }

    @Test
    public void testCanReadFalse() {
        Assert.assertFalse(filterXmlConverter.canRead(MockUser.class, MediaType.APPLICATION_JSON));
    }

    @Test
    public void testReadFalse() {
        assertNull(filterXmlConverter.read(MockUser.class, null));
    }
}
