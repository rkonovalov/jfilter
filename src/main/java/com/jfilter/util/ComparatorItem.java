package com.jfilter.util;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Class container which used in {@link Comparator}
 * @param <T> type of the object
 * @param <U> The type of the result of the mapping function
 */
public class ComparatorItem<T, U> {
    private T value;
    private Predicate<? super T> predicate;
    private Function<? super T, ? extends U> mapper;

    /**
     * Constructor
     *
     * @param value value of object
     * @param predicate a predicate to apply to the value, if present
     * @param mapper a mapping function to apply to the value, if present
     */
    public ComparatorItem(T value, Predicate<? super T> predicate, Function<? super T, ? extends U> mapper) {
        this.value = value;
        this.predicate = predicate;
        this.mapper = mapper;
    }

    /**
     * Returns object value
     *
     * @return object
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns predicate
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    public Predicate<? super T> getPredicate() {
        return predicate;
    }

    /**
     * Returns mapper function
     *
     * @return a mapping function to apply to the value, if present
     */
    public Function<? super T, ? extends U> getMapper() {
        return mapper;
    }
}