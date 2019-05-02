package com.jfilter.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

public class ComparatorTest {
    private static final String ATTRIBUTE_TEST1 = "TEST1";
    private static final String ATTRIBUTE_TEST2 = "TEST2";
    private static final String ATTRIBUTE_UNKNOWN = "UNKNOWN";

    private HttpSession session;

    @Before
    public void init() {
        session = new MockHttpSession();
        session.setAttribute(ATTRIBUTE_TEST1, "result_1");
        session.setAttribute(ATTRIBUTE_TEST2, "result_2");
    }

    @Test
    public void testComparatorFirst() {

        Object result = Comparator.of(session)
                .match((s -> s.getAttribute(ATTRIBUTE_TEST1) != null), (s -> s.getAttribute(ATTRIBUTE_TEST1)))
                .get();

        Assert.assertEquals("result_1", result);
    }

    @Test
    public void testComparatorSecond() {

        Object result = Comparator.of(session)
                .match((s -> s.getAttribute(ATTRIBUTE_TEST2) != null), (s -> s.getAttribute(ATTRIBUTE_TEST2)))
                .get();

        Assert.assertEquals("result_2", result);
    }

    @Test
    public void testComparatorFirstMultiple() {

        Object result = Comparator.of(session)
                .match((s -> s.getAttribute(ATTRIBUTE_TEST1) != null), (s -> s.getAttribute(ATTRIBUTE_TEST1)))
                .match((s -> s.getAttribute(ATTRIBUTE_TEST2) != null), (s -> s.getAttribute(ATTRIBUTE_TEST2)))
                .get();

        Assert.assertEquals("result_1", result);
    }

    @Test
    public void testComparatorUnknown() {


        Object result = Comparator.of(session)
                .match((s -> s.getAttribute(ATTRIBUTE_UNKNOWN) != null), (s -> s.getAttribute(ATTRIBUTE_UNKNOWN)))
                .get();

        Assert.assertNull(result);
    }

    @Test
    public void testComparatorDefaultOnNull() {
        Object result = Comparator.of(session)
                .match((s -> s.getAttribute(ATTRIBUTE_UNKNOWN) != null), (s -> s.getAttribute(ATTRIBUTE_UNKNOWN)))
                .orElse("DEFAULT");

        Assert.assertEquals("DEFAULT", result);
    }

    @Test
    public void testComparatorNull() {
        HttpSession nullSession = null;
        Object result = Comparator.of(nullSession)
                .match((s -> s.getAttribute(ATTRIBUTE_TEST1) != null), (s -> s.getAttribute(ATTRIBUTE_TEST1)))
                .orElse("DEFAULT");

        Assert.assertEquals("DEFAULT", result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testComparatorNullPredicate() {
        Object result = Comparator.of(session)
                .match(null, (s -> s.getAttribute(ATTRIBUTE_TEST1)))
                .orElse("DEFAULT");
        Assert.assertEquals("DEFAULT", result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testComparatorNullMapper() {
        Object result = Comparator.of(session)
                .match((s -> s.getAttribute(ATTRIBUTE_TEST1) != null), null)
                .orElse("DEFAULT");

        Assert.assertEquals("DEFAULT", result);
    }
}
