package mock;

import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.mock.web.MockHttpServletRequest;

public class MockHttpRequest {

    private static ServletServerHttpRequest getMocRequest(String attributeName, String attributeValue) {
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
}
