
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

package mock;

import java.util.*;

/**
 * @author Ruslan {@literal <rkonovalov86@gmail.com>}
 * @version 1.0
 */

public class MockClasses {

    public static MockUser getUserMock() {
        MockUser mockUser = new MockUser();

        Map<String, String> values = new HashMap<>();
        values.put("name", "value");

        List<String> collection = new ArrayList<>(Arrays.asList("Hello", "World"));

        mockUser.setId(100)
                .setEmail("mail@mail.com")
                .setFullName("Jane Doe")
                .setPassword("1234567")
                .setId(100)
                .setCollectionValue(collection)
                .setMapValue(values)
                .setBoolValue(true)
                .setByteValue((byte) 100)
                .setCharValue('c')
                .setDoubleValue(5.5)
                .setFloatValue(5.5f)
                .setLongValue(100500)
                .setShortValue((short) 15);

        return mockUser;
    }
}
