package com.json.ignore;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import mock.UserMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonIgnoreFieldsTest {
    private static final String SERIALIZED_USER = "{\"id\":100,\"email\":\"mail@mail.com\",\"fullName\":\"Jane Doe\",\"password\":\"1234567\"}";
    private static final String USER_WITHOUT_ID = "{\"email\":\"mail@mail.com\",\"fullName\":\"Jane Doe\",\"password\":\"1234567\"}";
    private static final String USER_WITHOUT_ID_AND_PASSWORD = "{\"email\":\"mail@mail.com\",\"fullName\":\"Jane Doe\"}";

    private static final List<String> LIST_ID = Arrays.asList("id");
    private static final List<String> LIST_ID_AND_PASSWORD = Arrays.asList("id", "password");
    private static final List<String> LIST_ALL = Arrays.asList("id", "email", "fullName", "password");

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

        userMock.setId(100)
                .setEmail("mail@mail.com")
                .setFullName("Jane Doe")
                .setPassword("1234567");

        return userMock;
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
        Assert.isTrue(SERIALIZED_USER.equals(strUser));
    }

    @Test
    public void testUserIgnoreId() throws JsonProcessingException, IllegalAccessException {
        UserMock user = getUserMock();
        jsonIgnoreFields = new JsonIgnoreFields(UserMock.class, LIST_ID);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        Assert.isTrue(USER_WITHOUT_ID.equals(strUser));
    }

    @Test
    public void testUserIgnoreIdAndPassword() throws JsonProcessingException, IllegalAccessException {
        UserMock user = getUserMock();
        jsonIgnoreFields = new JsonIgnoreFields(UserMock.class, LIST_ID_AND_PASSWORD);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        Assert.isTrue(USER_WITHOUT_ID_AND_PASSWORD.equals(strUser));
    }

    @Test
    public void testUserIgnoreAll() throws JsonProcessingException, IllegalAccessException {
        UserMock user = getUserMock();
        jsonIgnoreFields = new JsonIgnoreFields(UserMock.class, LIST_ALL);
        jsonIgnoreFields.ignoreFields(user);
        String strUser = mapper.writeValueAsString(user);
        Assert.isTrue("{}".equals(strUser));
    }
}
