package com.json.ignore.converter;

import com.json.ignore.converter.FilterJsonConverter;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

public class FilterJsonConverterTest {
    private FilterJsonConverter filterJsonConverter;

    @Before
    public void init() {
        filterJsonConverter = new FilterJsonConverter();
    }

    @Test
    public void testCanReadFalse() {
        Assert.assertFalse(filterJsonConverter.canRead(MockUser.class, MediaType.APPLICATION_JSON));
    }

    @Test
    public void testReadNull() {
        Assert.assertNull(filterJsonConverter.read(MockUser.class, MockHttpRequest.getMockAdminRequest()));
    }
}
