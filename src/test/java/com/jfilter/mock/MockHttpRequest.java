package com.jfilter.mock;

import com.jfilter.filter.FilterFields;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.HttpServletRequest;

import static com.jfilter.components.DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class MockHttpRequest {

    private static HttpServletRequest getMockRequest(String attributeName, Object attributeValue) {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "");
        if (attributeName != null && !attributeName.isEmpty())
            request.getSession().setAttribute(attributeName, attributeValue);
        return request;
    }

    public static HttpServletRequest getMockAdminRequest() {
        return getMockRequest("ROLE", "ADMIN");
    }

    public static HttpServletRequest getMockUserRequest() {
        return getMockRequest("ROLE", "USER");
    }

    public static HttpServletRequest getMockClearRequest() {
        return getMockRequest("", "");
    }

    public static HttpServletRequest getMockDynamicFilterRequest(FilterFields filterFields) {
        return getMockRequest(ATTRIBUTE_FILTER_FIELDS, filterFields);
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

    public static String doRequest(MockMvc mockMvc, String url) {
        return doRequest(mockMvc, url, null, null);
    }
}
