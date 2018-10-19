package mock;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.file.FileFilterSetting;
import com.json.ignore.filter.strategy.SessionStrategies;
import com.json.ignore.filter.strategy.SessionStrategy;
import org.springframework.core.MethodParameter;
import java.lang.reflect.Method;


public class MockMethods {

    @FieldFilterSetting(fields = {"id", "password"})
    public boolean mockIgnoreSettingsMethod() {
        return false;
    }

    @SessionStrategies({
            @SessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
                    @FieldFilterSetting(fields = {"id", "password"})
            })
    })
    public boolean mockIgnoreStrategiesMethod() {
        return false;
    }


    @SessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @FieldFilterSetting(fields = {"id", "password"})
    })
    public boolean mockIgnoreStrategyMethod() {
        return false;
    }


    @FieldFilterSetting(fields = {"id"})
    public boolean singleAnnotation() {
        return false;
    }

    @FieldFilterSetting(className = MockUser.class, fields = {"id", "email", "fullName"})
    @FieldFilterSetting(className = MockUser.class, fields = {"password", "intValue", "collectionValue"})
    @FieldFilterSetting(className = MockUser.class, fields = {"mapValue", "boolValue", "byteValue", "charValue"})
    @FieldFilterSetting(className = MockUser.class, fields = {"doubleValue", "floatValue", "longValue", "shortValue"})
    public boolean multipleAnnotation() {
        return false;
    }

    public boolean methodWithoutAnnotations() {
        return false;
    }

    @FileFilterSetting(fileName = "config.xml")
    public boolean fileAnnotation() {
        return false;
    }

    @FileFilterSetting(fileName = "config_no_controllers.xml")
    public boolean fileAnnotationNoControllers() {
        return false;
    }

    @FileFilterSetting(fileName = "config_no_strategies.xml")
    public boolean fileAnnotationNoStrategies() {
        return false;
    }
    @FileFilterSetting(fileName = "config_class_duplicated.xml")
    public boolean fileAnnotationClassDuplicated() {
        return false;
    }


    public static Method findMethodByName(String methodName) {
        Method[] methods = MockMethods.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }

    public static MethodParameter findMethodParameterByName(String methodName) {
        Method method = findMethodByName(methodName);
        if (method != null) {
            return new MethodParameter(method, 0);
        } else
            return null;
    }


}
