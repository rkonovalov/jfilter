package com.json.ignore.filter.file;

import com.json.ignore.mock.MockClasses;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileWSConfigurationTest {
    private FileConfig mockConfig;

    @Before
    public void init() {
        mockConfig = MockClasses.getMockAdminFileConfig();
        assertNotNull(mockConfig);
    }

    @Test
    public void testControllerClassName() {
        FileConfig.Controller controller = mockConfig.getControllers().get(0);
        assertEquals("com.json.ignore.filter.file.FileWSConfigurationTest", controller.getClassName());
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
        assertEquals("com.json.ignore.filter.file.FileWSConfigurationTest", filter.getClassName());
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
