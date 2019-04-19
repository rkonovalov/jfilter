package com.jfilter.converter;

import com.jfilter.components.DynamicFilterProvider;
import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfiguration;
import com.jfilter.request.RequestSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

@Component
public class ConverterMapperITest {

    @Autowired
    private DynamicFilterProvider dynamicFilterProvider;

    @Test
    public void testOnGetFilterFieldsTrue() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);

        FilterFields filterFields = new FilterFields(MockUser.class, Arrays.asList("id", "password"));

        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();
        HttpServletRequest request = MockHttpRequest.getMockDynamicFilterRequest(filterFields);
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, new RequestSession(request));

        assertEquals(filterFields, found);
    }

    @Test
    public void testOnGetFilterFieldsEmpty() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);

        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();
        HttpServletRequest request = MockHttpRequest.getMockDynamicFilterRequest(null);
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, new RequestSession(request));

        assertEquals(0, found.getFieldsMap().size());
    }
}
