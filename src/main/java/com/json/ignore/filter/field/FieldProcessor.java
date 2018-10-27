package com.json.ignore.filter.field;

import com.json.ignore.reflect.MethodEventValue;
import com.json.ignore.reflect.MethodRecord;
import com.json.ignore.reflect.Reflect;
import com.json.ignore.request.RequestMethodParameter;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Field;
import java.util.*;

public class FieldProcessor {
    private Map<Class, List<String>> ignoreList;
    private final Reflect reflect;

    public FieldProcessor() {
        this.ignoreList = new HashMap<>();
        this.reflect = new Reflect();
    }

    public FieldProcessor(Map<Class, List<String>> ignoreList) {
        this();
        this.ignoreList = ignoreList;
    }

    public FieldProcessor(FieldFilterSetting[] annotations) {
        this(Arrays.asList(annotations));
    }

    private FieldProcessor(List<FieldFilterSetting> annotations) {
        this();
        this.ignoreList = parseSettingAnnotation(annotations);
    }

    public FieldProcessor(MethodParameter methodParameter) {
        this();
        RequestMethodParameter requestMethodParameter = new RequestMethodParameter(methodParameter);
        this.ignoreList = parseSettingAnnotation(Arrays.asList(requestMethodParameter.getSettingAnnotations()));
    }

    public FieldProcessor(Class clazz, List<String> ignoreFields) {
        this();
        this.ignoreList.put(clazz, ignoreFields);
    }

    private Map<Class, List<String>> parseSettingAnnotation(List<FieldFilterSetting> settings) {
        Map<Class, List<String>> items = new HashMap<>();
        if (settings != null)
            for (FieldFilterSetting setting : settings) {
                List<String> fields = new ArrayList<>(Arrays.asList(setting.fields()));
                if (items.containsKey(setting.className())) {
                    List<String> existFields = items.get(setting.className());
                    existFields.addAll(fields);
                    items.put(setting.className(), existFields);
                } else
                    items.put(setting.className(), fields);
            }

        return items;
    }

    private boolean isIgnoreRecordFound(Field field, Class clazz) {
        for (Map.Entry<Class, List<String>> entry : ignoreList.entrySet()) {
            if (entry.getValue().contains(field.getName())) {
                return Objects.equals(clazz, entry.getKey()) ||
                        Objects.equals(void.class, entry.getKey()) ||
                        Objects.equals(null, entry.getKey());
            }
        }


        return false;
    }

    private boolean isFieldIgnored(Field field, Class clazz) {
        return isIgnoreRecordFound(field, clazz);
    }

    private Object defaultValue(Field field) {
        switch (field.getType().getName()) {
            case "boolean":
                return Boolean.FALSE;
            case "byte":
                return Byte.MIN_VALUE;
            case "char":
                return Character.MIN_VALUE;
            case "double":
                return Double.MIN_VALUE;
            case "float":
                return Float.MIN_VALUE;
            case "int":
                return Integer.MIN_VALUE;
            case "long":
                return Long.MIN_VALUE;
            case "short":
                return Short.MIN_VALUE;
            default:
                return null;
        }
    }

    private boolean clearField(Object object, MethodRecord methodRecord) {
        Object value = defaultValue(methodRecord.getField());
        return methodRecord.setValue(() -> new MethodEventValue(object, value));
    }


    private Class getObjectClass(Object object) {
        if (object != null) {
            return object.getClass().getDeclaredFields().length > 0 ?
                    object.getClass() : object.getClass().getSuperclass();
        } else
            return null;
    }

    private void filterCollection(Collection collection) {
        for (Object aCollection : collection) {
            filter(aCollection);
        }
    }

    @SuppressWarnings("unchecked")
    private void filterMap(Map map) {
        for (Map.Entry<Object, Object> entry : (Iterable<Map.Entry<Object, Object>>) map.entrySet()) {
            filter(entry.getKey());
            filter(entry.getValue());
        }
    }


    public void filter(Object object) {
        Class objectClass = getObjectClass(object);
        if (objectClass != null) {

            for (Field field : objectClass.getDeclaredFields()) {
                MethodRecord methodRecord = reflect.getRecord(field);
                if (methodRecord != null) {
                    if (isFieldIgnored(field, objectClass)) {
                        clearField(object, methodRecord);
                    } else {
                        Object value = methodRecord.getValue(() -> object);
                        if (value != null) {

                            if (value instanceof Collection) {
                                filterCollection((Collection) value);
                            } else if (value instanceof Map) {
                                filterMap((Map) value);
                            } else
                                filter(value);

                        }
                    }
                }
            }
        }
    }
}
