package com.jfilter.components;

import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfigurationEnabled;
import com.jfilter.request.RequestSession;
import com.jfilter.filter.FilterFields;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class DynamicFilterProviderITest {
    private DynamicFilterProvider dynamicFilterProvider;
    private FilterFields filterFields;

    @Autowired
    public DynamicFilterProviderITest setDynamicFilterProvider(DynamicFilterProvider dynamicFilterProvider) {
        this.dynamicFilterProvider = dynamicFilterProvider;
        return this;
    }

    @Before
    public void init() {
        filterFields = new FilterFields(MockUser.class, Arrays.asList("id", "password"));
    }

    @Test
    public void testWithAnnotation() {
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();

        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockDynamicFilterRequest(filterFields));
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertEquals(filterFields, found);
    }

    @Test
    public void testWithoutAnnotation() {
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();

        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockDynamicFilterRequest(filterFields));
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertNotEquals(filterFields, found);
    }

    @Test
    public void testWithAnnotationWithEmptySession() {
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();

        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockDynamicFilterRequest(null));

        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertNotNull(found);
    }

    @Test
    public void testWithAnnotationClear() throws NoSuchFieldException, IllegalAccessException {

        //Clear dynamicList of dynamicFilterProvider
        Field field = DynamicFilterProvider.class.getDeclaredField("dynamicFilterMap");
        field.setAccessible(true);
        field.set(dynamicFilterProvider, new HashMap<>());


        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();

        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockDynamicFilterRequest(filterFields));
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertEquals(filterFields, found);
    }
}
