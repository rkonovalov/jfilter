package mock;

import com.json.ignore.FieldIgnoreSetting;
import com.json.ignore.strategy.SessionStrategies;
import com.json.ignore.strategy.SessionStrategy;

import java.lang.reflect.Method;


public class MockMethods {

    @FieldIgnoreSetting(fields = {"id", "password"})
    public boolean mockIgnoreSettingsMethod() {
        return false;
    }

    @SessionStrategies({
            @SessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
                    @FieldIgnoreSetting(fields = {"id", "password"})
            })
    })
    public boolean mockIgnoreStrategiesMethod() {
        return false;
    }



    @SessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @FieldIgnoreSetting(fields = {"id", "password"})
    })
    public boolean mockIgnoreStrategyMethod() {
        return false;
    }


    @FieldIgnoreSetting(fields = {"id"})
    public boolean singleAnnotation() {
        return false;
    }

    @FieldIgnoreSetting(className = MockUser.class, fields = {"id", "email", "fullName"})
    @FieldIgnoreSetting(className = MockUser.class, fields = {"password", "intValue", "collectionValue"})
    @FieldIgnoreSetting(className = MockUser.class, fields = {"mapValue", "boolValue", "byteValue", "charValue"})
    @FieldIgnoreSetting(className = MockUser.class, fields = {"doubleValue", "floatValue", "longValue", "shortValue"})
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
