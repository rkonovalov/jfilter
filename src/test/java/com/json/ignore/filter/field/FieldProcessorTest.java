package com.json.ignore.filter.field;

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

public class FieldProcessorTest {
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
    private FieldFilterProcessor fieldFilterProcessor;


    @Before
    public void init() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        fieldFilterProcessor = new FieldFilterProcessor();
    }

    @Test
    public void testJsonIgnoreFieldsExists() {
        assertNotNull(fieldFilterProcessor);
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
    public void testUserIgnoreId() throws JsonProcessingException {
        MockUser user = MockClasses.getUserMock();
        fieldFilterProcessor = new FieldFilterProcessor(MockUser.class, LIST_ID);
        fieldFilterProcessor.filterFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_WITHOUT_ID, strUser);
    }

    @Test
    public void testConstructorMap() throws JsonProcessingException {
        MockUser user = MockClasses.getUserMock();
        Map<Class, List<String>> ignores = new HashMap<>();
        ignores.put(MockUser.class, LIST_ALL);
        fieldFilterProcessor = new FieldFilterProcessor(ignores);
        fieldFilterProcessor.filterFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_EMPTY, strUser);
    }

    @Test
    public void testSingleAnnotationMethod() throws JsonProcessingException {
        Method method = MockMethods.findMethodByName("singleAnnotation");
        assertNotNull(method);

        MockUser user = MockClasses.getUserMock();
        fieldFilterProcessor = new FieldFilterProcessor(method);
        fieldFilterProcessor.filterFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_WITHOUT_ID, (strUser));
    }

    @Test
    public void testMultipleAnnotationMethod() throws JsonProcessingException {
        Method method = MockMethods.findMethodByName("multipleAnnotation");
        assertNotNull(method);

        MockUser user = MockClasses.getUserMock();
        fieldFilterProcessor = new FieldFilterProcessor(method);
        fieldFilterProcessor.filterFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_EMPTY, (strUser));
    }

    @Test
    public void testByMethodParameter() throws JsonProcessingException {
        Method method = MockMethods.findMethodByName("singleAnnotation");
        assertNotNull(method);

        MockUser user = MockClasses.getUserMock();
        MethodParameter methodParameter = new MethodParameter(method, 0);
        fieldFilterProcessor = new FieldFilterProcessor(methodParameter);
        fieldFilterProcessor.filterFields(user);
        String strUser = mapper.writeValueAsString(user);

        assertEquals(USER_WITHOUT_ID, (strUser));
    }
}
