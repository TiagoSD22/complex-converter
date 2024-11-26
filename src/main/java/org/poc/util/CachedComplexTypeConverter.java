package org.poc.util;

import org.poc.annotation.AttributeMapping;
import org.poc.annotation.AttributeMappingContainer;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for converting objects of one type to another with caching.
 */
public class CachedComplexTypeConverter {

    private static final Map<ClassPair, Map<String, String>> fieldMappingsCache = new ConcurrentHashMap<>();

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
            T target = targetClass.getDeclaredConstructor().newInstance();
            ClassPair classPair = new ClassPair(source.getClass(), targetClass);

            Map<String, String> fieldMappings = fieldMappingsCache.computeIfAbsent(classPair, k -> createFieldMappings(source.getClass(), targetClass));

            for (Map.Entry<String, String> entry : fieldMappings.entrySet()) {
                Field sourceField = source.getClass().getDeclaredField(entry.getKey());
                Field targetField = targetClass.getDeclaredField(entry.getValue());

                sourceField.setAccessible(true);
                targetField.setAccessible(true);

                targetField.set(target, sourceField.get(source));

                sourceField.setAccessible(false);
                targetField.setAccessible(false);
            }

            return target;
        } catch (Exception e) {
            throw new RuntimeException("Conversion failed", e);
        }
    }

    private static Map<String, String> createFieldMappings(Class<?> sourceClass, Class<?> targetClass) {
        Map<String, String> fieldMappings = new ConcurrentHashMap<>();
        Field[] sourceFields = sourceClass.getDeclaredFields();

        for (Field sourceField : sourceFields) {
            String targetFieldName = sourceField.getName();

            if (sourceField.isAnnotationPresent(AttributeMapping.class) || sourceField.isAnnotationPresent(AttributeMappingContainer.class)) {
                for (AttributeMapping mapping : sourceField.getAnnotationsByType(AttributeMapping.class)) {
                    if (mapping.targetClass().equals(targetClass)) {
                        targetFieldName = mapping.targetAttribute();
                    }
                }
            }

            try {
                Field targetField = targetClass.getDeclaredField(targetFieldName);
                if (sourceField.getType().equals(targetField.getType())) {
                    fieldMappings.put(sourceField.getName(), targetFieldName);
                }
            } catch (NoSuchFieldException ignored) {
                // Field not found in target class, ignore
            }
        }

        return fieldMappings;
    }

    private static class ClassPair {
        private final Class<?> sourceClass;
        private final Class<?> targetClass;

        public ClassPair(Class<?> sourceClass, Class<?> targetClass) {
            this.sourceClass = sourceClass;
            this.targetClass = targetClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClassPair classPair = (ClassPair) o;

            if (!sourceClass.equals(classPair.sourceClass)) return false;
            return targetClass.equals(classPair.targetClass);
        }

        @Override
        public int hashCode() {
            int result = sourceClass.hashCode();
            result = 31 * result + targetClass.hashCode();
            return result;
        }
    }
}