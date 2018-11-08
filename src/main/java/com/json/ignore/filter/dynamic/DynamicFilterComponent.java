package com.json.ignore.filter.dynamic;

import org.springframework.stereotype.Component;
import java.lang.annotation.*;

/**
 * Indicates that an annotated class is a "DynamicFilterComponent" (e.g. a dynamic filter component).
 *
 * <p>This annotation serves as a specialization of {@link Component @Component},
 * allowing for implementation classes to be autodetected through classpath scanning.
 * It is typically used in combination with annotated handler methods based on the
 * {@link DynamicFilter} annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Component
public @interface DynamicFilterComponent {
}