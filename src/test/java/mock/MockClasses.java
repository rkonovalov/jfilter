package mock;

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
}
