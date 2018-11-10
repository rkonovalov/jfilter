package com.jfilter.request;

import com.jfilter.filter.FileWSConfigurationTest;
import com.jfilter.mock.MockClasses;
import com.jfilter.filter.FilterFields;
import com.jfilter.filter.FileConfig;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestMethodParameterTest {

    @Test
    public void testGetStrategyFieldsNotNull() {
        FileConfig config = MockClasses.getMockAdminFileConfig();
        assertNotNull(config);

        FileConfig.Strategy strategy = config.getControllers().get(0).getStrategies().get(0);
        assertNotNull(strategy);

        FilterFields filterFields = strategy.appendStrategyFields(new FilterFields());
        assertTrue(filterFields.getFieldsMap().keySet().size() > 0);
    }

    @Test
    public void testGetStrategyFieldsEmptyStrategy() {
        FileConfig.Strategy strategy = new FileConfig.Strategy();
        FilterFields filterFields = strategy.appendStrategyFields(new FilterFields());
        assertEquals(0, filterFields.getFieldsMap().keySet().size());
    }

    @Test
    public void testGetStrategyFieldsNull() {
        FileConfig.Strategy strategy = new FileConfig.Strategy();
        FilterFields filterFields = strategy.appendStrategyFields(new FilterFields());
        assertEquals(0, filterFields.getFieldsMap().keySet().size());
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

        FilterFields filterFields = strategy.appendStrategyFields(new FilterFields());
        assertEquals(3, filterFields.getFields(FileWSConfigurationTest.class).size());
    }



}
