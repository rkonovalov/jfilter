package com.json.ignore.mock;

import java.util.*;

public class MockWithoutSetters {
    private String strValue;
    private Integer intValue;
    private Map<Integer, String> items;
    private Collection<String> items2;

    public MockWithoutSetters(String strValue, Integer intValue, Map<Integer, String> items, Collection<String> items2) {
        this.strValue = strValue;
        this.intValue = intValue;
        this.items = items;
        this.items2 = items2;
    }

    public MockWithoutSetters() {
        this.strValue = "Test";
        this.intValue = 100;
        this.items = new HashMap<>();
        this.items2 = new ArrayList<>();
        addMockItems();
    }

    private void addMockItems() {
        items.put(1, "hello");
        items.put(2, "World");

        items2.add("Hello");
        items2.add("World");
    }

    public String getStrValue() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public Integer getIntValue() {
        return intValue;
    }

    public Map<Integer, String> getItems() {
        return items;
    }

    public Collection<String> getItems2() {
        return items2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MockWithoutSetters)) return false;
        MockWithoutSetters that = (MockWithoutSetters) o;
        return Objects.equals(strValue, that.strValue) &&
                Objects.equals(intValue, that.intValue) &&
                Objects.equals(items, that.items) &&
                Objects.equals(items2, that.items2);
    }

    @Override
    public int hashCode() {

        return Objects.hash(strValue, intValue, items, items2);
    }
}
