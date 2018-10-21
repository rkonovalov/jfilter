package com.json.ignore.filter;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.file.FileConfig;
import com.json.ignore.filter.file.FileConfigTest;
import com.json.ignore.filter.strategy.SessionStrategy;
import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockMethods;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationUtilTest {
    private Method mockIgnoreSettingsMethod;
    private Method multipleAnnotation;
    private Method mockIgnoreStrategiesMethod;

    @Before
    public void init() {
        mockIgnoreSettingsMethod = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(mockIgnoreSettingsMethod);

        multipleAnnotation = MockMethods.findMethodByName("multipleAnnotation");
        assertNotNull(multipleAnnotation);

        mockIgnoreStrategiesMethod = MockMethods.findMethodByName("mockIgnoreStrategiesMethod");
        assertNotNull(mockIgnoreStrategiesMethod);
    }

    @Test
    public void testGetDeclaredAnnotations() {
        FieldFilterSetting[] annotations = AnnotationUtil.getDeclaredAnnotations(multipleAnnotation, FieldFilterSetting.class);
        assertTrue(annotations.length > 0);
    }

    @Test
    public void testGetDeclaredAnnotation() {
        FieldFilterSetting annotation = AnnotationUtil.getDeclaredAnnotation(mockIgnoreSettingsMethod, FieldFilterSetting.class);
        assertNotNull(annotation);
    }

    @Test
    public void testIsAnnotationExists() {
        boolean result = AnnotationUtil.isAnnotationExists(mockIgnoreSettingsMethod, Collections.singletonList(FieldFilterSetting.class));
        assertTrue(result);
    }

    @Test
    public void testIsAnnotationExistsZeroLength() {
        boolean result = AnnotationUtil.isAnnotationExists(mockIgnoreSettingsMethod, new ArrayList<>());
        assertFalse(result);
    }

    @Test
    public void testIsAnnotationExistsFalse() {
        boolean result = AnnotationUtil.isAnnotationExists(MockMethods.methodWithoutAnnotations().getMethod(), new ArrayList<>());
        assertFalse(result);
    }

    @Test
    public void testGetSettingAnnotations() {
        FieldFilterSetting[] annotations = AnnotationUtil.getSettingAnnotations(multipleAnnotation);
        assertTrue(annotations.length > 0);
    }

    @Test
    public void testGetStrategyAnnotations() {
        SessionStrategy[] annotations = AnnotationUtil.getStrategyAnnotations(mockIgnoreStrategiesMethod);
        assertTrue(annotations.length > 0);
    }

    @Test
    public void testGetStrategiesAnnotations() {
        SessionStrategy[] annotations = AnnotationUtil.getStrategyAnnotations(mockIgnoreStrategiesMethod);
        assertTrue(annotations.length > 0);
    }

    @Test
    public void testGetStrategyFieldsNotNull() {
        FileConfig config = MockClasses.getMockAdminFileConfig();
        assertNotNull(config);

        Map<Class, List<String>> fields = AnnotationUtil.getStrategyFields(config.getControllers().get(0).getStrategies().get(0));
        assertTrue(fields.keySet().size() > 0);
    }

    @Test
    public void testGetStrategyFieldsEmptyStrategy() {
        FileConfig.Strategy strategy = new FileConfig.Strategy();
        Map<Class, List<String>> fields = AnnotationUtil.getStrategyFields(strategy);
        assertEquals(0, fields.keySet().size());
    }

    @Test
    public void testGetStrategyFieldsNull() {
        FileConfig.Strategy strategy = new FileConfig.Strategy();
        Map<Class, List<String>> fields = AnnotationUtil.getStrategyFields(strategy);
        assertEquals(0, fields.keySet().size());
    }

    @Test
    public void testGetStrategyFieldsMultiple() {
        FileConfig mockConfig = MockClasses.getMockAdminFileConfig();

        FileConfig.Strategy strategy = mockConfig.getControllers().get(0).getStrategies().get(0);

        FileConfig.Filter filter = new FileConfig.Filter();
        filter.setClassName(strategy.getFilters().get(0).getClassName());
        filter.getFields().add(new FileConfig.Field().setName("password"));
        filter.getFields().add(new FileConfig.Field().setName("email"));
        filter.getFields().add(new FileConfig.Field().setName("email"));


        strategy.getFilters().add(filter);
        strategy.getFilters().add(filter);

        Map<Class, List<String>> fields = AnnotationUtil.getStrategyFields(strategy);
        assertEquals(3, fields.get(FileConfigTest.class).size());
    }



}
