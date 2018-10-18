
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

import mock.MockMethods;
import mock.MockUser;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import static org.junit.Assert.assertNotNull;

/**
 * @author Ruslan {@literal <rkonovalov86@gmail.com>}
 * @version 1.0
 */

public class FieldFilterTest {

    @Test
    public void testJsonIgnore() throws IllegalAccessException {
        MockUser user = new MockUser();
        MethodParameter methodParameter = MockMethods.findMethodParameterByName("singleAnnotation");
        assertNotNull(methodParameter);

        FieldFilter fieldFilter = new FieldFilter(null, methodParameter);
        fieldFilter.jsonIgnore(user);
        assertNotNull(user);
    }
}
