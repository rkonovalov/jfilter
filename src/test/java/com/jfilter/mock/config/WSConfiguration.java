package com.jfilter.mock.config;

import com.jfilter.EnableJsonFilter;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

public class WSConfiguration {

    public enum Instance {
        FILTER_ENABLED(Enabled.class),
        FILTER_DISABLED(Disabled.class),
        FILTER_DISABLED_FILTERED(DisabledFiltered .class);

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
    @WebAppConfiguration("src/main/resources")
    @RunWith(SpringRunner.class)
    @EnableJsonFilter
    private class Enabled {

    }

    @ContextConfiguration(classes = WSConfigurationDisabled.class)
    @WebAppConfiguration("src/main/resources")
    @RunWith(SpringRunner.class)
    private class Disabled {

    }

    @ContextConfiguration(classes = WSConfigurationDisabledFiltered.class)
    @WebAppConfiguration("src/main/resources")
    @RunWith(SpringRunner.class)
    private class DisabledFiltered {

    }
}
