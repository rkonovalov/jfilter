package com.jfilter.components;

import com.fasterxml.jackson.databind.*;
import com.jfilter.converter.FilterClassWrapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class FilterConverter extends AbstractHttpMessageConverter<Object> {
    private FilterConfiguration filterConfiguration;

    public FilterConverter(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Serializable.class.isAssignableFrom(clazz);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return filterConfiguration.supportedMediaTypes();
    }

    @Override
    public boolean canRead(Class clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class clazz, MediaType mediaType) {
        return filterConfiguration.isEnabled() &&
                (filterConfiguration.supportedMediaTypes().indexOf(mediaType) >= 0 || Objects.isNull(mediaType));
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        MediaType contentType = outputMessage.getHeaders().getContentType();


        //If object is FilterClassWrapper try to serialize object using filters(if configured)
        if (object instanceof FilterClassWrapper) {
            FilterClassWrapper wrapper = (FilterClassWrapper) object;

            //Retrieving ObjectMapper from ObjectMapperCache
            ObjectMapper objectMapper = filterConfiguration.getObjectMapperCache()
                    .findObjectMapper(wrapper.getMethodParameterDetails());

            //Serialize object with ObjectMapper
            objectMapper.writeValue(outputMessage.getBody(), wrapper.getObject());
        } else {
            //Otherwise try to serialize object without filters by default ObjectMapper from filterConfiguration
            ObjectMapper objectMapper = filterConfiguration.getMapper(contentType);
            objectMapper.writeValue(outputMessage.getBody(), object);
        }
    }
}
