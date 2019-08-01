package com.jfilter.components;

import com.jfilter.mock.MockUtils;
import com.jfilter.mock.config.WSConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertTrue;

@Component
public class FilterRegisterDefaultConvertersITest {
    private FilterRegister filterRegister;
    private RequestMappingHandlerAdapter handlerAdapter;
    private static final List<Object> registeredConverters = new ArrayList<>();
    private static final AtomicBoolean changed = new AtomicBoolean(false);

    @Autowired(required = false)
    public FilterRegisterDefaultConvertersITest setFilterRegister(FilterRegister filterRegister) {
        this.filterRegister = filterRegister;
        return this;
    }

    @Autowired(required = false)
    public FilterRegisterDefaultConvertersITest setHandlerAdapter(RequestMappingHandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
        return this;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        MockUtils.copyConverters(FilterRegisterDefaultConvertersITest.registeredConverters, handlerAdapter);
        FilterRegisterDefaultConvertersITest.changed.set(true);
    }

    @Test
    public void testConfigureMessageConvertersEnabled() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_USE_DEFAULT_CONVERTERS, this);
        await().atMost(5, TimeUnit.SECONDS).untilTrue(FilterRegisterDefaultConvertersITest.changed);
        boolean contain = FilterRegisterDefaultConvertersITest.registeredConverters.size() >= 2 &&
                MockUtils.beanFilterConverterLoaded(FilterRegisterDefaultConvertersITest.registeredConverters);
        assertTrue(contain);
    }
}
