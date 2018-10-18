package com.json.ignore.filter;

import com.json.ignore.AnnotationUtil;
import com.json.ignore.strategy.SessionStrategies;
import com.json.ignore.strategy.SessionStrategy;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * This class used for strategy filtration of object's fields based on SessionStrategy configuration
 *
 */
public class StrategyFilter extends Filter {
    /**
     * Array of {@link SessionStrategy} configuration annotations
     */
    private SessionStrategy[] config;

    /**
     * Constructor
     * @param serverHttpRequest {@link ServerHttpRequest} servlet request
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public StrategyFilter(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);

        /*
         * Attempt to retrieve all FieldFilterSetting annotations from method
         */
        config = AnnotationUtil.getStrategyAnnotations(methodParameter.getMethod());
    }

    /**
     * Constructor
     * @param session {@link HttpSession} session
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public StrategyFilter(HttpSession session, MethodParameter methodParameter) {
        super(session);
        config = AnnotationUtil.getStrategyAnnotations(methodParameter.getMethod());
    }

    /**
     * Filter object's fields if filtrating annotations and session is configured
     * @param object {@link Object} object which fields will be filtered
     * @throws IllegalAccessException exception of illegal access
     */
    @Override
    public void filter(Object object) throws IllegalAccessException {
        if (this.getSession() != null) {
            for (SessionStrategy strategy : config) {
                Object sessionObject = getSession().getAttribute(strategy.attributeName());

                /*
                 * Attempt to filter object's fields if attribute in session is found
                 * and value in session and strategy is equals
                 */
                if (Objects.equals(sessionObject, strategy.attributeValue())) {
                    FieldFilterProcessor processor = new FieldFilterProcessor(strategy.ignoreFields());
                    processor.filterFields(object);
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
