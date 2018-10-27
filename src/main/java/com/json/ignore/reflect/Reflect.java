package com.json.ignore.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Reflect {
    private Map<Field, MethodRecord> fields;

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


    private void test() {
        Field field = null;
        Object object = null;

        //1 loop to all declared methods and find only public, sort get and set
        //Maybe 2 lists of getter/setter methods
        //2 find equals and field

        // Modifier.isPublic(method.getModifiers())

        Object result = getRecord(field).getValue(() -> "Hello");
        boolean result2 = getRecord(field).setValue(() -> new MethodEventValue(object, result));
    }

}
