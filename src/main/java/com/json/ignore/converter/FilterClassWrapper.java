package com.json.ignore.converter;

import com.json.ignore.advice.FilterAdvice;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Class contains Serializable object and list of ignorable fields
 *
 * <p>Class used to send additional information from {@link FilterAdvice} to {@link FilterConverter}
 */
public class FilterClassWrapper implements Serializable {
    private final Serializable object;
    private final Map<Class, List<String>> ignoreList;

    /**
     * Creates a new instance of the {@link FilterClassWrapper} class.
     *
     * @param object Serializable object
     * @param ignoreList ignorable fields
     */
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
