package com.json.ignore.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class intercepts all responses of Spring Web Service which returns objects instance FilterClassWrapper
 */
public abstract class FilterConverter implements HttpMessageConverter<Object> {
    private final List<MediaType> supportedMedia;

    /**
     * Constructor
     */
    protected FilterConverter() {
        supportedMedia = new ArrayList<>();
    }

    protected abstract ObjectMapper getObjectMapper();

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
     * Attempt to find supported media type in supportedMedia
     *
     * @param aClass    class name
     * @param mediaType {@link MediaType} media type
     * @return true if converter supports to serialize object instance of aClass and specified media type, else false
     */
    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return supportedMedia.indexOf(mediaType) >= 0;
    }

    /**
     * List of supported media types
     *
     * @return {@link List} and {@link MediaType}
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return supportedMedia;
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
        if (object instanceof FilterClassWrapper) {
            FilterClassWrapper wrapper = (FilterClassWrapper) object;
            ConverterMapper converterMapper = getIgnoreMapper(wrapper);
            httpOutputMessage.getBody().write(converterMapper.getMapper().writeValueAsBytes(wrapper.getObject()));
        } else {
            httpOutputMessage.getBody().write(getObjectMapper().writeValueAsBytes(object));
        }
    }

    private ConverterMapper getIgnoreMapper(FilterClassWrapper object) {
        return new ConverterMapper(getObjectMapper(), object.getIgnoreList());
    }
}
