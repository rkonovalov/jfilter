package com.json.ignore.strategy;

import com.json.ignore.AnnotationUtil;
import com.json.ignore.JsonIgnoreFields;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import java.util.Arrays;

public class StrategyIgnore extends JsonIgnore {
    private JsonSessionStrategy[] jsonStrategies;

    public StrategyIgnore(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);
        jsonStrategies = AnnotationUtil.getStrategyAnnotations(methodParameter.getMethod());
    }

    @Override
    public void jsonIgnore(Object object) throws IllegalAccessException {
        if (this.getSession() != null) {
            for (JsonSessionStrategy strategy : jsonStrategies) {
                Object sessionObject = getSession().getAttribute(strategy.attributeName());
                if (sessionObject != null && sessionObject.equals(strategy.attributeValue())) {
                    JsonIgnoreFields jsonIgnore = new JsonIgnoreFields(Arrays.asList(strategy.ignoreFields()));
                    jsonIgnore.ignoreFields(object);
                }
            }
        }
    }

    public static boolean isAccept(MethodParameter methodParameter) {
        return AnnotationUtil.isAnnotationExists(methodParameter.getMethod(), JsonSessionStrategy.class, JsonSessionStrategies.class);
    }

}
