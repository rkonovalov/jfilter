package mock;

import com.json.ignore.filter.file.FileConfig;

import java.util.*;

public class MockClasses {

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
        FileConfig.Field idField = new FileConfig.Field();
        idField.setName("id");

        FileConfig.Field passwordField = new FileConfig.Field();
        idField.setName("password");


        FileConfig.Filter filter = new FileConfig.Filter();
        filter.setClassName("com.json.ignore.filter.file.FileConfigTest");
        filter.getFields().add(idField);
        filter.getFields().add(passwordField);

        FileConfig.Strategy strategy = new FileConfig.Strategy();
        strategy.setAttributeName("ROLE")
                .setAttributeValue("ADMIN")
                .getFilters()
                .add(filter);

        FileConfig.Controller controller = new FileConfig.Controller();
        controller.setClassName("com.json.ignore.filter.file.FileConfigTest")
                .getStrategies()
                .add(strategy);

        FileConfig fileConfig = new FileConfig();
        fileConfig.getControllers()
                .add(controller);
        return fileConfig;
    }
}
