package com.jfilter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This class simplifies if...else branching
 * <p>In situations when you need to use a lot of "else...if" statements you can simplify it by using this class.
 *
 * @param <T> value of object
 */
public class Comparator<T> {
    private List<ComparatorItem> items;
    private T value;

    private Comparator(T value) {
        this.value = value;
        items = new ArrayList<>();
    }

    /**
     * Returns an {@code Comparator} with the specified present nullable value.
     *
     * @param value value of nullable object
     * @param <T>   type of the object
     * @return an {@code Comparator} with the value present
     */
    public static <T> Comparator<T> of(T value) {
        return new Comparator<>(value);
    }

    /**
     * Adds predicate and return function in items list
     *
     * @param predicate   a predicate to apply to the value, if present
     * @param returnValue a mapping function to apply to the value, if present
     * @param <U>         The type of the result of the mapping function
     * @return an {@code Comparator} with the value present
     * @throws NoSuchElementException if predicate or mapping function is null
     */
    @SuppressWarnings("unchecked")
    public <U> Comparator<T> match(Predicate<? super T> predicate, Function<? super T, ? extends U> returnValue) {

        if (predicate == null) {
            throw new NoSuchElementException("No predicate present");
        } else if (returnValue == null)
            throw new NoSuchElementException("No mapping function present");

        items.add(new ComparatorItem(value, predicate, returnValue));
        return this;
    }

    /**
     * Gets object from all stored in list mapping functions
     *
     * <p>This method loops through the list, pickup each item {@link ComparatorItem}.
     * If predicate in item is apply to the value then method get object from mapping function.
     * If object in mapping function isn't null then method returns this object.
     * Otherwise method continues to loop list items.
     * If all items is looped and object value is null, method returns {@code defaultValue}
     *
     * @param defaultValue default value if method couln't to find not null object
     * @return {@link Object}
     */
    @SuppressWarnings("unchecked")
    private Object get(Object defaultValue) {

        for (ComparatorItem item : items) {
            Optional<T> optional = (Optional<T>) Optional.ofNullable(item.getValue());

            Object obj = optional.filter(item.getPredicate())
                    .map(item.getMapper())
                    .orElse(null);
            if (obj != null)
                return obj;
        }
        return defaultValue;
    }

    /**
     * Gets object from all stored in list mapping functions.
     *
     * <p>If method couldn't to find not null object, it returns {@code value} object
     *
     * @param value not-null object which will be returned by default
     * @return {@link Object}
     */
    public Object orElse(Object value) {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return get(value);
    }

    /**
     * Gets object from all stored in list mapping functions.
     *
     * <p>If method couldn't to find not null object, it returns null value
     *
     * @return {@link Object}
     */
    public Object get() {
        return get(null);
    }
}
