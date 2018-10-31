package com.json.ignore.converter;

import com.json.ignore.mock.MockUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

public class FilterXmlConverterTest {
    private FilterXmlConverter filterXmlConverter;

    @Before
    public void init() {
        filterXmlConverter = new FilterXmlConverter();
    }

    @Test
    public void testCanReadFalse() {
        Assert.assertFalse(filterXmlConverter.canRead(MockUser.class, MediaType.APPLICATION_JSON));
    }
}
