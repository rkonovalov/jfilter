package com.json.ignore.reflect;

public class MethodEventValue {
    private Object object;
    private Object value;

    public MethodEventValue(Object object, Object value) {
        this.object = object;
        this.value = value;
    }

    public Object getObject() {
        return object;
    }

    public Object getValue() {
        return value;
    }
}
