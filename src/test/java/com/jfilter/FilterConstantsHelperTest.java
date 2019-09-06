package com.jfilter;

import org.junit.Test;
import java.lang.reflect.InvocationTargetException;

public class FilterConstantsHelperTest {

    @Test(expected = InvocationTargetException.class)
    public void constructorTest() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        TestUtils.instantiateObject(FilterConstantsHelper.class);
    }
}
