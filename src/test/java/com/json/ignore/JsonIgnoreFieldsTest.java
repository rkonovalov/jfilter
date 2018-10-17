package com.json.ignore;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mock.MockClasses;
import mock.MockMethods;
import mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import java.lang.reflect.Method;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JsonIgnoreFieldsTest {
    private static final String SERIALIZED_USER = "{\"id\":100,\"email\":\"mail@mail.com\",\"fullName\":\"Jane Doe\"," +
            "\"password\":\"1234567\",\"intValue\":0,\"collectionValue\":[\"Hello\",\"World\"],\"mapValue\":{\"name\":\"value\"}," +
            "\"boolValue\":true,\"byteValue\":100,\"charValue\":\"c\",\"doubleValue\":5.5,\"floatValue\":5.5,\"longValue\":100500,\"shortValue\":15}";
    private static final String USER_WITHOUT_ID = "{\"email\":\"mail@mail.com\",\"fullName\":\"Jane Doe\",\"password\":\"1234567\"," +
            "\"intValue\":0,\"collectionValue\":[\"Hello\",\"World\"],\"mapValue\":{\"name\":\"value\"},\"boolValue\":true,\"byteValue\":100," +
            "\"charValue\":\"c\",\"doubleValue\":5.5,\"floatValue\":5.5,\"longValue\":100500,\"shortValue\":15}";
    private static final String USER_EMPTY = "{\"intValue\":" + Integer.MIN_VALUE + ",\"boolValue\":false,\"byteValue\":" + Byte.MIN_VALUE + "," +
            "\"charValue\":\"\\u0000\",\"doubleValue\":" + Double.MIN_VALUE + ",\"floatValue\":" + Float.MIN_VALUE + "," +
            "\"longValue\":" + Long.MIN_VALUE + ",\"shortValue\":" + Short.MIN_VALUE + "}";

    private static final List<String> LIST_ID = Collections.singletonList("id");
    private static final List<String> LIST_ALL = Arrays.asList("id", "email", "fullName", "password", "intValue", "collectionValue",
            "mapValue", "boolValue", "byteValue", "charValue", "doubleValue", "floatValue", "longValue", "shortValue");

    private ObjectMapper mapper;
    private JsonIgnoreFields jsonIgnoreFields;


    @Before
    public void init() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonIgnoreFields = new JsonIgnoreFields();
    }

    @Test
    public void testJsonIgnoreFieldsExists() {
        assertNotNull(jsonIgnoreFields);
    }

    @Test
    public void testMapperExists() {
        assertNotNull(mapper);
    }

    @Test
    public void testUserSerialization() throws JsonProcessingException {
        MockUser user = MockClasses.getUserMock();
        String strUser = mapper.writeValueAsString(user);
        assertEquals(SERIALIZED_USER, strUser);
    }

    @Test
    public void testUserIgnoreId() throws JsonProcessingException, IllegalAccessException {
        MockUser user = MockClasses.getUserMock();
        jsonIgnoreFields = new JsonIgnoreFields(MockUser.class, LIST_ID);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_WITHOUT_ID, strUser);
    }

    @Test
    public void testConstructorMap() throws JsonProcessingException, IllegalAccessException {
        MockUser user = MockClasses.getUserMock();
        Map<Class, List<String>> ignores = new HashMap<>();
        ignores.put(MockUser.class, LIST_ALL);
        jsonIgnoreFields = new JsonIgnoreFields(ignores);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_EMPTY, strUser);
    }

    @Test
    public void testSingleAnnotationMethod() throws JsonProcessingException, IllegalAccessException {
        Method method = MockMethods.findMethodByName("singleAnnotation");
        assertNotNull(method);

        MockUser user = MockClasses.getUserMock();
        jsonIgnoreFields = new JsonIgnoreFields(method);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_WITHOUT_ID, (strUser));
    }

    @Test
    public void testMultipleAnnotationMethod() throws JsonProcessingException, IllegalAccessException {
        Method method = MockMethods.findMethodByName("multipleAnnotation");
        assertNotNull(method);

        MockUser user = MockClasses.getUserMock();
        jsonIgnoreFields = new JsonIgnoreFields(method);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_EMPTY, (strUser));
    }

    @Test
    public void testByMethodParameter() throws JsonProcessingException, IllegalAccessException {
        Method method = MockMethods.findMethodByName("singleAnnotation");
        assertNotNull(method);

        MockUser user = MockClasses.getUserMock();
        MethodParameter methodParameter = new MethodParameter(method, 0);
        jsonIgnoreFields = new JsonIgnoreFields(methodParameter);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);

        assertEquals(USER_WITHOUT_ID, (strUser));
    }
}
