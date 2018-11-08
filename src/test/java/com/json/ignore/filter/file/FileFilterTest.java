package com.json.ignore.filter.file;

import com.json.ignore.filter.FilterFields;
import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import org.junit.Assert;
import org.junit.Test;

public class FileFilterTest {

    @Test
    public void testMethodWithoutAnnotations() {
        FileFilter fileFilter = new FileFilter(MockMethods.methodWithoutAnnotations());
        FilterFields filterFields = fileFilter.getIgnoreList(MockClasses.getUserMock(), MockHttpRequest.getMockAdminRequest());
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testMethodNotExistFile() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileNotExist());
        FilterFields filterFields = fileFilter.getIgnoreList(MockClasses.getUserMock(), MockHttpRequest.getMockAdminRequest());
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testMethodBadConfig() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileBadConfig());
        FilterFields filterFields = fileFilter.getIgnoreList(MockClasses.getUserMock(), MockHttpRequest.getMockAdminRequest());
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

}
