package com.json.ignore.reflect;

import com.json.ignore.mock.MockWithAll;
import com.json.ignore.mock.MockWithoutGetters;
import com.json.ignore.mock.MockWithoutSetters;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class MethodRecordTest {

    @Test
    public void testGetterTrue() throws NoSuchFieldException {
        Field field = MockWithAll.class.getDeclaredField("strValue");
        assertNotNull(field);

        MethodRecord methodRecord = new MethodRecord(field);
        assertNotNull(methodRecord.getGetter());
    }

    @Test
    public void testGetterFalse() throws NoSuchFieldException {
        Field field = MockWithoutGetters.class.getDeclaredField("strValue");
        assertNotNull(field);

        MethodRecord methodRecord = new MethodRecord(field);
        assertNull(methodRecord.getGetter());
    }

    @Test
    public void testSetterTrue() throws NoSuchFieldException {
        Field field = MockWithAll.class.getDeclaredField("strValue");
        assertNotNull(field);

        MethodRecord methodRecord = new MethodRecord(field);
        assertNotNull(methodRecord.getSetter());
    }

    @Test
    public void testSetterFalse() throws NoSuchFieldException {
        Field field = MockWithoutSetters.class.getDeclaredField("strValue");
        assertNotNull(field);

        MethodRecord methodRecord = new MethodRecord(field);
        assertNull(methodRecord.getSetter());
    }


    @Test
    public void testGetValueTrue() throws NoSuchFieldException {
        MockWithAll object = new MockWithAll();
        object.setStrValue("test");

        Field field = MockWithAll.class.getDeclaredField("strValue");
        assertNotNull(field);

        MethodRecord methodRecord = new MethodRecord(field);
        Object value = methodRecord.getValue(() -> object);

        assertEquals(object.getStrValue(), value);
    }

    @Test
    public void testGetValueFalse() throws NoSuchFieldException {
        MockWithoutGetters object = new MockWithoutGetters();
        object.setIntValue(100);

        Field field = object.getClass().getDeclaredField("intValue");
        assertNotNull(field);

        MethodRecord methodRecord = new MethodRecord(field);
        Object value = methodRecord.getValue(() -> object);

        assertNull(value);
    }


    @Test
    public void testSetValueTrue() throws NoSuchFieldException {
        MockWithAll object = new MockWithAll();

        Field field = MockWithAll.class.getDeclaredField("strValue");
        assertNotNull(field);

        MethodRecord methodRecord = new MethodRecord(field);
        methodRecord.setValue(() -> new MethodEventValue(object, "test"));

        assertEquals("test", object.getStrValue());
    }

    @Test
    public void testSetValueFalse() throws NoSuchFieldException {
        MockWithoutSetters object = new MockWithoutSetters();

        Field field = object.getClass().getDeclaredField("intValue");
        assertNotNull(field);

        MethodRecord methodRecord = new MethodRecord(field);
        assertNull(methodRecord.getSetter());


        methodRecord.setValue(() -> new MethodEventValue(object, 500));

        assertNotEquals(new Integer(500), object.getIntValue());
    }

    @Test
    public void testGetValueException() throws NoSuchFieldException {
        MockWithoutSetters object = new MockWithoutSetters();


        Field field = object.getClass().getDeclaredField("strValue");
        assertNotNull(field);

        MethodRecord methodRecord = new MethodRecord(field);

        String value = (String) methodRecord.getValue(() -> object);

        assertNull(value);
    }

    @Test
    public void testSetValueException() throws NoSuchFieldException {
        MockWithoutGetters object = new MockWithoutGetters();

        Field field = object.getClass().getDeclaredField("strValue");
        assertNotNull(field);

        MethodRecord methodRecord = new MethodRecord(field);

        boolean result = methodRecord.setValue(() -> new MethodEventValue(object, "test"));

        assertFalse(result);
    }
}
