package com.jfilter.filter;

import com.jfilter.mock.MockClassesHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileConfigurationTest {
    private FileConfig mockConfig;

    @Before
    public void init() {
        mockConfig = MockClassesHelper.getMockAdminFileConfig();
        assertNotNull(mockConfig);
    }

    @Test
    public void testControllerClassName() {
        FileConfig.Controller controller = mockConfig.getControllers().get(0);
        assertEquals("com.jfilter.filter.FileConfigurationTest", controller.getClassName());
    }

    @Test
    public void testStrategy() {
        FileConfig.Controller controller = mockConfig.getControllers().get(0);
        FileConfig.Strategy strategy = controller.getStrategies().get(0);
        assertEquals("ROLE", strategy.getAttributeName());
        assertEquals("ADMIN", strategy.getAttributeValue());
    }

    @Test
    public void testFilter() {
        FileConfig.Controller controller = mockConfig.getControllers().get(0);
        FileConfig.Strategy strategy = controller.getStrategies().get(0);
        FileConfig.Filter filter = strategy.getFilters().get(0);
        assertEquals("com.jfilter.filter.FileConfigurationTest", filter.getClassName());
    }

    @Test
    public void testField() {
        FileConfig.Controller controller = mockConfig.getControllers().get(0);
        FileConfig.Strategy strategy = controller.getStrategies().get(0);
        FileConfig.Filter filter = strategy.getFilters().get(0);
        FileConfig.Field field = filter.getFields().get(0);
        assertEquals("id", field.getName());
    }
}
