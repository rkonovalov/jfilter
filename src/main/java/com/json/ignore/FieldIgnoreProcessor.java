package com.json.ignore;

import org.springframework.core.MethodParameter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


public class FieldIgnoreProcessor {
    private static final List<String> ignoredNames = Arrays.asList("CASE_INSENSITIVE_ORDER", "LOGGER");

    private Map<Class, List<String>> ignore;
    private List<String> fieldNames;

    public FieldIgnoreProcessor() {
        this.fieldNames = new ArrayList<>();
    }

    public FieldIgnoreProcessor(Map<Class, List<String>> ignore) {
        this();
        this.ignore = ignore;
    }

    public FieldIgnoreProcessor(MethodParameter methodParameter) {
        this(getAnnotations(methodParameter.getMethod()));
    }

    public FieldIgnoreProcessor(List<FieldIgnoreSetting> annotations) {
        this();
        this.ignore = parseSettingAnnotation(annotations);
    }

    public FieldIgnoreProcessor(Method method) {
        this(getAnnotations(method));
    }

    public FieldIgnoreProcessor(Class clazz, List<String> ignoreFields) {
        this();
        this.ignore = new HashMap<>();
        this.ignore.put(clazz, ignoreFields);
    }

    private boolean fieldHasGetter(Field field, Class clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equalsIgnoreCase("get" + field.getName())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void process(Map map) throws IllegalAccessException {
        for(Object k : map.keySet()) {
            ignoreFields(k);
            ignoreFields(map.get(k));
        }
    }

    private void process(Collection items) throws IllegalAccessException {
        for (Object item : items)
            ignoreFields(item);
    }

    private boolean isFieldIgnored(Field field, Class clazz) {
        for (Class cl : ignore.keySet()) {
            List<String> items = ignore.get(cl);
            if (items.contains(field.getName())) {
                return (clazz.equals(cl) || void.class.equals(cl));
            }
        }
        return false;
    }

    private void clearField(Field field, Object object) throws IllegalAccessException {
        field.setAccessible(true);
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
    }

    private boolean fieldAcceptable(Field field) {
        return /*field.getType().isPrimitive() ||*/ field.getType().isArray() || ignoredNames.contains(field.getName());
    }

    public void ignoreFields(Object object) throws IllegalAccessException {
        Class clazz = object.getClass().getDeclaredFields().length > 0 ? object.getClass() : object.getClass().getSuperclass();
        Class currentClass = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (!fieldAcceptable(field) && fieldHasGetter(field, clazz)) {
                field.setAccessible(true);
                if (isFieldIgnored(field, currentClass)) {
                    clearField(field, object);
                } else {
                    Object value = field.get(object);
                    if (value != null) {
                        if (value instanceof Collection) {
                            process((Collection) value);
                        } else if (value instanceof Map) {
                            process((Map) value);
                        } else
                            ignoreFields(value);
                    }
                }
            }
        }
    }

    private Map<Class, List<String>> parseSettingAnnotation(List<FieldIgnoreSetting> settings) {
        Map<Class, List<String>> items = new HashMap<>();
        if (settings != null)
            for (FieldIgnoreSetting setting : settings) {
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

    private static List<FieldIgnoreSetting> getAnnotations(Method method) {
        return Arrays.asList(AnnotationUtil.getSettingAnnotations(method));
    }

}