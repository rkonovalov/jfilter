package com.jfilter.components;

import com.jfilter.filter.DynamicFilterEvent;
import com.jfilter.mock.MockHttpRequestHelper;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfigurationEnabled;
import com.jfilter.request.RequestSession;
import com.jfilter.filter.FilterFields;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

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

    @After
    public void deInit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = dynamicFilterProvider.getClass().getDeclaredMethod("findDynamicFilters");
        method.setAccessible(true);
        method.invoke(dynamicFilterProvider);
    }

    @Test
    public void testWithAnnotation() {
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();

        RequestSession requestSession = new RequestSession(MockHttpRequestHelper.getMockDynamicFilterRequest(filterFields));
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertEquals(filterFields, found);
    }

    @Test
    public void testWithoutAnnotation() {
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();

        RequestSession requestSession = new RequestSession(MockHttpRequestHelper.getMockDynamicFilterRequest(filterFields));
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertNotEquals(filterFields, found);
    }

    @Test
    public void testWithAnnotationWithEmptySession() {
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();

        RequestSession requestSession = new RequestSession(MockHttpRequestHelper.getMockDynamicFilterRequest(null));

        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertNotNull(found);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithAnnotationAndEmptyMap() throws NoSuchFieldException, IllegalAccessException {
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();

        Field field = dynamicFilterProvider.getClass().getDeclaredField("dynamicFilterMap");
        field.setAccessible(true);

        Map<Class, DynamicFilterEvent> dynamicFilterMap = (Map<Class, DynamicFilterEvent>)field.get(dynamicFilterProvider);
        dynamicFilterMap.clear();


        RequestSession requestSession = new RequestSession(MockHttpRequestHelper.getMockDynamicFilterRequest(filterFields));
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertEquals(new FilterFields(), found);
    }
}
