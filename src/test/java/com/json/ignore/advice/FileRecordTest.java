package com.json.ignore.advice;

import com.json.ignore.filter.file.FileFilter;
import org.junit.Before;
import com.json.ignore.advice.FileWatcher.FileRecord;
import org.junit.Test;

import java.io.File;
import static org.junit.Assert.*;

public class FileRecordTest {
    private FileWatcher.FileRecord fileRecord;
    private File file;
    private String eventResult;

    @Before
    public void init() {
        file = FileFilter.resourceFile("config.xml");
        assertNotNull(file);

        fileRecord = new FileRecord(file, f -> eventResult = f.getAbsoluteFile().toString());
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
        FileRecord fileRecord2 = new FileRecord(file, null);
        boolean result = fileRecord2.onEvent();

        assertFalse(result);
    }

}
