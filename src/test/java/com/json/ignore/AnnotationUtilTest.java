package com.json.ignore;

import com.json.ignore.strategy.SessionStrategy;
import mock.MockMethods;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Method;

public class AnnotationUtilTest {

    @Test
    public void testGetDeclaredAnnotations() {
        Method method = MockMethods.findMethodByName("multipleAnnotation");
        assertNotNull(method);

        FieldIgnoreSetting[] annotations = AnnotationUtil.getDeclaredAnnotations(method, FieldIgnoreSetting.class);
        assertTrue(annotations.length > 0);
    }

    @Test
    public void testGetDeclaredAnnotation() {
        Method method = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(method);

        FieldIgnoreSetting annotation = AnnotationUtil.getDeclaredAnnotation(method, FieldIgnoreSetting.class);
        assertNotNull(annotation);
    }

    @Test
    public void testIsAnnotationExists() {
        Method method = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(method);

        boolean result = AnnotationUtil.isAnnotationExists(method, FieldIgnoreSetting.class);
        assertTrue(result);
    }

    @Test
    public void testGetSettingAnnotations() {
        Method method = MockMethods.findMethodByName("multipleAnnotation");
        assertNotNull(method);

        FieldIgnoreSetting[] annotations = AnnotationUtil.getSettingAnnotations(method);
        assertTrue(annotations.length > 0);
    }

    @Test
    public void testGetStrategyAnnotations() {
        Method method = MockMethods.findMethodByName("mockIgnoreStrategiesMethod");
        assertNotNull(method);

        SessionStrategy[] annotations = AnnotationUtil.getStrategyAnnotations(method);
        assertTrue(annotations.length > 0);
    }

    @Test
    public void testGetStrategiesAnnotations() {
        Method method = MockMethods.findMethodByName("mockIgnoreStrategiesMethod");
        assertNotNull(method);

        SessionStrategy[] annotations = AnnotationUtil.getStrategyAnnotations(method);
        assertTrue(annotations.length > 0);
    }

}
