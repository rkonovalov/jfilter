package com.jfilter.mock;

import com.jfilter.components.FilterConverter;
import com.jfilter.filter.FileFilter;
import com.jfilter.mapper.FilterObjectMapper;
import com.jfilter.mapper.FilterXmlMapper;
import org.awaitility.core.ConditionTimeoutException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.awaitility.Awaitility.await;

public class MockUtils {

    @SuppressWarnings("UnusedReturnValue")
    public static boolean sleep(Integer timeout) {
        try {
            await().atMost(timeout, TimeUnit.SECONDS)
                    .untilTrue(new AtomicBoolean(false));
            return true;
        } catch (ConditionTimeoutException e) {
            return false;
        }
    }

    public static boolean fileCopy(String sourceName, String destinationName) {
        File sourceFile = FileFilter.resourceFile(sourceName);
        File destinationFile = FileFilter.resourceFile(destinationName);
        if (sourceFile == null || destinationFile == null)
            return false;

        if (sourceFile.exists() && destinationFile.exists()) {
            try {
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), REPLACE_EXISTING);
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean fileWrite(File file, String content) {
        try {
            if (file != null && file.exists()) {
                Files.write(file.toPath(), content.getBytes(), WRITE);
                return true;
            } else
                return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean beanFilterConverterLoaded(List<Object> registeredConverters) {
        final AtomicBoolean result = new AtomicBoolean(false);
        registeredConverters.forEach(i -> {
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

    public static void copyConverters(List<Object> registeredConverters, RequestMappingHandlerAdapter handlerAdapter) {
        registeredConverters.clear();
        registeredConverters.addAll(handlerAdapter.getMessageConverters());
    }


}
