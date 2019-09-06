package com.jfilter.filter;

import com.jfilter.FilterException;
import com.jfilter.mock.MockClasses;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockMethods;
import com.jfilter.request.RequestSession;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
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

    @Test
    public void testFileAnnotationEmpty() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileAnnotationEmpty());
        FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                new RequestSession(MockHttpRequest.getMockAdminRequest()));

        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test(expected = FilterException.class)
    public void testIOException() throws IOException {
        String fileName = FileFilter.getFileName("config_io_exception.xml");
        File file = new File(fileName);

        try (FileOutputStream in = new FileOutputStream(file)) {
            java.nio.channels.FileLock lock = in.getChannel().lock();
            try {
                FileFilter fileFilter = new FileFilter(MockMethods.fileLocked());
                FilterFields filterFields = fileFilter.getFields(MockClasses.getUserMock(),
                        new RequestSession(MockHttpRequest.getMockAdminRequest()));

                Assert.assertEquals(0, filterFields.getFieldsMap().size());
            } finally {
                lock.release();
            }
        }
    }
}
