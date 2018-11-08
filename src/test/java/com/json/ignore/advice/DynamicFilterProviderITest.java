package com.json.ignore.advice;

import com.json.ignore.filter.FilterFields;
import com.json.ignore.filter.dynamic.DynamicFilterEvent;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.mock.MockUser;
import com.json.ignore.mock.config.WSConfigurationEnabled;
import com.json.ignore.request.RequestSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class DynamicFilterProviderITest {
    private DynamicFilterProvider dynamicFilterProvider;

    @Autowired
    public DynamicFilterProviderITest setDynamicFilterProvider(DynamicFilterProvider dynamicFilterProvider) {
        this.dynamicFilterProvider = dynamicFilterProvider;
        return this;
    }

    @Test
    public void testWithAnnotation() {
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();
        FilterFields filterFields = new FilterFields(MockUser.class, Arrays.asList("id", "password"));

        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockDynamicFilterRequest(filterFields));
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertEquals(filterFields, found);
    }

    @Test
    public void testWithoutAnnotation() {
        MethodParameter methodParameter = MockMethods.withoutFileFilters();
        FilterFields filterFields = new FilterFields(MockUser.class, Arrays.asList("id", "password"));

        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockDynamicFilterRequest(filterFields));
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertNotEquals(filterFields, found);
    }

    @Test
    public void testWithAnnotationClear() throws NoSuchFieldException, IllegalAccessException {

        //Clear dynamicList of dynamicFilterProvider
        Field field = DynamicFilterProvider.class.getDeclaredField("dynamicList");
        field.setAccessible(true);
        field.set(dynamicFilterProvider, new HashMap<>());


        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();
        FilterFields filterFields = new FilterFields(MockUser.class, Arrays.asList("id", "password"));

        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockDynamicFilterRequest(filterFields));
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertEquals(new FilterFields(), found);
    }
}
