
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.json.ignore.filter;

import mock.MockHttpRequest;
import mock.MockMethods;
import mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import javax.servlet.http.HttpSession;
import static org.junit.Assert.assertNotNull;

/**
 * @author Ruslan {@literal <rkonovalov86@gmail.com>}
 * @version 1.0
 */

public class FieldFilterTest {
    private HttpSession session;
    private ServletServerHttpRequest serverHttpRequest;

    @Before
    public void initSession() {
        serverHttpRequest = MockHttpRequest.getMockAdminRequest();
        assertNotNull(serverHttpRequest);

        session = serverHttpRequest.getServletRequest().getSession();
        assertNotNull(session);
    }

    @Test
    public void testFieldFilterWithRequest() throws IllegalAccessException {
        MockUser user = new MockUser();
        MethodParameter methodParameter = MockMethods.findMethodParameterByName("singleAnnotation");
        assertNotNull(methodParameter);

        FieldFilter fieldFilter = new FieldFilter(serverHttpRequest, methodParameter);
        fieldFilter.filter(user);
        assertNotNull(user);
    }

    @Test
    public void testFieldFilterWithSession() throws IllegalAccessException {
        MockUser user = new MockUser();
        MethodParameter methodParameter = MockMethods.findMethodParameterByName("singleAnnotation");
        assertNotNull(methodParameter);
        FieldFilter fieldFilter = new FieldFilter(session, methodParameter);
        fieldFilter.filter(user);
        assertNotNull(user);
    }
}
