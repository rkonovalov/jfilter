package com.jfilter.components;

import com.jfilter.FilterException;
import com.jfilter.filter.BaseFilter;
import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockClassesHelper;
import com.jfilter.mock.MockHttpRequestHelper;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.MockUtils;
import com.jfilter.mock.config.WSConfigurationHelper;
import com.jfilter.request.RequestSession;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import static com.jfilter.filter.FileFilter.resourceFile;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Component
public class FileWatcherModifyITest {
    private FileWatcher fileWatcher;
    private AtomicBoolean modified;
    private File file;
    private FilterProvider filterProvider;

    @Autowired
    public void setFileWatcher(FileWatcher fileWatcher) {
        this.fileWatcher = fileWatcher;
    }

    @Autowired
    public FileWatcherModifyITest setFilterProvider(FilterProvider filterProvider) {
        this.filterProvider = filterProvider;
        return this;
    }

    @Before
    public void init() throws Exception {
        WSConfigurationHelper.instance(WSConfigurationHelper.Instance.FILTER_ENABLED, this);

        modified = new AtomicBoolean(false);

        file = resourceFile("config_io_exception.xml");
        assertNotNull(file);

        boolean add = fileWatcher.add(file, (f) -> modified.set(true));
        assertTrue(add);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testFileIsModifiedTrue() {
        file.setLastModified(new Date().getTime() + 5000);
        await().atMost(5, SECONDS).until(() -> modified.get());

        assertTrue(modified.get());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testFileIsModifiedFalse() {
        /*
           Try to modify file, result should be modified
         */
        file.setLastModified(new Date().getTime() + 5000);
        await().atMost(5, SECONDS).until(() -> modified.get());

        assertTrue(modified.get());

        /*
           Try to modify file, result should be NOT modified, because lastModify date lass than the file modify date
         */
        modified.set(false);
        file.setLastModified(new Date().getTime() - 1000);
        try {
            await().atMost(5, SECONDS).until(() -> modified.get());
        } catch (ConditionTimeoutException e) {
            modified.set(false);
        }

        assertFalse(modified.get());
    }


    @Test
    public void testDynamicChangeError() throws FilterException {
        boolean copyResult = MockUtils.fileCopy("config.xml", "config_dynamic.xml");
        assertTrue(copyResult);

        MethodParameter methodParameter = MockMethods.fileFilterDynamic(null);
        RequestSession request = new RequestSession(MockHttpRequestHelper.getMockUserRequest());

        BaseFilter filter = filterProvider.getFilter(methodParameter);
        FilterFields filterFields = filter.getFields(MockClassesHelper.getUserMock(), request);

        assertEquals(2, filterFields.getFields(MockUser.class).size());

        MockUtils.fileWrite(resourceFile("config_dynamic.xml"), "bad content");

        MockUtils.sleep(10);

        filterFields = filter.getFields(MockClassesHelper.getUserMock(), request);

        assertEquals(2, filterFields.getFields(MockUser.class).size());
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testDynamicChange() throws FilterException {
        boolean copyResult = MockUtils.fileCopy("config.xml", "config_dynamic.xml");
        assertTrue(copyResult);

        MethodParameter methodParameter = MockMethods.fileFilterDynamic(null);
        RequestSession request = new RequestSession(MockHttpRequestHelper.getMockUserRequest());

        BaseFilter filter = filterProvider.getFilter(methodParameter);
        FilterFields filterFields = filter.getFields(MockClassesHelper.getUserMock(), request);

        assertEquals(2, filterFields.getFields(MockUser.class).size());

        File file = resourceFile("config_dynamic.xml");

        if (file != null)
            file.setLastModified(new Date().getTime() + 1000);


        MockUtils.sleep(10);

        filterFields = filter.getFields(MockClassesHelper.getUserMock(), request);

        assertEquals(2, filterFields.getFields(MockUser.class).size());

    }
}
