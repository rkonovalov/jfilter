
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.json.ignore;

import org.springframework.core.MethodParameter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Ruslan {@literal <rkonovalov86@gmail.com>}
 * @version 1.0
 */

public class JsonIgnoreFields {
    public static final List<String> ignoredNames = Arrays.asList("CASE_INSENSITIVE_ORDER", "LOGGER");

    private Map<Class, List<String>> ignore;
    private List<String> fieldNames;

    public JsonIgnoreFields(MethodParameter methodParameter) {
        this(getAnnotations(methodParameter));
    }

    public JsonIgnoreFields(List<JsonIgnoreSetting> annotations) {
        this.fieldNames = new ArrayList<>();
        this.ignore = parseSettingAnnotation(annotations);
    }

    public boolean fieldHasGetter(Field field, Class clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().toLowerCase().equals("get" + field.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void process(Map map) {
        map.forEach((k, v) -> {
            ignoreFields(k);
            ignoreFields(v);
        });
    }

    public void process(Collection items) {
        for (Object item : items)
            ignoreFields(item);
    }

    public boolean isFieldIgnored(Field field, Class clazz) {
        for (Class cl : ignore.keySet()) {
            List<String> items = ignore.get(cl);
            if (items.contains(field.getName())) {
                if (clazz.equals(cl) || void.class.equals(cl))
                    return true;
            }
        }
        return false;
    }

    public void clearField(Field field, Object object) {
        try {
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
                    field.setLong(object, Short.MIN_VALUE);
                    break;
                default:
                    field.set(object, null);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean fieldAcceptable(Field field) {
        return field.getType().isPrimitive() || field.getType().isArray() || ignoredNames.contains(field.getName());
    }

    public void ignoreFields(Object object) {
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
                                ignoreFields(value);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Map<Class, List<String>> parseSettingAnnotation(List<JsonIgnoreSetting> settings) {
        Map<Class, List<String>> items = new HashMap<>();
        if (settings != null)
            for (JsonIgnoreSetting setting : settings) {
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

    public static boolean annotationFound(MethodParameter methodParameter) {
        Method method = methodParameter.getMethod();
        if (method.getDeclaredAnnotation(JsonIgnoreSettings.class) != null ||
                method.getDeclaredAnnotation(JsonIgnoreSetting.class) != null) {
            return true;
        } else
            return false;
    }

    public static List<JsonIgnoreSetting> getAnnotations(MethodParameter methodParameter) {
        Method method = methodParameter.getMethod();
        List<JsonIgnoreSetting> annotations = new ArrayList<>();

        if (method.getDeclaredAnnotation(JsonIgnoreSettings.class) != null) {
            JsonIgnoreSettings ignoreSettings = method.getDeclaredAnnotation(JsonIgnoreSettings.class);
            annotations.addAll(new ArrayList<>(Arrays.asList(ignoreSettings.value())));
        }

        if (method.getDeclaredAnnotation(JsonIgnoreSetting.class) != null) {
            JsonIgnoreSetting ignoreSetting = method.getDeclaredAnnotation(JsonIgnoreSetting.class);
            annotations.add(ignoreSetting);
        }

        return annotations;
    }

}