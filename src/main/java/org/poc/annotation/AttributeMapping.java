package org.poc.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(AttributeMappingContainer.class)
public @interface AttributeMapping {
    String targetAttribute();
    Class<?> targetClass();
}
