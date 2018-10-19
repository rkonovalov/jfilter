package com.json.ignore.filter.file;

import mock.MockClasses;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class FileConfigTest {

    @Test
    public void testInitAdminFileConfig() {
        FileConfig adminFileConfig = MockClasses.getMockAdminFileConfig();
        assertNotNull(adminFileConfig);
    }
}
