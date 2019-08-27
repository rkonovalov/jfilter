package com.jfilter.util;

import com.jfilter.TestUtils;
import org.junit.Test;
import java.lang.reflect.InvocationTargetException;

public class FilterUtilTest {

    @Test(expected = InvocationTargetException.class)
    public void constructorTest() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        TestUtils.instantiateObject(FilterUtil.class);
    }
}
