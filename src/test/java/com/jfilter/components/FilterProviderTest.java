package com.jfilter.components;

import com.jfilter.filter.BaseFilter;
import com.jfilter.mock.MockMethods;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class FilterProviderTest {
    private FilterProvider filterProvider;
    private MethodParameter fileAnnotationMethod;
    private MethodParameter fileAnnotationYamlMethod;
    private MethodParameter methodWithoutAnnotationsMethod;

    @Before
    public void init() {
        filterProvider = new FilterProvider();

        fileAnnotationMethod = MockMethods.fileAnnotation(null);
        fileAnnotationYamlMethod = MockMethods.fileAnnotationYaml(null);
        assertNotNull(fileAnnotationMethod);
        assertNotNull(fileAnnotationYamlMethod);

        methodWithoutAnnotationsMethod = MockMethods.methodWithoutAnnotations(null);
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

    @Test
    public void testCacheSizeGreaterZeroYaml() {
        BaseFilter filter = filterProvider.getFilter(fileAnnotationYamlMethod);
        assertNotNull(filter);

        assertTrue(filterProvider.cacheSize() > 0);
    }

    @Test
    public void testCacheSizeZeroYaml() {
        BaseFilter filter = filterProvider.getFilter(fileAnnotationYamlMethod);
        assertNotNull(filter);

        filterProvider.clearCache();
        assertEquals(0, filterProvider.cacheSize());
    }


}
