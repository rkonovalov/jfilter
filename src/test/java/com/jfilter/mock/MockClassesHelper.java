package com.jfilter.mock;

import com.jfilter.filter.FileConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;


public class MockClassesHelper {

    public static MockUser getUserMock() {
        MockUser mockUser = new MockUser();

        Map<String, String> values = new HashMap<>();
        values.put("name", "value");

        List<String> collection = new ArrayList<>(Arrays.asList("Hello", "World"));

        mockUser.setId(100)
                .setEmail("mail@mail.com")
                .setFullName("Jane Doe")
                .setPassword("1234567")
                .setId(100)
                .setCollectionValue(collection)
                .setMapValue(values)
                .setBoolValue(true)
                .setByteValue((byte) 100)
                .setCharValue('c')
                .setDoubleValue(5.5)
                .setFloatValue(5.5f)
                .setLongValue(100500)
                .setShortValue((short) 15);

        return mockUser;
    }

    public static FileConfig getMockAdminFileConfig() {

        FileConfig.Filter filter = new FileConfig.Filter();
        filter.setClassName("com.jfilter.filter.FileConfigurationTest");
        filter.getFields().add(new FileConfig.Field().setName("id"));
        filter.getFields().add(new FileConfig.Field().setName("password"));

        FileConfig.Strategy strategy = new FileConfig.Strategy();
        strategy.setAttributeName("ROLE")
                .setAttributeValue("ADMIN")
                .getFilters()
                .add(filter);

        FileConfig.Controller controller = new FileConfig.Controller();
        controller.setClassName("com.jfilter.filter.FileConfigurationTest")
                .getStrategies()
                .add(strategy);

        FileConfig fileConfig = new FileConfig();
        fileConfig.getControllers()
                .add(controller);
        return fileConfig;
    }
}
