package com.json.ignore;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import mock.UserMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JsonIgnoreFieldsTest {
    private static final String SERIALIZED_USER = "{\"id\":100,\"email\":\"mail@mail.com\",\"fullName\":\"Jane Doe\",\"password\":\"1234567\",\"intValue\":0,\"collectionValue\":[\"Hello\",\"World\"],\"mapValue\":{\"name\":\"value\"},\"boolValue\":true}";
    private static final String USER_WITHOUT_ID = "{\"email\":\"mail@mail.com\",\"fullName\":\"Jane Doe\",\"password\":\"1234567\",\"intValue\":0,\"collectionValue\":[\"Hello\",\"World\"],\"mapValue\":{\"name\":\"value\"},\"boolValue\":true}";
    private static final String USER_EMPTY = "{\"intValue\":" + Integer.MIN_VALUE +",\"boolValue\":false}";

    private static final List<String> LIST_ID = Arrays.asList("id");
    private static final List<String> LIST_ALL = Arrays.asList("id", "email", "fullName", "password", "intValue", "collectionValue", "mapValue", "boolValue");

    private ObjectMapper mapper;
    private JsonIgnoreFields jsonIgnoreFields;


    @Before
    public void init() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonIgnoreFields = new JsonIgnoreFields();
    }

    private UserMock getUserMock() {
        UserMock userMock = new UserMock();

        Map<String, String> values = new HashMap<>();
        values.put("name", "value");


        userMock.setId(100)
                .setEmail("mail@mail.com")
                .setFullName("Jane Doe")
                .setPassword("1234567")
                .setId(100)
                .setCollectionValue(new ArrayList<>(Arrays.asList("Hello", "World")))
                .setMapValue(values)
                .setBoolValue(true);

        return userMock;
    }

    @JsonIgnoreSetting(className = UserMock.class, fields = {"id"})
    private void singleAnnotation() {

    }

    @JsonIgnoreSetting(className = UserMock.class, fields = {"id"})
    @JsonIgnoreSetting(className = UserMock.class, fields = {"email", "fullName", "password", "intValue", "collectionValue", "mapValue", "boolValue"})
    private void multipleAnnotation() {

    }

    private Method findDeclaredMethod(String methodName) {
        Method[] methods = this.getClass().getDeclaredMethods();

        for(Method method : methods) {
            if(method.getName().equals(methodName))
            if (method.getDeclaredAnnotation(JsonIgnoreSettings.class) != null ||
                    method.getDeclaredAnnotation(JsonIgnoreSetting.class) != null)
                return method;
        }
        return null;
    }

    @Test
    public void testJsonIgnoreFieldsExists() {
        Assert.notNull(jsonIgnoreFields);
    }

    @Test
    public void testMapperExists() {
        Assert.notNull(mapper);
    }

    @Test
    public void testUserSerialization() throws JsonProcessingException {
        UserMock user = getUserMock();
        String strUser = mapper.writeValueAsString(user);
        assertEquals(SERIALIZED_USER, strUser);
    }

    @Test
    public void testUserIgnoreId() throws JsonProcessingException, IllegalAccessException {
        UserMock user = getUserMock();
        jsonIgnoreFields = new JsonIgnoreFields(UserMock.class, LIST_ID);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_WITHOUT_ID, strUser);
    }

    @Test
    public void testConstructorMap() throws JsonProcessingException, IllegalAccessException {
        UserMock user = getUserMock();
        Map<Class, List<String>> ignores = new HashMap<>();
        ignores.put(UserMock.class, LIST_ALL);
        jsonIgnoreFields = new JsonIgnoreFields(ignores);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_EMPTY, strUser);
    }

    @Test
    public void testSingleAnnotationMethod() throws JsonProcessingException, IllegalAccessException {
        Method method = findDeclaredMethod("singleAnnotation");
        assertNotNull(method);

        UserMock user = getUserMock();
        jsonIgnoreFields = new JsonIgnoreFields(method);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_WITHOUT_ID, (strUser));
    }

    @Test
    public void testMultipleAnnotationMethod() throws JsonProcessingException, IllegalAccessException {
        Method method = findDeclaredMethod("multipleAnnotation");
        assertNotNull(method);

        UserMock user = getUserMock();
        jsonIgnoreFields = new JsonIgnoreFields(method);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        assertEquals(USER_EMPTY, (strUser));
    }

    @Test
    public void testByMethodParameter() throws JsonProcessingException, IllegalAccessException {
        Method method = findDeclaredMethod("singleAnnotation");
        assertNotNull(method);

        UserMock user = getUserMock();
        MethodParameter methodParameter = new MethodParameter(method, 0);
        jsonIgnoreFields = new JsonIgnoreFields(methodParameter);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);

        assertEquals(USER_WITHOUT_ID, (strUser));
    }

    @Test
    public void testAnnotationFound() {
        Method method = findDeclaredMethod("singleAnnotation");
        assertNotNull(method);

        MethodParameter methodParameter = new MethodParameter(method, 0);
        boolean result = JsonIgnoreFields.annotationFound(methodParameter);

        assertTrue(result);
    }
}
