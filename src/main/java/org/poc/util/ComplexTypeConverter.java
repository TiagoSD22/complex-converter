package org.poc.util;

import org.poc.annotation.AttributeMapping;
import org.poc.annotation.AttributeMappingContainer;

import java.lang.reflect.Field;

/**
 * Utility class for converting objects of one type to another.
 */
public class ComplexTypeConverter {

    /**
     * Converts an object of one type to another type.
     *
     * @param source the source object to convert
     * @param targetClass the class of the target object
     * @param <T> the type of the target object
     * @return the converted object of type T
     * @throws RuntimeException if the conversion fails
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        try {
            // Create a new instance of the target class
            T target = targetClass.getDeclaredConstructor().newInstance();

            // Get all fields of the source and target classes
            Field[] sourceFields = source.getClass().getDeclaredFields();
            Field[] targetFields = targetClass.getDeclaredFields();

            // Iterate over each field in the source class
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                String targetFieldName = sourceField.getName();

                // Check if the source field is annotated with AttributeMapping or AttributeMappingContainer
                if (sourceField.isAnnotationPresent(AttributeMapping.class) || sourceField.isAnnotationPresent(AttributeMappingContainer.class)) {
                    AttributeMapping[] mappings = sourceField.getAnnotationsByType(AttributeMapping.class);
                    for (AttributeMapping mapping : mappings) {
                        // If the target class matches, use the specified target attribute name
                        if (mapping.targetClass().equals(targetClass)) {
                            targetFieldName = mapping.targetAttribute();
                        }
                    }
                }

                // Iterate over each field in the target class
                for (Field targetField : targetFields) {
                    // If the field names and types match, set the value from the source to the target
                    if (targetField.getName().equals(targetFieldName) &&
                            sourceField.getType().equals(targetField.getType())) {
                        targetField.setAccessible(true);
                        targetField.set(target, sourceField.get(source));
                        targetField.setAccessible(false);
                    }
                }
                sourceField.setAccessible(false);
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Conversion failed", e);
        }
    }
}