package com.json.ignore.mock;

import java.util.*;

public class MockWithAll {
    private String strValue;
    private Integer intValue;
    private Map<Integer, String> items;
    private Collection<String> items2;

    public MockWithAll(String strValue, Integer intValue, Map<Integer, String> items, Collection<String> items2) {
        this.strValue = strValue;
        this.intValue = intValue;
        this.items = items;
        this.items2 = items2;
    }

    public MockWithAll() {
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

    public String getStrValue() {
        return strValue;
    }

    public MockWithAll setStrValue(String strValue) {
        this.strValue = strValue;
        return this;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public MockWithAll setIntValue(Integer intValue) {
        this.intValue = intValue;
        return this;
    }

    public Map<Integer, String> getItems() {
        return items;
    }

    public MockWithAll setItems(Map<Integer, String> items) {
        this.items = items;
        return this;
    }

    public Collection<String> getItems2() {
        return items2;
    }

    public MockWithAll setItems2(Collection<String> items2) {
        this.items2 = items2;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MockWithAll)) return false;
        MockWithAll that = (MockWithAll) o;
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
