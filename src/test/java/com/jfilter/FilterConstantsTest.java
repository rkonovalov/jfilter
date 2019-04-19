package com.jfilter;

import org.junit.Test;

public class FilterConstantsTest {

    @Test(expected = IllegalAccessException.class)
    public void testConstructor() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName("com.jfilter.FilterConstants");
        Object date = clazz.newInstance();
    }
}
