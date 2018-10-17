package mock;

import com.json.ignore.JsonIgnoreSetting;
import com.json.ignore.strategy.JsonSessionStrategies;
import com.json.ignore.strategy.JsonSessionStrategy;

import java.lang.reflect.Method;


public class MockMethods {

    @JsonIgnoreSetting(fields = {"id", "password"})
    public boolean mockIgnoreSettingsMethod() {
        return false;
    }

    @JsonSessionStrategies({
            @JsonSessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
                    @JsonIgnoreSetting(fields = {"id", "password"})
            })
    })
    public boolean mockIgnoreStrategiesMethod() {
        return false;
    }


    @JsonSessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
            @JsonIgnoreSetting(fields = {"id"})
    })

    @JsonSessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @JsonIgnoreSetting(fields = {"id", "password"})
    })

    public boolean mockIgnoreStrategyMethod() {
        return false;
    }


    @JsonIgnoreSetting(fields = {"id"})
    public boolean singleAnnotation() {
        return false;
    }

    @JsonIgnoreSetting(className = MockUser.class, fields = {"id", "email", "fullName"})
    @JsonIgnoreSetting(className = MockUser.class, fields = {"password", "intValue", "collectionValue"})
    @JsonIgnoreSetting(className = MockUser.class, fields = {"mapValue", "boolValue", "byteValue", "charValue"})
    @JsonIgnoreSetting(className = MockUser.class, fields = {"doubleValue", "floatValue", "longValue", "shortValue"})
    public boolean multipleAnnotation() {
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


}
