package com.json.ignore.advice;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FilterClassWrapper implements Serializable {
    private Serializable object;
    private Map<Class, List<String>> ignoreList;

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
