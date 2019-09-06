package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.converter.SerializationConfig;
import com.jfilter.mapper.FilterObjectMapper;
import com.jfilter.mapper.FilterXmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import static com.jfilter.FilterConstantsHelper.MEDIA_SUB_TYPE_JSON;
import static com.jfilter.FilterConstantsHelper.MEDIA_SUB_TYPE_JSON2;
import static com.jfilter.FilterConstantsHelper.MEDIA_SUB_TYPE_XML;
import static com.jfilter.FilterConstantsHelper.MEDIA_SUB_TYPE_XML2;
import static com.jfilter.FilterConstantsHelper.MEDIA_TYPE_APPLICATION;
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
    private SerializationConfig serializationConfig;
    private boolean useDefaultConverters;
    private List<HttpMessageConverter<?>> customConverters;

    public FilterConfiguration() {
        mapperList = new ConcurrentHashMap<>();
        customConverters = new ArrayList<>();
        serializationConfig = new SerializationConfig();
        configureDefaultMappers();
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
    public ObjectMapperCache getObjectMapperCache() {
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

    public SerializationConfig getSerializationConfig() {
        return serializationConfig;
    }

    public boolean isUseDefaultConverters() {
        return useDefaultConverters;
    }

    public FilterConfiguration setUseDefaultConverters(boolean useDefaultConverters) {
        this.useDefaultConverters = useDefaultConverters;
        return this;
    }

    /**
     * Add custom converter extemded from AbstractJackson2HttpMessageConverter
     * Note: Message converter should contain FilterObjectMapper or FilterXmlMapper in objectMapper property for correct filtering
     *
     * @param converter instance of message converter extended from {@link AbstractJackson2HttpMessageConverter}
     * @param <T>       {@link AbstractJackson2HttpMessageConverter}
     * @return instance of {@link FilterConfiguration}
     */
    public <T extends AbstractJackson2HttpMessageConverter> FilterConfiguration withCustomConverter(T converter) {
        if (converter == null)
            throw new IllegalArgumentException("Converter coudn't be null");

        if (!(converter.getObjectMapper() instanceof FilterObjectMapper) &&
                !(converter.getObjectMapper() instanceof FilterXmlMapper))
            throw new IllegalArgumentException("Converter should contain FilterObjectMapper or FilterXmlMapper in objectMapper property for correct filtering");
        customConverters.add(converter);
        return this;
    }

    /**
     * Returns list of custom converters
     *
     * @return {@link HttpMessageConverter}
     */
    public List<HttpMessageConverter<?>> getCustomConverters() {
        return customConverters;
    }

    /**
     * Find ObjectMapper by mediaType and perform consumer operation
     *
     * @param mediaType {@link MediaType} supported media type
     * @param onFind    consumer operation which performs when ObjectMapper will be found
     */
    public void findObjectMapper(MediaType mediaType, Consumer<ObjectMapper> onFind) {
        if (mediaType == null || onFind == null)
            throw new IllegalArgumentException("mediaType or onFind operation can't be null");

        ObjectMapper objectMapper = getMapper(mediaType);
        if(objectMapper != null)
        onFind.accept(objectMapper);
    }

    /**
     * Find all ObjectMappers and perform consumer operation
     *
     * @param onFind consumer operation which performs when ObjectMapper will be found
     */
    public void findObjectMapper(Consumer<ObjectMapper> onFind) {
        if (onFind == null)
            throw new IllegalArgumentException("onFind operation can't be null");
            mapperList.forEach((mediaType, objectMapper) -> onFind.accept(objectMapper));
    }
}


