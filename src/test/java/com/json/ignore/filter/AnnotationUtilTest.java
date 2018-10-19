package com.json.ignore.filter;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.strategy.SessionStrategy;
import mock.MockMethods;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtilTest {

    @Test
    public void testGetDeclaredAnnotations() {
        Method method = MockMethods.findMethodByName("multipleAnnotation");
        assertNotNull(method);

        FieldFilterSetting[] annotations = AnnotationUtil.getDeclaredAnnotations(method, FieldFilterSetting.class);
        assertTrue(annotations.length > 0);
    }

    @Test
    public void testGetDeclaredAnnotation() {
        Method method = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(method);

        FieldFilterSetting annotation = AnnotationUtil.getDeclaredAnnotation(method, FieldFilterSetting.class);
        assertNotNull(annotation);
    }

    @Test
    public void testIsAnnotationExists() {
        Method method = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(method);

        boolean result = AnnotationUtil.isAnnotationExists(method, FieldFilterSetting.class);
        assertTrue(result);
    }

    @Test
    public void testGetSettingAnnotations() {
        Method method = MockMethods.findMethodByName("multipleAnnotation");
        assertNotNull(method);

        FieldFilterSetting[] annotations = AnnotationUtil.getSettingAnnotations(method);
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
