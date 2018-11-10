package com.jfilter.filter.file;

import com.jfilter.FilterException;
import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockClasses;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockMethods;
import com.jfilter.request.RequestSession;
import org.junit.Assert;
import org.junit.Test;
import java.io.*;
import java.util.Arrays;

public class FileFilterTest {

    @Test
    public void testMethodWithoutAnnotations() {
        com.jfilter.filter.file.FileFilter fileFilter = new com.jfilter.filter.file.FileFilter(MockMethods.methodWithoutAnnotations());
        FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                new RequestSession(MockHttpRequest.getMockAdminRequest()));
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testMethodNotExistFile() {
        com.jfilter.filter.file.FileFilter fileFilter = new com.jfilter.filter.file.FileFilter(MockMethods.fileNotExist());
        FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                new RequestSession(MockHttpRequest.getMockAdminRequest()));
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testMethodBadConfig() {
        com.jfilter.filter.file.FileFilter fileFilter = new com.jfilter.filter.file.FileFilter(MockMethods.fileBadConfig());
        FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                new RequestSession(MockHttpRequest.getMockAdminRequest()));
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testFileAnnotationClassNotFound() {
        com.jfilter.filter.file.FileFilter fileFilter = new com.jfilter.filter.file.FileFilter(MockMethods.fileAnnotationClassNotFound());
        FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                new RequestSession(MockHttpRequest.getMockAdminRequest()));
        Assert.assertEquals(Arrays.asList("id", "password"), filterFields.getFieldsMap().get(null));
    }

    @Test
    public void testFileAnnotationEmpty() {
        com.jfilter.filter.file.FileFilter fileFilter = new com.jfilter.filter.file.FileFilter(MockMethods.fileAnnotationEmpty());
        FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                new RequestSession(MockHttpRequest.getMockAdminRequest()));

        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test(expected = FilterException.class)
    public void testIOException() throws IOException {
        String fileName = com.jfilter.filter.file.FileFilter.getFileName("config_io_exception.xml");
        File file = new File(fileName);

        try (FileOutputStream in = new FileOutputStream(file)) {
            java.nio.channels.FileLock lock = in.getChannel().lock();
            try {
                com.jfilter.filter.file.FileFilter fileFilter = new com.jfilter.filter.file.FileFilter(MockMethods.fileLocked());
                FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                        new RequestSession(MockHttpRequest.getMockAdminRequest()));

                Assert.assertEquals(0, filterFields.getFieldsMap().size());
            } finally {
                lock.release();
            }
        }
    }
}
