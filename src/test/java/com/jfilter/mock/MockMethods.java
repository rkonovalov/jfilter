package com.jfilter.mock;

import com.jfilter.filter.DynamicFilter;
import com.jfilter.components.DynamicSessionFilter;
import com.jfilter.filter.FieldFilterSetting;
import com.jfilter.filter.SessionStrategy;
import com.jfilter.filter.FileFilterSetting;
import com.jfilter.mock.config.MockDynamicNullFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import java.lang.reflect.Method;

public class MockMethods {

    @FieldFilterSetting(fields = {"id", "password"})
    public static MethodParameter mockIgnoreSettingsMethod(Object mock) {
        return findMethodParameterByName("mockIgnoreSettingsMethod");
    }

    @SessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
            @FieldFilterSetting(fields = {"id"})
    })

    @SessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @FieldFilterSetting(fields = {"email", "password"})
    })

    public static MethodParameter mockIgnoreStrategiesMethod(Object object) {
        return findMethodParameterByName("mockIgnoreStrategiesMethod");
    }

    @SessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @FieldFilterSetting(fields = {"id", "password"})
    })
    public static MethodParameter mockIgnoreStrategyMethod(Object object) {
        return findMethodParameterByName("mockIgnoreStrategyMethod");
    }

    @FieldFilterSetting(fields = {"id"})
    public static MethodParameter singleAnnotation(Object object) {
        return findMethodParameterByName("singleAnnotation");
    }

    @FieldFilterSetting(fields = {"strValue", "intValue", "items", "items2"})
    public static MethodParameter mockClass() {
        return findMethodParameterByName("mockClass");
    }

    @FieldFilterSetting(fields = {"strValue", "intValue", "items2"})
    public static MethodParameter mockClass2(Object object) {
        return findMethodParameterByName("mockClass2");
    }

    @FieldFilterSetting(fields = {"id"})
    public static MethodParameter secondSingleAnnotation(Object object) {
        return findMethodParameterByName("secondSingleAnnotation");
    }

    @FieldFilterSetting(fields = {"filters"})
    public static MethodParameter withoutFileFilters(Object object) {
        return findMethodParameterByName("withoutFileFilters");
    }

    @FieldFilterSetting(fields = {"password"})
    public static MethodParameter thirdSingleAnnotation(Object object) {
        return findMethodParameterByName("thirdSingleAnnotation");
    }


    @FieldFilterSetting(className = MockUser.class, fields = {"id", "email", "fullName"})
    @FieldFilterSetting(className = MockUser.class, fields = {"password", "intValue", "collectionValue"})
    @FieldFilterSetting(className = MockUser.class, fields = {"mapValue", "boolValue", "byteValue", "charValue"})
    @FieldFilterSetting(className = MockUser.class, fields = {"doubleValue", "floatValue", "longValue", "shortValue"})
    public static MethodParameter multipleAnnotation(Object object) {
        return findMethodParameterByName("multipleAnnotation");
    }

    public static MethodParameter methodWithoutAnnotations(Object object) {
        return findMethodParameterByName("methodWithoutAnnotations");
    }

    @Lazy
    public static MethodParameter methodWithLazyAnnotation(Object object) {
        return findMethodParameterByName("methodWithLazyAnnotation");
    }

    @FileFilterSetting(fileName = "config.xml")
    public static MethodParameter fileAnnotation(Object object) {
        return findMethodParameterByName("fileAnnotation");
    }

    @FileFilterSetting(fileName = "config.yaml")
    public static MethodParameter fileAnnotationYaml(Object object) {
        return findMethodParameterByName("fileAnnotationYaml");
    }

    @FileFilterSetting(fileName = "bad_config.xml")
    public static MethodParameter fileBadConfig(Object object) {
        return findMethodParameterByName("fileBadConfig");
    }

    @FileFilterSetting(fileName = "unknown_config.xml")
    public static MethodParameter fileNotExist(Object object) {
        return findMethodParameterByName("fileNotExist");
    }

    @FileFilterSetting(fileName = "config_no_controllers.xml")
    public static MethodParameter fileAnnotationNoControllers(Object object) {
        return findMethodParameterByName("fileAnnotationNoControllers");
    }

    @FileFilterSetting(fileName = "config_no_strategies.xml")
    public static MethodParameter fileAnnotationNoStrategies(Object object) {
        return findMethodParameterByName("fileAnnotationNoStrategies");
    }

    @FileFilterSetting(fileName = "config_class_duplicated.xml")
    public static MethodParameter fileAnnotationClassDuplicated(Object object) {
        return findMethodParameterByName("fileAnnotationClassDuplicated");
    }

    @DynamicFilter(DynamicSessionFilter.class)
    public static MethodParameter dynamicSessionFilter(Object object) {
        return findMethodParameterByName("dynamicSessionFilter");
    }

    @DynamicFilter(MockDynamicNullFilter.class)
    public static MethodParameter mockDynamicNullFilter(Object object) {
        return findMethodParameterByName("mockDynamicNullFilter");
    }

    @FileFilterSetting(fileName = "config_class_not_found.xml")
    public static MethodParameter fileAnnotationClassNotFound(Object object) {
        return findMethodParameterByName("fileAnnotationClassNotFound");
    }

    @FileFilterSetting()
    public static MethodParameter fileAnnotationEmpty(Object object) {
        return findMethodParameterByName("fileAnnotationEmpty");
    }

    @FileFilterSetting(fileName = "config_io_exception.xml")
    public static MethodParameter fileLocked(Object object) {
        return findMethodParameterByName("fileLocked");
    }

    @FileFilterSetting(fileName = "config_dynamic.xml")
    public static MethodParameter fileFilterDynamic(Object object) {
        return findMethodParameterByName("fileFilterDynamic");
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
}
