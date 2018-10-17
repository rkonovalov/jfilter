package com.json.ignore.strategy;

import com.json.ignore.AnnotationUtil;
import com.json.ignore.FieldIgnoreProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

/**
 * This class used for strategy filtration of object's fields based on SessionStrategy configuration
 *
 */
public class StrategyIgnore extends Ignore {
    /**
     * Array of {@link SessionStrategy} configuration annotations
     */
    private SessionStrategy[] config;

    /**
     * Constructor
     * @param serverHttpRequest {@link ServerHttpRequest} servlet request
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public StrategyIgnore(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);

        /*
         * Attempt to retrieve all FieldIgnoreSetting annotations from method
         */
        config = AnnotationUtil.getStrategyAnnotations(methodParameter.getMethod());
    }

    /**
     * Filter object's fields if filtrating annotations and session is configured
     * @param object {@link Object} object which fields will be filtered
     * @throws IllegalAccessException exception of illegal access
     */
    @Override
    public void jsonIgnore(Object object) throws IllegalAccessException {
        if (this.getSession() != null) {
            for (SessionStrategy strategy : config) {
                Object sessionObject = getSession().getAttribute(strategy.attributeName());

                /*
                 * Attempt to filter object's fields if attribute in session is found
                 * and value in session and strategy is equals
                 */
                if (sessionObject != null && sessionObject.equals(strategy.attributeValue())) {
                    FieldIgnoreProcessor jsonIgnore = new FieldIgnoreProcessor(strategy.ignoreFields());
                    jsonIgnore.ignoreFields(object);
                }
            }
        }
    }

    /**
     * Used to show that method has {@link SessionStrategy} or {@link SessionStrategies} annotations
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     * @return {@link Boolean} true if annotations found, otherwise false
     */
    public static boolean isAccept(MethodParameter methodParameter) {
        return AnnotationUtil.isAnnotationExists(methodParameter.getMethod(), SessionStrategy.class, SessionStrategies.class);
    }

}
