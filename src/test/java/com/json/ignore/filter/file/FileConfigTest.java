package com.json.ignore.filter.file;

import mock.MockClasses;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileConfigTest {
    private FileConfig mockConfig;

    @Before
    public void init() {
        mockConfig = MockClasses.getMockAdminFileConfig();
        assertNotNull(mockConfig);
    }

    @Test
    public void testControllerClassName() {
        FileConfig.Controller controller = mockConfig.getControllers().get(0);
        assertEquals(controller.getClassName(), "com.json.ignore.filter.file.FileConfigTest");
    }

    @Test
    public void testStrategy() {
        FileConfig.Controller controller = mockConfig.getControllers().get(0);
        FileConfig.Strategy strategy = controller.getStrategies().get(0);
        assertEquals(strategy.getAttributeName(), "ROLE");
        assertEquals(strategy.getAttributeValue(), "ADMIN");
    }

    @Test
    public void testFilter() {
        FileConfig.Controller controller = mockConfig.getControllers().get(0);
        FileConfig.Strategy strategy = controller.getStrategies().get(0);
        FileConfig.Filter filter = strategy.getFilters().get(0);
        assertEquals(filter.getClassName(), "com.json.ignore.filter.file.FileConfigTest");
    }

    @Test
    public void testField() {
        FileConfig.Controller controller = mockConfig.getControllers().get(0);
        FileConfig.Strategy strategy = controller.getStrategies().get(0);
        FileConfig.Filter filter = strategy.getFilters().get(0);
        FileConfig.Field field = filter.getFields().get(0);
        assertEquals(field.getName(), "password");
    }
}
