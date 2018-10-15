package com.json.ignore;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

public class JsonIgnoreFieldsTest {
    private JsonIgnoreFields jsonIgnoreFields;

    @Before
    private void init() {
        jsonIgnoreFields = new JsonIgnoreFields();
    }

    @Test
    public void testJsonIgnoreFieldsExists() {
        Assert.notNull(jsonIgnoreFields);
    }
}
