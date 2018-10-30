package com.json.ignore.filter.file;

import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class FileFilterTest {

    @Test
    public void testMethodWithoutAnnotations() {
        FileFilter fileFilter = new FileFilter(MockMethods.methodWithoutAnnotations());
        Map<Class, List<String>> ignoreList = fileFilter.getIgnoreList(MockClasses.getUserMock(), MockHttpRequest.getMockAdminRequest());
        Assert.assertEquals(0,ignoreList.size());
    }

    @Test
    public void testMethodNotExistFile() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileNotExist());
        Map<Class, List<String>> ignoreList = fileFilter.getIgnoreList(MockClasses.getUserMock(), MockHttpRequest.getMockAdminRequest());
        Assert.assertEquals(0,ignoreList.size());
    }

    @Test
    public void testMethodBadConfig() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileBadConfig());
        Map<Class, List<String>> ignoreList = fileFilter.getIgnoreList(MockClasses.getUserMock(), MockHttpRequest.getMockAdminRequest());
        Assert.assertEquals(0,ignoreList.size());
    }

}
