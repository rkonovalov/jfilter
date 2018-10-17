package com.json.ignore.strategy;

import com.json.ignore.AnnotationUtil;
import com.json.ignore.FieldIgnoreProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import java.util.Arrays;

public class StrategyIgnore extends Ignore {
    private SessionStrategy[] jsonStrategies;

    public StrategyIgnore(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);
        jsonStrategies = AnnotationUtil.getStrategyAnnotations(methodParameter.getMethod());
    }

    @Override
    public void jsonIgnore(Object object) throws IllegalAccessException {
        if (this.getSession() != null) {
            for (SessionStrategy strategy : jsonStrategies) {
                Object sessionObject = getSession().getAttribute(strategy.attributeName());
                if (sessionObject != null && sessionObject.equals(strategy.attributeValue())) {
                    FieldIgnoreProcessor jsonIgnore = new FieldIgnoreProcessor(Arrays.asList(strategy.ignoreFields()));
                    jsonIgnore.ignoreFields(object);
                }
            }
        }
    }

    public static boolean isAccept(MethodParameter methodParameter) {
        return AnnotationUtil.isAnnotationExists(methodParameter.getMethod(), SessionStrategy.class, SessionStrategies.class);
    }

}
