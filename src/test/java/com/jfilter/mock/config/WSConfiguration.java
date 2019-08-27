package com.jfilter.mock.config;

import com.jfilter.EnableJsonFilter;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

public class WSConfiguration {

    public enum Instance {
        FILTER_ENABLED(Enabled.class),
        FILTER_ENABLED2(Enabled2.class),
        FILTER_DISABLED(Disabled.class),
        FILTER_DISABLED_FILTERED(DisabledFiltered .class),
        FILTER_USE_DEFAULT_CONVERTERS(UseDefaultConverters .class),
        FILTER_USE_CUSTOM_CONVERTERS(UseCustomConverters .class);

        private final Class className;

        Instance(Class clazz) {
            this.className = clazz;
        }
    }

    public static void instance(Instance testClass, Object testInstance) throws Exception {
        TestContextManager testContextManager = new TestContextManager(testClass.className);
        testContextManager.prepareTestInstance(testInstance);
    }

    @ContextConfiguration(classes = WSConfigurationEnabled.class)
    @WebAppConfiguration
    @RunWith(SpringJUnit4ClassRunner.class)
    @EnableJsonFilter
    private class Enabled {

    }

    @ContextConfiguration(classes = WSConfigurationEnabled.class)
    @WebAppConfiguration
    @RunWith(SpringJUnit4ClassRunner.class)
    @EnableJsonFilter
    private class Enabled2 {

    }

    @ContextConfiguration(classes = WSConfigurationDisabled.class)
    @WebAppConfiguration
    @RunWith(SpringJUnit4ClassRunner.class)
    private class Disabled {

    }

    @ContextConfiguration(classes = WSConfigurationDisabledFiltered.class)
    @WebAppConfiguration
    @RunWith(SpringJUnit4ClassRunner.class)
    private class DisabledFiltered {

    }

    @ContextConfiguration(classes = WSUseDefaultConvertersEnabled.class)
    @WebAppConfiguration
    @RunWith(SpringJUnit4ClassRunner.class)
    private class UseDefaultConverters {

    }

    @ContextConfiguration(classes = WSConfigurationCustomConverter.class)
    @WebAppConfiguration
    @RunWith(SpringJUnit4ClassRunner.class)
    private class UseCustomConverters {

    }
}
