package com.json.ignore.mock;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.file.FileFilterSetting;
import com.json.ignore.filter.strategy.SessionStrategy;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

public class MockMethods {

    @FieldFilterSetting(fields = {"id", "password"})
    public static MethodParameter mockIgnoreSettingsMethod() {
        return findMethodParameterByName("mockIgnoreSettingsMethod");
    }

    @SessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
            @FieldFilterSetting(fields = {"id"})
    })

    @SessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @FieldFilterSetting(fields = {"email", "password"})
    })

    public static MethodParameter mockIgnoreStrategiesMethod() {
        return findMethodParameterByName("mockIgnoreStrategiesMethod");
    }

    @SessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @FieldFilterSetting(fields = {"id", "password"})
    })
    public static MethodParameter mockIgnoreStrategyMethod() {
        return findMethodParameterByName("mockIgnoreStrategyMethod");
    }

    @FieldFilterSetting(fields = {"id"})
    public static MethodParameter singleAnnotation() {
        return findMethodParameterByName("singleAnnotation");
    }

    @FieldFilterSetting(fields = {"strValue", "intValue", "items", "items2"})
    public static MethodParameter mockClass() {
        return findMethodParameterByName("mockClass");
    }

    @FieldFilterSetting(fields = {"strValue", "intValue", "items2"})
    public static MethodParameter mockClass2() {
        return findMethodParameterByName("mockClass2");
    }

    @FieldFilterSetting(fields = {"id"})
    public static MethodParameter secondSingleAnnotation() {
        return findMethodParameterByName("secondSingleAnnotation");
    }

    @FieldFilterSetting(fields = {"filters"})
    public static MethodParameter withoutFileFilters() {
        return findMethodParameterByName("withoutFileFilters");
    }

    @FieldFilterSetting(fields = {"password"})
    public static MethodParameter thirdSingleAnnotation() {
        return findMethodParameterByName("thirdSingleAnnotation");
    }


    @FieldFilterSetting(className = MockUser.class, fields = {"id", "email", "fullName"})
    @FieldFilterSetting(className = MockUser.class, fields = {"password", "intValue", "collectionValue"})
    @FieldFilterSetting(className = MockUser.class, fields = {"mapValue", "boolValue", "byteValue", "charValue"})
    @FieldFilterSetting(className = MockUser.class, fields = {"doubleValue", "floatValue", "longValue", "shortValue"})
    public static MethodParameter multipleAnnotation() {
        return findMethodParameterByName("multipleAnnotation");
    }

    public static MethodParameter methodWithoutAnnotations() {
        return findMethodParameterByName("methodWithoutAnnotations");
    }

    @FileFilterSetting(fileName = "config.xml")
    public static MethodParameter fileAnnotation() {
        return findMethodParameterByName("fileAnnotation");
    }

    @FileFilterSetting(fileName = "config_no_controllers.xml")
    public static MethodParameter fileAnnotationNoControllers() {
        return findMethodParameterByName("fileAnnotationNoControllers");
    }

    @FileFilterSetting(fileName = "config_no_strategies.xml")
    public static MethodParameter fileAnnotationNoStrategies() {
        return findMethodParameterByName("fileAnnotationNoStrategies");
    }

    @FileFilterSetting(fileName = "config_class_duplicated.xml")
    public static MethodParameter fileAnnotationClassDuplicated() {
        return findMethodParameterByName("fileAnnotationClassDuplicated");
    }

    private static Method findMethodByName(String methodName) {
        Method[] methods = MockMethods.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }

    private static MethodParameter findMethodParameterByName(String methodName) {
        Method method = findMethodByName(methodName);
        if (method != null) {
            return new MethodParameter(method, 0);
        } else
            return null;
    }

    @FileFilterSetting(fileName = "config.xml")
    public static MethodParameter methodWithParams(String email, String password) {
        return findMethodParameterByName("methodWithParams");
    }


}
