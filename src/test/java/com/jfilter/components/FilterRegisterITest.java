package com.jfilter.components;

import com.jfilter.mapper.FilterObjectMapper;
import com.jfilter.mapper.FilterXmlMapper;
import com.jfilter.mock.config.WSConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Component
public class FilterRegisterITest {
    private FilterRegister filterRegister;
    private RequestMappingHandlerAdapter handlerAdapter;
    private static final List<Object> registeredConverters = new ArrayList<>();
    private static final AtomicBoolean changed = new AtomicBoolean(false);

    @Autowired(required = false)
    public FilterRegisterITest setFilterRegister(FilterRegister filterRegister) {
        this.filterRegister = filterRegister;
        return this;
    }

    @Autowired(required = false)
    public FilterRegisterITest setHandlerAdapter(RequestMappingHandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
        return this;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        FilterRegisterITest.registeredConverters.clear();
        FilterRegisterITest.registeredConverters.addAll(handlerAdapter.getMessageConverters());
        FilterRegisterITest.changed.set(true);
    }

    private boolean beanFilterConverterLoaded() {
        final AtomicBoolean result = new AtomicBoolean(false);
        FilterRegisterITest.registeredConverters.forEach(i -> {
            if (i instanceof FilterConverter) {
                result.set(true);
            } else if (i instanceof MappingJackson2HttpMessageConverter &&
                    ((MappingJackson2HttpMessageConverter) i).getObjectMapper() instanceof FilterObjectMapper) {
                result.set(true);
            } else if (i instanceof MappingJackson2XmlHttpMessageConverter &&
                    ((MappingJackson2XmlHttpMessageConverter) i).getObjectMapper() instanceof FilterXmlMapper) {
                result.set(true);
            }
        });
        return result.get();
    }

    @Test
    public void testConfigureMessageConvertersEnabled() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
        await().atMost(5, TimeUnit.SECONDS).untilTrue(FilterRegisterITest.changed);
        boolean contain = FilterRegisterITest.registeredConverters.size() > 2 && beanFilterConverterLoaded();
        assertTrue(contain);
    }

    @Test
    public void testConfigureMessageConvertersDisabled() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_DISABLED_FILTERED, this);
        filterRegister.configureMessageConverters(new ArrayList<>());
        await().atMost(5, TimeUnit.SECONDS).untilTrue(FilterRegisterITest.changed);
        System.out.println(FilterRegisterITest.registeredConverters.size());
        boolean contain = beanFilterConverterLoaded();
        assertFalse(contain);
    }
}
