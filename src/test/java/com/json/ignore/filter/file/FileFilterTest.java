package com.json.ignore.filter.file;

import com.json.ignore.filter.FilterFields;
import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.request.RequestSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class FileFilterTest {

    @Test
    public void testMethodWithoutAnnotations() {
        FileFilter fileFilter = new FileFilter(MockMethods.methodWithoutAnnotations());
        FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                new RequestSession(MockHttpRequest.getMockAdminRequest()));
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testMethodNotExistFile() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileNotExist());
        FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                new RequestSession(MockHttpRequest.getMockAdminRequest()));
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testMethodBadConfig() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileBadConfig());
        FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                new RequestSession(MockHttpRequest.getMockAdminRequest()));
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testFileAnnotationClassNotFound() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileAnnotationClassNotFound());
        FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                new RequestSession(MockHttpRequest.getMockAdminRequest()));
        Assert.assertEquals(Arrays.asList("id", "password"), filterFields.getFieldsMap().get(null));
    }

}
