package com.jfilter.converter;

import com.jfilter.components.FilterMapperConfig;
import com.jfilter.mock.MockUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertNull;

public class FilterJsonConverterTest {
    private FilterJsonConverter filterJsonConverter;
    private FilterMapperConfig filterMapperConfig;

    @Before
    public void init() {
        filterMapperConfig = new FilterMapperConfig();
        filterJsonConverter = new FilterJsonConverter(filterMapperConfig);
    }

    @Test
    public void testCanReadFalse() {
        Assert.assertFalse(filterJsonConverter.canRead(MockUser.class, MediaType.APPLICATION_JSON));
    }

    @Test
    public void testReadFalse() {
        assertNull(filterJsonConverter.read(MockUser.class, null));
    }
}
