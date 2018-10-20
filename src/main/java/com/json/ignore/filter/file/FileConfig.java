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

    /**
     * Constructor
     */
    public FileConfig() {
        this.controllers = new ArrayList<>();
    }

    /**
     * Get controllers list
     * @return {@link List} list of controllers, else zero length list
     */
    public List<FileConfig.Controller> getControllers() {
        return controllers;
    }

    /**
     * Set controllers list
     * @param controllers {@link List} list of controllers
     * @return {@link FileConfig} instance of FileConfig class
     */
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

        /**
         * Get full class name with package
         * @return {@link String} class name
         */
        public String getClassName() {
            return className;
        }

        /**
         * Set full class name with package
         * @param className {@link String} class name
         * @return {@link FileConfig.Controller} instance of Controller class
         */
        public FileConfig.Controller setClassName(String className) {
            this.className = className;
            return this;
        }

        /**
         * Get list of strategies
         * @return {@link List} list of strategies, else zero length list
         */
        public List<Strategy> getStrategies() {
            return strategies;
        }

        /**
         * Set list of strategies
         * @param strategies {@link List}
         * @return {@link FileConfig.Controller} instance of Controller class
         */
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

        /**
         * Get attribute name
         * @return {@link String}
         */
        public String getAttributeName() {
            return attributeName;
        }

        /**
         * Set attribute name
         * @param attributeName {@link String}
         * @return {@link FileConfig.Strategy} instance of Controller class
         */
        public Strategy setAttributeName(String attributeName) {
            this.attributeName = attributeName;
            return this;
        }

        /**
         * Get attribute value
         * @return {@link String}
         */
        public String getAttributeValue() {
            return attributeValue;
        }

        /**
         * Set attribute value
         * @param attributeValue {@link String}
         * @return {@link FileConfig.Strategy} instance of Strategy class
         */
        public Strategy setAttributeValue(String attributeValue) {
            this.attributeValue = attributeValue;
            return this;
        }

        /**
         * Get list of filters
         * @return {@link List} list, else zero length list
         */
        public List<Filter> getFilters() {
            return filters;
        }

        /**
         * Set list of strategies
         * @param filters {@link List} list with items or zero length list
         * @return {@link FileConfig.Strategy} instance of Strategy class
         */
        public Strategy setFilters(List<Filter> filters) {
            this.filters = filters;
            return this;
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

        /**
         * Get class name
         * @return {@link String}
         */
        public String getClassName() {
            return className;
        }

        /**
         * Set class name
         * @param className {@link String}
         * @return {@link FileConfig.Filter} instance of Filter class
         */
        public Filter setClassName(String className) {
            this.className = className;
            return this;
        }

        /**
         * Get fields
         * @return {@link List} list of fields, else zero length list
         */
        public List<Field> getFields() {
            return fields;
        }

        /**
         * Set fields
         * @param fields {@link List}
         * @return {@link FileConfig.Filter} instance of Filter class
         */
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

        /**
         * Get field name
         * @return {@link String}
         */
        public String getName() {
            return name;
        }

        /**
         * Set field name
         * @param name {@link String}
         * @return {@link FileConfig.Field} instance of Field class
         */
        public Field setName(String name) {
            this.name = name;
            return this;
        }
    }

}
