package com.jfilter.components;

import com.jfilter.filter.FileFilter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FileRecordTest {
    private FileWatcher.FileRecord fileRecord;
    private File file;
    private String eventResult;

    @Before
    public void init() {
        file = FileFilter.resourceFile("config.xml");
        assertNotNull(file);

        fileRecord = new FileWatcher.FileRecord(file, f -> eventResult = f.getAbsoluteFile().toString());
    }

    @Test
    public void testGetLastModified() {
        assertEquals(file.lastModified(), fileRecord.getLastModified().longValue());
    }

    @Test
    public void testSetLastModified() {
        fileRecord.setLastModified(0L);
        assertEquals(0, fileRecord.getLastModified().longValue());
    }

    @Test
    public void testOnEvent() {
        fileRecord.onEvent();
        assertEquals(file.getAbsoluteFile().toString(), eventResult);
    }

    @Test
    public void testOnEventTrue() {
        boolean result = fileRecord.onEvent();
        assertTrue(result);
    }

    @Test
    public void testOnEventFalse() {
        FileWatcher.FileRecord fileRecord2 = new FileWatcher.FileRecord(file, null);
        boolean result = fileRecord2.onEvent();

        assertFalse(result);
    }

}
