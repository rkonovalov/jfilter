package com.jfilter.mock;

import com.jfilter.filter.dynamic.DynamicSessionFilter;
import com.jfilter.filter.FilterFields;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class MockHttpRequest {

    private static ServletServerHttpRequest getMocRequest(String attributeName, Object attributeValue) {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "");
        if (attributeName != null && !attributeName.isEmpty())
            request.getSession().setAttribute(attributeName, attributeValue);
        return new ServletServerHttpRequest(request);
    }

    public static ServletServerHttpRequest getMockAdminRequest() {
        return getMocRequest("ROLE", "ADMIN");
    }

    public static ServletServerHttpRequest getMockUserRequest() {
        return getMocRequest("ROLE", "USER");
    }

    public static ServletServerHttpRequest getMockClearRequest() {
        return getMocRequest("", "");
    }

    public static ServletServerHttpRequest getMockDynamicFilterRequest(FilterFields filterFields) {
        return getMocRequest(DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS, filterFields);
    }

    private static String getContent(MockMvc mockMvc, MockHttpServletRequestBuilder requestBuilder) {
        final StringBuilder result = new StringBuilder();
        try {
            mockMvc.perform(requestBuilder)
                    .andDo(print())
                    .andExpect(mvcResult -> result.append(mvcResult.getResponse().getContentAsString()));
        } catch (Exception e) {
            return result.toString();
        }
        return result.toString();
    }

    public static String doRequest(MockMvc mockMvc, String url, String attributeName, Object attributeValue) {
        MockHttpServletRequestBuilder requestBuilder = post(url);
        requestBuilder
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        if (attributeName != null && attributeValue != null)
            requestBuilder.sessionAttr(attributeName, attributeValue);
        return MockHttpRequest.getContent(mockMvc, requestBuilder);
    }
}
