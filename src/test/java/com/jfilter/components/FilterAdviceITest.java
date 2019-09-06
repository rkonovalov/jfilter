package com.jfilter.components;

import com.jfilter.mock.MockMethods;
import com.jfilter.mock.config.WSConfigurationHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Component
public class FilterAdviceITest {
    private FilterAdvice filterAdvice;
    private FilterConfiguration filterConfiguration;

    @Autowired
    public FilterAdviceITest setFilterAdvice(FilterAdvice filterAdvice) {
        this.filterAdvice = filterAdvice;
        return this;
    }

    @Autowired
    public FilterAdviceITest setFilterConfiguration(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
        return this;
    }

    @Before
    public void init() throws Exception {
        WSConfigurationHelper.instance(WSConfigurationHelper.Instance.FILTER_ENABLED2, this);
        filterConfiguration.setEnabled(true);
    }

    @Test
    public void testNotNull() {
        assertNotNull(filterAdvice);
    }

    @Test
    public void testSupports() {
        boolean supports = filterAdvice.supports(MockMethods.singleAnnotation(), null);
        assertTrue(supports);
    }

    @Test
    public void testNotSupports() {
        filterConfiguration.setEnabled(false);
        boolean supports = filterAdvice.supports(MockMethods.singleAnnotation(), null);
        assertFalse(supports);
    }

}
