package com.jfilter.converter;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.jfilter.filter.FilterFields;

import java.util.List;

import static com.jfilter.filter.FilterBehaviour.HIDE_FIELDS;

/**
 * This class is  inherited from BeanSerializerModifier
 *
 * <p>Extends functionality of standard {@link BeanSerializerModifier} and
 * give ability to filter ignorable fields from object
 */
public class ConverterMapperModifier extends BeanSerializerModifier {
    private final FilterFields filterFields;

    /**
     * Creates a new instance of the {@link ConverterMapperModifier} class.
     *
     * @param filterFields {@link FilterFields} list of ignorable field names
     */
    public ConverterMapperModifier(FilterFields filterFields) {
        this.filterFields = filterFields;
    }

    /**
     * Trying to change properties
     *
     * @param config         serialization config
     * @param beanDesc       bean description
     * @param beanProperties list of bean properties
     * @return list of bean properties
     */
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        removeFields(beanDesc, beanProperties);
        return super.changeProperties(config, beanDesc, beanProperties);
    }

    /**
     * Search matches of field name in ignoreList and bean properties list
     * If match is found then bean property will be removed from list
     *
     * @param clazz          class
     * @param beanProperties list of bean properties
     */
    private void removeFieldsByClass(Class clazz, List<BeanPropertyWriter> beanProperties) {
        List<String> ignores = filterFields.getFields(clazz);

        if (!ignores.isEmpty())
            if (filterFields.getFilterBehaviour() == HIDE_FIELDS) {
                beanProperties.removeIf(beanProperty -> ignores.contains(beanProperty.getName()));
            } else
                beanProperties.removeIf(beanProperty -> !ignores.contains(beanProperty.getName()));
    }

    /**
     * Attempt to remove bean property from bean properties list
     *
     * @param beanDesc       bean description
     * @param beanProperties list of bean properties
     */
    private void removeFields(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        /*
         * Try to remove fields of specified class
         */
        removeFieldsByClass(beanDesc.getType().getRawClass(), beanProperties);

        /*
         * Try to remove fields with not specified class
         */
        removeFieldsByClass(null, beanProperties);
        removeFieldsByClass(void.class, beanProperties);
    }
}
