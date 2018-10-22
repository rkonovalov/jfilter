package com.json.ignore.filter.file;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.json.ignore.request.RequestMethodParameter.getClassByName;

/**
 * This class used for deserialization of xml annotated strategy files
 */
@JacksonXmlRootElement(localName = "config")
public class FileConfig implements Serializable {

    @JacksonXmlProperty(localName = "controller")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Controller> controllers;

    /**
     * Constructor
     */
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

    /**
     * Controller tag of xml schema
     */
    @JacksonXmlRootElement(localName = "controller")
    public static class Controller implements Serializable {
        @JacksonXmlProperty(localName = "class-name", isAttribute = true)
        private String className;

        @JacksonXmlProperty(localName = "strategy")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Strategy> strategies;

        /**
         * Constructor
         */
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

    /**
     * Strategy tag of xml schema
     */
    @JacksonXmlRootElement(localName = "strategy")
    public static class Strategy implements Serializable {

        @JacksonXmlProperty(localName = "attribute-name", isAttribute = true)
        private String attributeName;

        @JacksonXmlProperty(localName = "attribute-value", isAttribute = true)
        private String attributeValue;

        @JacksonXmlProperty(localName = "filter")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Filter> filters;

        /**
         * Constructor
         */
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

        /**
         * Convert strategy class in Map
         *
         * @return {@link HashMap} map of fields which should be filtered/excluded
         */
        public Map<Class, List<String>> getStrategyFields() {
            Map<Class, List<String>> fields = new HashMap<>();

            this.getFilters().forEach(filter -> {
                Class clazz = getClassByName(filter.getClassName());
                List<String> items;

                if (fields.containsKey(clazz)) {
                    items = fields.get(clazz);
                } else
                    items = new ArrayList<>();

                filter.getFields().forEach(field -> {
                    //filter duplicates of field names
                    if (!items.contains(field.getName()))
                        items.add(field.getName());
                });
                fields.put(clazz, items);
            });

            return fields;
        }
    }

    /**
     * Filter tag of xml schema
     */
    @JacksonXmlRootElement(localName = "filter")
    public static class Filter implements Serializable {

        @JacksonXmlProperty(localName = "class", isAttribute = true)
        private String className;

        @JacksonXmlProperty(localName = "field")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Field> fields;

        /**
         * Constructor
         */
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

    /**
     * Field tag of xml schema
     */
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
