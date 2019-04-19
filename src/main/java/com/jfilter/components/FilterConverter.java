package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.converter.FilterClassWrapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * This class intercepts all responses of Spring Web Service which returns objects instance FilterClassWrapper
 */
public class FilterConverter implements HttpMessageConverter<Object> {
    private FilterConfiguration filterConfiguration;

    /**
     * Creates a new instance of the {@link FilterConverter} class
     *
     * @param filterConfiguration {@link FilterConfiguration}
     */
    public FilterConverter(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
    }

    /**
     * Returns ability to deserialize.
     * This functionality is disabled in current converter
     *
     * @param aClass    class name
     * @param mediaType {@link MediaType} media type
     * @return true if converter supports to deserialize object instance of aClass
     */
    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return false;
    }

    /**
     * Returns ability to serialize.
     * Attempt to find supported media type in supported media  type list of configuration
     *
     * @param aClass    class name
     * @param mediaType {@link MediaType} media type
     * @return true if converter supports to serialize object instance of aClass and specified media type, else false
     */
    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return filterConfiguration.isEnabled() &&
                (filterConfiguration.supportedMediaTypes().indexOf(mediaType) >= 0 || Objects.isNull(mediaType));
    }

    /**
     * List of supported media types from configuration
     *
     * @return {@link List} and {@link MediaType}
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return filterConfiguration.supportedMediaTypes();
    }

    /**
     * Deserialize object from HttpInputMessage.
     * This functionality is disabled in current converter
     *
     * @param aClass           class name
     * @param httpInputMessage {@link HttpInputMessage}
     * @return {@link Serializable}
     * @throws HttpMessageNotReadableException exception
     */
    @Override
    public FilterClassWrapper read(Class<?> aClass, HttpInputMessage httpInputMessage) throws HttpMessageNotReadableException {
        return null;
    }

    /**
     * Serialize FilterClassWrapper
     *
     * @param object            instance of {@link Serializable}
     * @param mediaType         {@link MediaType}
     * @param httpOutputMessage HttpOutputMessage
     * @throws HttpMessageNotWritableException exception
     * @throws IOException                     exception
     */
    @Override
    public void write(Object object, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws HttpMessageNotWritableException, IOException {
        httpOutputMessage.getHeaders().setContentType(mediaType);

        //If object is FilterClassWrapper try to serialize object using filters(if configured)
        if (object instanceof FilterClassWrapper) {
            FilterClassWrapper wrapper = (FilterClassWrapper) object;

            //Retrieving ObjectMapper from ObjectMapperProvider
            ObjectMapper objectMapper = filterConfiguration.getObjectMapperCache()
                    .findObjectMapper(wrapper.getMethodParameterDetails());

            //Serialize object with ObjectMapper
            httpOutputMessage.getBody().write(objectMapper.writeValueAsBytes(wrapper.getObject()));
        } else {
            //Otherwise try to serialize object without filters by default ObjectMapper from filterConfiguration
            ObjectMapper objectMapper = filterConfiguration.getMapper(mediaType);
            httpOutputMessage.getBody().write(objectMapper.writeValueAsBytes(object));
        }
    }
}
