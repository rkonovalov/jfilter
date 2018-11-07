package com.json.ignore.advice;

import com.json.ignore.filter.BaseFilter;
import com.json.ignore.mock.MockMethods;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import static org.junit.Assert.*;

public class FilterProviderTest {
    private FilterProvider filterProvider;
    private MethodParameter fileAnnotationMethod;
    private MethodParameter methodWithoutAnnotationsMethod;

    @Before
    public void init() {
        filterProvider = new FilterProvider();

        fileAnnotationMethod = MockMethods.fileAnnotation();
        assertNotNull(fileAnnotationMethod);

        methodWithoutAnnotationsMethod = MockMethods.methodWithoutAnnotations();
        assertNotNull(methodWithoutAnnotationsMethod);
    }

    @Test
    public void testIsAcceptFalse() {
        boolean result = filterProvider.isAccept(methodWithoutAnnotationsMethod);
        assertFalse(result);
    }

    @Test
    public void testCacheSizeGreaterZero() {
        BaseFilter filter = filterProvider.getFilter(fileAnnotationMethod);
        assertNotNull(filter);

        assertTrue(filterProvider.cacheSize() > 0);
    }

    @Test
    public void testCacheSizeZero() {
        BaseFilter filter = filterProvider.getFilter(fileAnnotationMethod);
        assertNotNull(filter);

        filterProvider.clearCache();
        assertEquals(0, filterProvider.cacheSize());
    }


}
