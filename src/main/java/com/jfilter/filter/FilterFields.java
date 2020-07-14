package com.jfilter.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Class used in filters
 *
 * <p>This class contains map of fields which could be filtered
 */
@SuppressWarnings("rawtypes")
public class FilterFields implements Serializable {
    private static final long serialVersionUID = -2354837314560228182L;
    public static final Supplier<FilterFields> EMPTY_FIELDS = FilterFields::new;

    private final Map<Class, List<String>> fieldsMap;
    private FilterBehaviour filterBehaviour;

    /**
     * Creates a new instance of the {@link FilterFields} class.
     */
    public FilterFields() {
        this.fieldsMap = new HashMap<>();
        this.filterBehaviour = FilterBehaviour.HIDE_FIELDS;
    }

    /**
     * Creates a new instance of the {@link FilterFields} class.
     *
     * @param className class name
     * @param fields    list of field name
     */
    public FilterFields(Class className, List<String> fields) {
        this();
        this.fieldsMap.put(className, fields);
    }

    public Map<Class, List<String>> getFieldsMap() {
        return fieldsMap;
    }

    public FilterBehaviour getFilterBehaviour() {
        return filterBehaviour;
    }

    public FilterFields setFilterBehaviour(FilterBehaviour filterBehaviour) {
        this.filterBehaviour = filterBehaviour;
        return this;
    }

    /**
     * Returns filterable field names of defined class
     *
     * @param className class name whose fields will be filtered
     * @return list of fields, if defined class not found returns empty array list
     */
    public List<String> getFields(Class className) {
        return fieldsMap.containsKey(className) ? fieldsMap.get(className) : new ArrayList<>();
    }

    /**
     * Append filterable fields in map
     *
     * <p>Method attempt to find defined class name in map.
     * If class is found then adds filterable fields in exist list, else
     * creates new array list and adds fields in it
     *
     * @param classname class name whose fields will be filtered
     * @param fields    list of filterable fields
     */
    public void appendToMap(Class classname, List<String> fields) {
        List<String> foundFields = fieldsMap.computeIfAbsent(classname, k -> new ArrayList<>());
        fields.forEach(v -> {
            if (!foundFields.contains(v)) foundFields.add(v);
        });
    }

    /**
     * Create instance of FilterFields
     *
     * @param classname class name whose fields will be filtered
     * @param fields    list of filterable fields
     * @return instance of FilterFields
     */
    @SuppressWarnings("WeakerAccess")
    public static FilterFields getFieldsBy(Class classname, List<String> fields) {
        return new FilterFields(classname, fields);
    }

    /**
     * Create instance of FilterFields without direction to filterable class
     *
     * @param fields list of filterable fields
     * @return instance of FilterFields
     */
    public static FilterFields getFieldsBy(List<String> fields) {
        return getFieldsBy(void.class, fields);
    }

    /**
     * Create instance of FilterFields
     *
     * @param classname class name whose fields will be filtered
     * @param fields    string array of filterable fields
     * @return instance of FilterFields
     */
    public static FilterFields getFieldsBy(Class classname, String[] fields) {
        return getFieldsBy(classname, Arrays.asList(fields));
    }

    /**
     * Create instance of FilterFields without direction to filterable class
     *
     * @param fields string array of filterable fields
     * @return instance of FilterFields
     */
    public static FilterFields getFieldsBy(String[] fields) {
        return getFieldsBy(void.class, Arrays.asList(fields));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof FilterFields)) return false;
        FilterFields that = (FilterFields) object;
        return Objects.equals(fieldsMap, that.fieldsMap) &&
                filterBehaviour == that.filterBehaviour;
    }

    /**
     * Return size of fields HashMap
     *
     * @return size
     */
    public int size() {
        return fieldsMap.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldsMap, filterBehaviour);
    }
}