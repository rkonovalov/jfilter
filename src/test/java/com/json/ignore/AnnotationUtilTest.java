package com.json.ignore;

import com.json.ignore.strategy.JsonSessionStrategy;
import mock.MockMethods;
import org.junit.Test;

import static com.json.ignore.AnnotationUtil.getDeclaredAnnotations;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtilTest {

    @Test
    public void testGetDeclaredAnnotations() {
        Method method = MockMethods.findMethodByName("multipleAnnotation");
        assertNotNull(method);

        JsonIgnoreSetting[] annotations = AnnotationUtil.getDeclaredAnnotations(method, JsonIgnoreSetting.class);
        assertTrue(annotations.length > 0);
    }

    @Test
    public void testGetDeclaredAnnotation() {
        Method method = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(method);

        JsonIgnoreSetting annotation = AnnotationUtil.getDeclaredAnnotation(method, JsonIgnoreSetting.class);
        assertNotNull(annotation);
    }

    @Test
    public void testIsAnnotationExists() {
        Method method = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(method);

        boolean result = AnnotationUtil.isAnnotationExists(method, JsonIgnoreSetting.class);
        assertTrue(result);
    }

    @Test
    public void testGetSettingAnnotations() {
        Method method = MockMethods.findMethodByName("multipleAnnotation");
        assertNotNull(method);

        JsonIgnoreSetting[] annotations = AnnotationUtil.getSettingAnnotations(method);
        assertTrue(annotations.length > 0);
    }

    @Test
    public void testGetStrategyAnnotations() {
        Method method = MockMethods.findMethodByName("mockIgnoreStrategiesMethod");
        assertNotNull(method);

        JsonSessionStrategy[] annotations = AnnotationUtil.getStrategyAnnotations(method);
        assertTrue(annotations.length > 0);
    }

}
