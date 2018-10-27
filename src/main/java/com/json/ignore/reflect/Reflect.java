package com.json.ignore.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Reflect {
    private final Map<Field, MethodRecord> fields;

    public Reflect() {
        fields = new HashMap<>();
    }

    private MethodRecord addRecord(Field field) {
        MethodRecord methodRecord = new MethodRecord(field);
        if (methodRecord.isHasMethods()) {
            fields.put(field, methodRecord);
            return methodRecord;
        } else
            return null;
    }

    public MethodRecord getRecord(Field field) {
        return fields.containsKey(field) ? fields.get(field) : addRecord(field);
    }
}
