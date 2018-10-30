package com.json.ignore.converter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FilterClassWrapper implements Serializable {
    private final Serializable object;
    private final Map<Class, List<String>> ignoreList;

    public FilterClassWrapper(Serializable object, Map<Class, List<String>> ignoreList) {
        this.object = object;
        this.ignoreList = ignoreList;
    }

    public Serializable getObject() {
        return object;
    }

    public Map<Class, List<String>> getIgnoreList() {
        return ignoreList;
    }
}
