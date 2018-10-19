package com.json.ignore.filter.file;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class used for deserialization of xml annotated strategy files
 */

@JacksonXmlRootElement(localName = "config")
public class FileConfig implements Serializable {

    @JacksonXmlProperty(localName = "controller")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Controller> controllers;

    public FileConfig() {
        this.controllers = new ArrayList<>();
    }

    public List<FileConfig.Controller> getControllers() {
        return controllers;
    }

    public FileConfig setControllers(List<FileConfig.Controller> controllers) {
        this.controllers = controllers;
        return this;
    }

    @JacksonXmlRootElement(localName = "controller")
    public static class Controller {
        @JacksonXmlProperty(localName = "class-name", isAttribute = true)
        private String className;

        @JacksonXmlProperty(localName = "strategy")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Strategy> strategies;

        public Controller() {
            this.strategies = new ArrayList<>();
        }

        public String getClassName() {
            return className;
        }

        public FileConfig.Controller setClassName(String className) {
            this.className = className;
            return this;
        }

        public List<Strategy> getStrategies() {
            return strategies;
        }

        public Controller setStrategies(List<Strategy> strategies) {
            this.strategies = strategies;
            return this;
        }
    }

    @JacksonXmlRootElement(localName = "strategy")
    public static class Strategy implements Serializable {

        @JacksonXmlProperty(localName = "attribute-name", isAttribute = true)
        private String attributeName;

        @JacksonXmlProperty(localName = "attribute-value", isAttribute = true)
        private String attributeValue;

        @JacksonXmlProperty(localName = "filter")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Filter> filters;

        public Strategy() {
            this.filters = new ArrayList<>();
        }

        public String getAttributeName() {
            return attributeName;
        }

        public Strategy setAttributeName(String attributeName) {
            this.attributeName = attributeName;
            return this;
        }

        public String getAttributeValue() {
            return attributeValue;
        }

        public Strategy setAttributeValue(String attributeValue) {
            this.attributeValue = attributeValue;
            return this;
        }

        public List<Filter> getFilters() {
            return filters;
        }

        public Strategy setFilters(List<Filter> filters) {
            this.filters = filters;
            return this;
        }
    }

    @JacksonXmlRootElement(localName = "filter")
    public static class Filter implements Serializable {

        @JacksonXmlProperty(localName = "class", isAttribute = true)
        private String className;

        @JacksonXmlProperty(localName = "field")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Field> fields;

        public Filter() {
            this.fields = new ArrayList<>();
        }

        public String getClassName() {
            return className;
        }

        public Filter setClassName(String className) {
            this.className = className;
            return this;
        }

        public List<Field> getFields() {
            return fields;
        }

        public Filter setFields(List<Field> fields) {
            this.fields = fields;
            return this;
        }
    }

    @JacksonXmlRootElement(localName = "field")
    public static class Field implements Serializable {

        @JacksonXmlProperty(localName = "name", isAttribute = true)
        private String name;

        public String getName() {
            return name;
        }

        public Field setName(String name) {
            this.name = name;
            return this;
        }
    }

}
