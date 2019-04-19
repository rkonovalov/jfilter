package com.jfilter;

import org.junit.Assert;
import org.junit.Test;

public class FilterConstantsTest {

    @Test
    public void testConstructor() {
        try {
            Class<?> clazz = Class.forName("com.jfilter.FilterConstants");
            Object date = clazz.newInstance();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalAccessException);
        }
    }
}
