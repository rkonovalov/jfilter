package com.json.ignore.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.json.ignore.reflect.MethodType.GET;
import static com.json.ignore.reflect.MethodType.SET;

public class MethodRecord {
    private Field field;
    private Method getter;
    private Method setter;
    private boolean hasMethods;

    public MethodRecord(Field field) {
        this.field = field;
        getter = findMethod(GET);
        setter = findMethod(SET);
        this.hasMethods = getter != null && setter != null;
    }

    public Method getGetter() {
        return getter;
    }

    public Method getSetter() {
        return setter;
    }

    private Method findMethod(MethodType methodType) {
        Class<?> clazz = field.getDeclaringClass();
        String methodName = methodName(methodType);
        try {
            switch (methodType) {
                case GET:
                    return clazz.getDeclaredMethod(methodName);
                case SET:
                    return clazz.getDeclaredMethod(methodName, field.getType());
                default:
                    return null;
            }
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private String methodName(MethodType methodType) {
        return String.format("%s%s%s", methodType.getValue(),
                Character.toUpperCase(field.getName().charAt(0)),
                field.getName().substring(1));
    }

    private Object getValue(Object object) {
        if (getter != null) {
            try {
                return getter.invoke(object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                return null;
            }
        } else
            return null;
    }

    private boolean setValue(Object object, Object value) {
        if (setter != null) {
            try {
                setter.invoke(object, value);
                return true;
            } catch (IllegalAccessException | InvocationTargetException e) {
                return false;
            }
        } else
            return false;
    }

    public boolean setValue(SetEvent event) {
        if (hasMethods && event != null && event.eventValue() != null) {
            return setValue(event.eventValue().getObject(), event.eventValue().getValue());
        } else
            return false;
    }

    public Object getValue(GetEvent event) {
        if (hasMethods && event != null) {
            return getValue(event.getObject());
        } else
            return null;
    }

    public boolean isHasMethods() {
        return hasMethods;
    }

    public Field getField() {
        return field;
    }
}