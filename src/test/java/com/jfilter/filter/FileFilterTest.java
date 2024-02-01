package com.jfilter.filter;

import com.jfilter.FilterException;
import com.jfilter.mock.MockClassesHelper;
import com.jfilter.mock.MockHttpRequestHelper;
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
        FileFilter fileFilter = new FileFilter(MockMethods.methodWithoutAnnotations(null));
        FilterFields filterFields = fileFilter.getFields(MockClassesHelper.getUserMock(),
                new RequestSession(MockHttpRequestHelper.getMockAdminRequest()));
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testMethodNotExistFile() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileNotExist(null));
        FilterFields filterFields = fileFilter.getFields(MockClassesHelper.getUserMock(),
                new RequestSession(MockHttpRequestHelper.getMockAdminRequest()));
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testMethodBadConfig() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileBadConfig(null));
        FilterFields filterFields = fileFilter.getFields(MockClassesHelper.getUserMock(),
                new RequestSession(MockHttpRequestHelper.getMockAdminRequest()));
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test
    public void testFileAnnotationClassNotFound() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileAnnotationClassNotFound(null));
        FilterFields filterFields = fileFilter.getFields(MockClassesHelper.getUserMock(),
                new RequestSession(MockHttpRequestHelper.getMockAdminRequest()));
        Assert.assertEquals(Arrays.asList("id", "password"), filterFields.getFieldsMap().get(null));
    }

    @Test
    public void testFileAnnotationEmpty() {
        FileFilter fileFilter = new FileFilter(MockMethods.fileAnnotationEmpty(null));
        FilterFields filterFields = fileFilter.getFields(MockClassesHelper.getUserMock(),
                new RequestSession(MockHttpRequestHelper.getMockAdminRequest()));

        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }

    @Test(expected = FilterException.class)
    public void testIOException() throws IOException {
        String fileName = FileFilter.getFileName("config_io_exception.xml");
        File file = new File(fileName);

        try (FileOutputStream in = new FileOutputStream(file)) {
            java.nio.channels.FileLock lock = in.getChannel().lock();
            try {
                FileFilter fileFilter = new FileFilter(MockMethods.fileLocked(null));
                FilterFields filterFields = fileFilter.getFields(MockClassesHelper.getUserMock(),
                        new RequestSession(MockHttpRequestHelper.getMockAdminRequest()));

                Assert.assertEquals(0, filterFields.getFieldsMap().size());
            } finally {
                lock.release();
            }
        }
    }
}
