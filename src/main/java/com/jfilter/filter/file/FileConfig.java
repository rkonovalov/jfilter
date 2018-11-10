package com.jfilter.filter.file;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.jfilter.filter.FilterFields;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class used for deserialization of xml annotated strategy files
 */
@JacksonXmlRootElement(localName = "config")
public class FileConfig implements Serializable {

    private static final long serialVersionUID = -5089337585215156841L;
    @JacksonXmlProperty(localName = "controller")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Controller> controllers;

    /**
     * Creates a new instance of the {@link FileConfig} class.
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
        private static final long serialVersionUID = -7843796315737644281L;
        @JacksonXmlProperty(localName = "class-name", isAttribute = true)
        private String className;

        @JacksonXmlProperty(localName = "strategy")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Strategy> strategies;

        /**
         * Creates a new instance of the {@link Controller} class.
         */
        public Controller() {
            this.strategies = new ArrayList<>();
        }

        /**
         * Returns class name
         *
         * @return {@link String}
         */
        public String getClassName() {
            return className;
        }

        /**
         * Sets class name
         *
         * @param className {@link String}
         * @return instance of {@link Controller}
         */
        public Controller setClassName(String className) {
            this.className = className;
            return this;
        }

        /**
         * Returns list of strategies
         *
         * @return {@link List}, {@link Strategy}
         */
        public List<Strategy> getStrategies() {
            return strategies;
        }

        /**
         * Sets list of strategies
         *
         * @param strategies {@link List}, {@link Strategy}
         * @return instance of {@link Controller}
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

        private static final long serialVersionUID = 2166775466278797733L;
        @JacksonXmlProperty(localName = "attribute-name", isAttribute = true)
        private String attributeName;

        @JacksonXmlProperty(localName = "attribute-value", isAttribute = true)
        private String attributeValue;

        @JacksonXmlProperty(localName = "filter")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Filter> filters;

        /**
         * Creates a new instance of the {@link Strategy} class.
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
         * Get class by name
         *
         * <p>Try to get class by it full name. If class couldn't be found, returns null
         *
         * @param className {@link String} class name. Example: java.io.File
         * @return {@link Class} return class, else null
         */
        private Class getClassByName(String className) {
            if (className != null && !className.isEmpty()) {
                try {
                    return Class.forName(className);
                } catch (ClassNotFoundException e) {
                    return null;
                }
            } else
                return null;
        }

        /**
         * Add filter fields
         *
         * @param filterFields {@link FilterFields} source fields
         * @return {@link FilterFields} fields which should be filtered/excluded
         */
        public FilterFields appendStrategyFields(FilterFields filterFields) {
            this.getFilters().forEach(filter -> {
                Class clazz = getClassByName(filter.getClassName());
                List<String> items = filterFields.getFields(clazz);

                filter.getFields().forEach(field -> {
                    //filter duplicates of field names
                    if (!items.contains(field.getName()))
                        items.add(field.getName());
                });
                filterFields.appendToMap(clazz, items);
            });

            return filterFields;
        }
    }

    /**
     * Filter tag of xml schema
     */
    @JacksonXmlRootElement(localName = "filter")
    public static class Filter implements Serializable {

        private static final long serialVersionUID = 4096598826506008282L;
        @JacksonXmlProperty(localName = "class", isAttribute = true)
        private String className;

        @JacksonXmlProperty(localName = "field")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Field> fields;

        /**
         * Creates a new instance of the {@link Filter} class.
         */
        public Filter() {
            this.fields = new ArrayList<>();
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
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

        private static final long serialVersionUID = 6812574046008633856L;
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
