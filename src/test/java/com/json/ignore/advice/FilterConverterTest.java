package com.json.ignore.advice;

import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.mock.MockUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

public class FilterConverterTest {
    private FilterConverter filterConverter;

    @Before
    public void init() {
        filterConverter = new FilterConverter();
    }

    @Test
    public void testCanReadFalse() {
        Assert.assertFalse(filterConverter.canRead(MockUser.class, MediaType.APPLICATION_JSON));
    }

    @Test
    public void testReadNull() {
        Assert.assertNull(filterConverter.read(MockUser.class, MockHttpRequest.getMockAdminRequest()));
    }
}
