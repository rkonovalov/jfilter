package com.json.ignore.filter;

import java.io.Serializable;
import java.util.*;

/**
 * Class used in filters
 *
 * <p>This class contains map of fields which could be filtered
 */
public class FilterFields implements Serializable {
    private static final long serialVersionUID = -2354837314560228182L;

    private final Map<Class, List<String>> fieldsMap;

    /**
     * Creates a new instance of the {@link FilterFields} class.
     */
    public FilterFields() {
        this.fieldsMap = new HashMap<>();
    }

    /**
     * Creates a new instance of the {@link FilterFields} class.
     *
     * @param className class name
     * @param fields list of field name
     */
    public FilterFields(Class className, List<String> fields) {
        this();
        this.fieldsMap.put(className, fields);
    }

    public Map<Class, List<String>> getFieldsMap() {
        return fieldsMap;
    }

    /**
     * Returns filterable field names of defined class
     *
     * @param className class name
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
     * @param classname class name
     * @param fields list of filterable fields
     */
    public void appendToMap(Class classname, List<String> fields) {
        List<String> foundFields = fieldsMap.computeIfAbsent(classname, k -> new ArrayList<>());
        fields.forEach(v -> {
            if (!foundFields.contains(v)) foundFields.add(v);
        });
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof FilterFields)) return false;
        FilterFields that = (FilterFields) object;
        return Objects.equals(fieldsMap, that.fieldsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldsMap);
    }
}