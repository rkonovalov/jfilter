package com.json.ignore.filter;

import com.json.ignore.filter.field.FieldFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BaseFilterTest {
    private String test;
    private int intValue;

    public String getTest() {
        return test;
    }

    public BaseFilterTest setTest(String test) {
        System.out.println("setTest->" + test);
        this.test = test;
        return this;
    }

    public int getIntValue() {
        return intValue;
    }

    public BaseFilterTest setIntValue(int intValue) {
        System.out.println("setIntValue->" + intValue);
        this.intValue = intValue;
        return this;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullMethod() {
        BaseFilter baseFilter = new FieldFilter(null);
        assertNotNull(baseFilter);
    }

    @Test
    public void test() throws IllegalAccessException {
        FieldUtils.writeField(this, "test", "Hello", true);
        System.out.println(this.test);
    }

    private String getSetMethodName(String methodName) {
        return "set" + StringUtils.capitalize(methodName);
    }

    private void setValue(Object object, Field field, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = object.getClass().getDeclaredMethod(getSetMethodName(field.getName()), field.getType());
        if (method != null) {
            method.invoke(object, value);
        }
    }

    @Test
    public void test2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        //BaseFilterTest.class.getDeclaredMethod("setTest", String.class).invoke(this, "MyString");
        //System.out.println(this.test);

        this.intValue = 100;
        System.out.println("intValue->" + this.intValue);

        Field field = BaseFilterTest.class.getDeclaredField("intValue");
        setValue(this, field, 0);

        System.out.println("intValue->" + this.intValue);
    }
}
