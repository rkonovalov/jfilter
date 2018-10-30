package com.json.ignore.advice;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.json.ignore.IgnoreMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FilterXmlConverter implements HttpMessageConverter<FilterClassWrapper> {
    private final List<MediaType> supportedMedia;

    public FilterXmlConverter() {
        supportedMedia = new ArrayList<>();
        supportedMedia.add(MediaType.APPLICATION_XML);
        supportedMedia.add(new MediaType("application", "xml", Charset.defaultCharset()));
        supportedMedia.add(new MediaType("application", "*+xml"));
        supportedMedia.add(new MediaType("text", "xml"));
    }

    @Override
    public boolean canRead(Class aClass, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class aClass, MediaType mediaType) {
        return supportedMedia.indexOf(mediaType) >= 0;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return supportedMedia;
    }

    @Override
    public FilterClassWrapper read(Class aClass, HttpInputMessage httpInputMessage) throws HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(FilterClassWrapper object, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        httpOutputMessage.getHeaders().setContentType(mediaType);

        IgnoreMapper ignoreMapper = new IgnoreMapper(new XmlMapper(), object.getIgnoreList());
        httpOutputMessage.getBody().write(ignoreMapper.getMapper().writeValueAsBytes(object.getObject()));
    }
}