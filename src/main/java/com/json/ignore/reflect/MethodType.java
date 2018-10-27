package com.json.ignore.reflect;

public enum MethodType {
    GET("get"),
    SET("set");

    private final String value;

    MethodType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
