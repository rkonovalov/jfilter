package com.json.ignore.request;

import com.json.ignore.filter.file.FileConfig;
import com.json.ignore.filter.file.FileConfigTest;
import com.json.ignore.mock.MockClasses;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestMethodParameterTest {

    @Test
    public void testClassExists() {
        Class clazz = RequestMethodParameter.getClassByName("com.json.ignore.filter.BaseFilter");
        assertNotNull(clazz);
    }

    @Test
    public void testClassNotExists() {
        Class clazz = RequestMethodParameter.getClassByName("com.json.ignore.NotExistedClass");
        assertNull(clazz);
    }

    @Test
    public void testClassEmpty() {
        Class clazz = RequestMethodParameter.getClassByName("");
        assertNull(clazz);
    }

    @Test
    public void testClassNull() {
        Class clazz = RequestMethodParameter.getClassByName("");
        assertNull(clazz);
    }

    @Test
    public void testGetStrategyFieldsNotNull() {
        FileConfig config = MockClasses.getMockAdminFileConfig();
        assertNotNull(config);

        FileConfig.Strategy strategy = config.getControllers().get(0).getStrategies().get(0);
        assertNotNull(strategy);

        Map<Class, List<String>> fields = strategy.getStrategyFields();
        assertTrue(fields.keySet().size() > 0);
    }

    @Test
    public void testGetStrategyFieldsEmptyStrategy() {
        FileConfig.Strategy strategy = new FileConfig.Strategy();
        Map<Class, List<String>> fields = strategy.getStrategyFields();
        assertEquals(0, fields.keySet().size());
    }

    @Test
    public void testGetStrategyFieldsNull() {
        FileConfig.Strategy strategy = new FileConfig.Strategy();
        Map<Class, List<String>> fields = strategy.getStrategyFields();
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

        Map<Class, List<String>> fields = strategy.getStrategyFields();
        assertEquals(3, fields.get(FileConfigTest.class).size());
    }



}
