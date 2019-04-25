package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.jfilter.FilterConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_XML;

/**
 * This class give access to extending of ObjectMapper lists and control of filter functionality
 */
@Component
public class FilterConfiguration {
    private boolean enabled;
    private ConcurrentMap<MediaType, ObjectMapper> mapperList;
    private ObjectMapperCache objectMapperCache;
    private boolean defaultSerializers;
    private boolean dateTimeModule;

    public FilterConfiguration() {
        mapperList = new ConcurrentHashMap<>();
        configureDefaultMappers();
        defaultSerializers = true;
        dateTimeModule = true;
    }

    /**
     * Method adds default Json and Xml ObjectMappers
     */
    private void configureDefaultMappers() {
        //Default JSON mappers
        setMapper(APPLICATION_JSON, Jackson2ObjectMapperBuilder.json().build());
        setMapper(APPLICATION_JSON_UTF8, Jackson2ObjectMapperBuilder.json().build());
        setMapper(new MediaType(MEDIA_TYPE_APPLICATION, MEDIA_SUB_TYPE_JSON, Charset.defaultCharset()), Jackson2ObjectMapperBuilder.json().build());
        setMapper(new MediaType(MEDIA_TYPE_APPLICATION, MEDIA_SUB_TYPE_JSON2), Jackson2ObjectMapperBuilder.json().build());

        //Default XML mappers
        setMapper(APPLICATION_XML, Jackson2ObjectMapperBuilder.xml().build());
        setMapper(new MediaType(MEDIA_TYPE_APPLICATION, MEDIA_SUB_TYPE_XML, Charset.defaultCharset()), Jackson2ObjectMapperBuilder.xml().build());
        setMapper(new MediaType(MEDIA_TYPE_APPLICATION, MEDIA_SUB_TYPE_XML2), Jackson2ObjectMapperBuilder.xml().build());
        setMapper(new MediaType(MEDIA_TYPE_APPLICATION, MEDIA_SUB_TYPE_XML), Jackson2ObjectMapperBuilder.xml().build());
    }

    /**
     * Set of ObjectMapperProvider {@link ObjectMapperCache}
     *
     * @param objectMapperCache object mapper provider which contents list of generated ObjectMappers
     * @return {@link FilterConfiguration}
     */
    @Autowired
    @SuppressWarnings("unused")
    private FilterConfiguration setObjectMapperCache(ObjectMapperCache objectMapperCache) {
        this.objectMapperCache = objectMapperCache;
        return this;
    }

    /**
     * Set of WebApplicationContext {@link WebApplicationContext}
     *
     * @param webApplicationContext application context
     * @return {@link FilterConfiguration}
     */
    @Autowired
    @SuppressWarnings("unused")
    private FilterConfiguration setWebApplicationContext(WebApplicationContext webApplicationContext) {
        /*
         * Important! For enabling filtration, should be specified one of application bean with EnableJsonFilter annotation
         */
        enabled = FilterProvider.isFilterEnabled(webApplicationContext);
        return this;
    }

    /**
     * Method finds ObjectMapper by MediaType
     *
     * @param mediaType {@link MediaType} media type of http message
     * @return {@link ObjectMapper} ObjectMapper if found, otherwise returns null
     */
    protected ObjectMapper getMapper(MediaType mediaType) {
        return mapperList.get(mediaType);
    }

    /**
     * Add or update ObjectMapper by MediaType
     *
     * @param mediaType    {@link MediaType} media type of http message
     * @param objectMapper {@link ObjectMapper} ObjectMapper
     */
    @SuppressWarnings("WeakerAccess")
    public void setMapper(MediaType mediaType, ObjectMapper objectMapper) {
        mapperList.put(mediaType, objectMapper);
    }

    /**
     * Returns supported media types
     *
     * @return {@link List}, {@link MediaType} list of supported media types
     */
    protected List<MediaType> supportedMediaTypes() {
        return new ArrayList<>(mapperList.keySet());
    }

    /**
     * Returns ObjectMapperProvider
     *
     * @return {@link ObjectMapperCache}
     */
    protected ObjectMapperCache getObjectMapperCache() {
        return objectMapperCache;
    }

    /**
     * Returns state of filter
     * <p>
     * True if filter enabled, false if not
     *
     * @return {@link Boolean}
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Change state of filter
     * If true the filter is working, else filter stops working
     *
     * @param enabled {@link Boolean}
     * @return instance of {@link FilterConfiguration}
     */
    public FilterConfiguration setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Enabling/disabling using of default Serializers
     *
     * @param defaultSerializers if true {@link FilterObjectMapper} will add default Serializers to ObjectMappers, otherwise not
     * @return instance of {@link FilterConfiguration}
     */
    public FilterConfiguration enableDefaultSerializers(boolean defaultSerializers) {
        this.defaultSerializers = defaultSerializers;
        return this;
    }

    /**
     * Enabling/disabling using of JavaDateTimeModule in ObjectMappers
     *
     * @param dateTimeModule if true {@link FilterObjectMapper} will add JavaTimeModule to ObjectMappers, otherwise not
     * @return instance of {@link FilterConfiguration}
     */
    public FilterConfiguration enableDateTimeModule(boolean dateTimeModule) {
        this.dateTimeModule = dateTimeModule;
        return this;
    }

    /**
     * Get using of default Serializers state
     *
     * @return true if adding is enabled, otherwise false
     */
    public boolean isDefaultSerializers() {
        return defaultSerializers;
    }

    /**
     * Get using of JavaDateTimeModule state
     *
     * @return true if using is enabled, otherwise false
     */
    public boolean isDateTimeModule() {
        return dateTimeModule;
    }
}


