package com.json.ignore.advice;

import com.json.ignore.mock.config.WSConfigurationEnabled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.io.IOException;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class FileWatcherScheduleITest {

    private FileWatcher fileWatcher;
    private ScheduledAnnotationBeanPostProcessor taskScheduler;

    @Autowired
    public FileWatcherScheduleITest setFileWatcher(FileWatcher fileWatcher) {
        this.fileWatcher = fileWatcher;
        return this;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public FileWatcherScheduleITest setTaskScheduler(ScheduledAnnotationBeanPostProcessor taskScheduler) {
        this.taskScheduler = taskScheduler;
        return this;
    }

    @Test
    public void testClose() throws IOException {
        fileWatcher.destroy();
        assertTrue(fileWatcher.isClosed());
    }

    @Test
    public void testWatchSchedulerInterruptedException() {
        taskScheduler.destroy();
        await().atMost(5, SECONDS).until(() -> fileWatcher.isClosed());
        assertTrue(fileWatcher.isClosed());
    }

    @Test
    public void testWatchSchedulerClosedException() throws IOException {
        fileWatcher.getWatcher().close();
        await().atMost(5, SECONDS).until(() -> fileWatcher.isClosed());
        assertTrue(fileWatcher.isClosed());
    }
}
