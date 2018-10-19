package com.json.ignore.filter.field;

import com.json.ignore.FieldAccessException;
import com.json.ignore.filter.AnnotationUtil;
import org.springframework.core.MethodParameter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This class used to filter fields in object
 */
public class FieldFilterProcessor {
    /**
     * list of object name which will be ignored by filter algorithm
     */
    private static final List<String> ignoredNames = Arrays.asList("CASE_INSENSITIVE_ORDER", "LOGGER");

    /**
     * Map of classes and fields which should be filtered
     */
    private Map<Class, List<String>> ignore;

    /**
     * list of field name which should be filtered
     */
    private List<String> fieldNames;

    /**
     * Constructor
     */
    public FieldFilterProcessor() {
        this.fieldNames = new ArrayList<>();
    }

    /**
     * Constructor
     *
     * @param ignore map of classes and fields which should be filtered
     */
    public FieldFilterProcessor(Map<Class, List<String>> ignore) {
        this();
        this.ignore = ignore;
    }

    /**
     * Constructor
     *
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public FieldFilterProcessor(MethodParameter methodParameter) {
        this(getAnnotations(methodParameter.getMethod()));
    }

    /**
     * Constructor
     *
     * @param annotations array of {@link FieldFilterSetting}
     */
    public FieldFilterProcessor(FieldFilterSetting[] annotations) {
        this(Arrays.asList(annotations));
    }

    /**
     * Constructor
     *
     * @param annotations list of {@link FieldFilterSetting}
     */
    public FieldFilterProcessor(List<FieldFilterSetting> annotations) {
        this();
        this.ignore = parseSettingAnnotation(annotations);
    }

    /**
     * Constructor
     *
     * @param method {@link Method} object's method which may have annotation
     */
    public FieldFilterProcessor(Method method) {
        this(getAnnotations(method));
    }

    /**
     * Constructor
     *
     * @param clazz        {@link Class} class name of filterable class
     * @param ignoreFields array of filterable items
     */
    public FieldFilterProcessor(Class clazz, List<String> ignoreFields) {
        this();
        this.ignore = new HashMap<>();
        this.ignore.put(clazz, ignoreFields);
    }

    /**
     * Check if specified class has getter method of field
     *
     * @param field {@link Field}
     * @param clazz {@link Class}
     * @return true if getter is found, else false
     */
    private boolean fieldHasGetter(Field field, Class clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equalsIgnoreCase("get" + field.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * BaseFilter field which has value of Map, enumerate all objects in Map if exists
     *
     * @param map {@link Map}
     * @throws FieldAccessException exception of illegal access
     */
    private void process(Map map) throws FieldAccessException {
        for (Object k : map.keySet()) {
            filterFields(k);
            filterFields(map.get(k));
        }
    }

    /**
     * BaseFilter field which has value of Collection, enumerate all objects in collection if exists
     *
     * @param items {@link Collection} collection of items
     * @throws FieldAccessException exception of illegal access
     */
    private void process(Collection items) throws FieldAccessException {
        for (Object item : items)
            filterFields(item);
    }

    /**
     * Check if field should be filtered
     *
     * @param field {@link Field} field
     * @param clazz {@link Class} class
     * @return true if field should be ignored, else false
     */
    private boolean isFieldIgnored(Field field, Class clazz) {
        for (Class cl : ignore.keySet()) {
            List<String> items = ignore.get(cl);
            if (items.contains(field.getName())) {
                return (clazz.equals(cl) || void.class.equals(cl));
            }
        }
        return false;
    }

    /**
     * Attempt to filter item. This method clears value of field by setting null value if field is object
     * or setting system defined MIN value if object is primitive
     *
     * @param field  {@link Field} field
     * @param object {@link Object} object
     * @throws FieldAccessException exception of illegal access
     */
    private void clearField(Field field, Object object) throws FieldAccessException {
        field.setAccessible(true);
        try {
            switch (field.getType().getName()) {
                case "boolean":
                    field.setBoolean(object, Boolean.FALSE);
                    break;
                case "byte":
                    field.setByte(object, Byte.MIN_VALUE);
                    break;
                case "char":
                    field.setChar(object, Character.MIN_VALUE);
                    break;
                case "double":
                    field.setDouble(object, Double.MIN_VALUE);
                    break;
                case "float":
                    field.setFloat(object, Float.MIN_VALUE);
                    break;
                case "int":
                    field.setInt(object, Integer.MIN_VALUE);
                    break;
                case "long":
                    field.setLong(object, Long.MIN_VALUE);
                    break;
                case "short":
                    field.setShort(object, Short.MIN_VALUE);
                    break;
                default:
                    field.set(object, null);
                    break;
            }
        } catch (IllegalAccessException e) {
            throw new FieldAccessException(e);
        }
    }

    /**
     * This method used to filter not defined by user fields or not ignorable fields in object
     *
     * @param field {@link Field} field
     * @return true if field may be processed by filter algorithm
     */
    private boolean fieldAcceptable(Field field) {
        return /*field.getType().isPrimitive() ||*/ field.getType().isArray() || ignoredNames.contains(field.getName());
    }

    /**
     * BaseFilter algorithm, finds fields which should be ignored and filters them
     *
     * @param object {@link Object} object
     * @throws FieldAccessException exception of illegal access
     */
    public void filterFields(Object object) throws FieldAccessException {
        Class clazz = object.getClass().getDeclaredFields().length > 0 ? object.getClass() : object.getClass().getSuperclass();
        Class currentClass = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (!fieldAcceptable(field) && fieldHasGetter(field, clazz)) {
                field.setAccessible(true);
                if (isFieldIgnored(field, currentClass)) {
                    clearField(field, object);
                } else {
                    try {
                        Object value = field.get(object);
                        if (value != null) {
                            if (value instanceof Collection) {
                                process((Collection) value);
                            } else if (value instanceof Map) {
                                process((Map) value);
                            } else
                                filterFields(value);
                        }
                    } catch (IllegalAccessException e) {
                        throw new FieldAccessException(e);
                    }
                }
            }
        }
    }

    /**
     * Convert list of {@link FieldFilterSetting} to Map of classes and fields
     *
     * @param settings list of {@link FieldFilterSetting}
     * @return Map of classes and fields if settings has more than one item, else returns Map with zero length
     */
    private Map<Class, List<String>> parseSettingAnnotation(List<FieldFilterSetting> settings) {
        Map<Class, List<String>> items = new HashMap<>();
        if (settings != null)
            for (FieldFilterSetting setting : settings) {
                List<String> fields = new ArrayList<>(Arrays.asList(setting.fields()));
                fieldNames.addAll(fields);
                if (items.containsKey(setting.className())) {
                    List<String> existFields = items.get(setting.className());
                    existFields.addAll(fields);
                    items.put(setting.className(), existFields);
                } else
                    items.put(setting.className(), fields);
            }

        return items;
    }

    /**
     * Get list of {@link FieldFilterSetting} annotations
     *
     * @param method {@link Method} object's method which may have annotation
     * @return list of {@link FieldFilterSetting} items if method has annotations, else returns list with zero length
     */
    private static List<FieldFilterSetting> getAnnotations(Method method) {
        return Arrays.asList(AnnotationUtil.getSettingAnnotations(method));
    }

}