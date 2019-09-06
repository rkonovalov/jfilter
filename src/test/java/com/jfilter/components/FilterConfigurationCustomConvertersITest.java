package com.jfilter.components;


import com.jfilter.mock.config.WSConfigurationHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static junit.framework.TestCase.assertEquals;

@Component
public class FilterConfigurationCustomConvertersITest {
    private FilterConfiguration filterConfiguration;

    @Autowired(required = false)
    public FilterConfigurationCustomConvertersITest setFilterProvider(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
        return this;
    }

    @Before
    public void init() throws Exception {
        WSConfigurationHelper.instance(WSConfigurationHelper.Instance.FILTER_USE_CUSTOM_CONVERTERS, this);
    }

    @Test
    public void testCustomControllersSize() {
        assertEquals(2, filterConfiguration.getCustomConverters().size());
    }
}
